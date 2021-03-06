package com.example.phonekeeper;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FilePickerDialog extends Dialog implements OnItemClickListener{

	public FilePickerDialog(Context context) {
		super(context);
		init();
	}
	
	
	private ListView lv_file;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList;
	private OnFileSelectListener listener;
	
	private static final File rootPath=
			Environment.getExternalStorageDirectory().getParentFile(); 
	
	private static final String SRC="src"; 
	private static final String NAME="name";
	private static final String FILE="file";
	private static final String IS_BAK="isBak";
	
	private void init() {
		getChildren2dataList(rootPath);
		simpleAdapter=new SimpleAdapter(getContext(),dataList , R.layout.list_item_choosefile, 
				new String[]{SRC,NAME}, new int[]{R.id.iv_file_type,R.id.tv_file_name});
		
		lv_file=new ListView(getContext());
		lv_file.setAdapter(simpleAdapter);
		lv_file.setOnItemClickListener(this);
		setContentView(lv_file);
		
		setCanceledOnTouchOutside(true);
		this.setTitle("选择文件");
	}
	/**
	 * 璁剧疆閫夋嫨鏂囦欢鐨勭洃鍚櫒
	 * @param listener
	 */
	public void setOnFileSelectListener(OnFileSelectListener listener) {
		this.listener = listener;
	}
	/**
	 * 灏嗘湰鐩綍鐨勫瓙鏂囦欢鐨勪俊鎭祴鍊肩粰dataList瀛楁
	 * @param dir
	 */
	private void getChildren2dataList(File dir) {
		File[] children=dir.listFiles();
		if(dataList==null){
			dataList=new ArrayList<Map<String,Object>>();
		}else {
			dataList.clear();
		}	
		
		if(!rootPath.equals(dir)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(SRC, R.drawable.folder);
			map.put(NAME, "..");
			map.put(FILE, dir.getParentFile());
			map.put(IS_BAK, true);
			dataList.add(map);
		}
		if(children==null) return ;//閬垮厤绌虹洰褰曞紩璧风殑绌烘寚閽堥敊璇�
		for (File file : children) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(SRC, file.isDirectory()
								?R.drawable.folder
								:R.drawable.file);
			map.put(NAME, file.getName());
			map.put(FILE, file);
			dataList.add(map);
		}
		
		
		Collections.sort(dataList, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
				int result=0;
				
				if(lhs.get(IS_BAK)!=null){
					result = -1;
				}else if (rhs.get(IS_BAK)!=null) {
					result = 1;
				}else {
					File lhsFile=(File) lhs.get(FILE);
					File rhsFile=(File) rhs.get(FILE);  
					
					if(lhsFile.isDirectory()&&rhsFile.isFile()){
						result = -1;
					}else if (lhsFile.isFile()&&rhsFile.isDirectory()) {
						result = 1;
					}else {
						Collator cmp = Collator.getInstance(Locale.getDefault());  
						result = cmp.compare(lhs.get(NAME), rhs.get(NAME));
					}
				}
				return result;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		File file=(File)dataList.get(position).get(FILE);
		if(file.isDirectory()){
			getChildren2dataList(file);
			simpleAdapter.notifyDataSetChanged();
		}else {
			if(listener!=null){
				listener.onFileSelect(file);
				this.dismiss();
			}
		}
	}
	
	public static interface OnFileSelectListener{
		void onFileSelect(File file);
	}
}
