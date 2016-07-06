package com.example.phonekeeper;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.ActivityManager;
//import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug.MemoryInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProcessManageActivity extends Activity {
	private ListView lvProcess;
	private List<ProcessInfo> arrProcess;//��������
	private ProcessAdapter processAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_manage);
		lvProcess=(ListView) findViewById(R.id.lv_process);
		arrProcess=getProcessDate();
		processAdapter=new ProcessAdapter();
		lvProcess.setAdapter(processAdapter);
		lvProcess.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final ProcessInfo process=arrProcess.get(position);
//				new AlertDialog.Builder(ProcessManageActivity.this)
//				.setTitle("��ʾ")
//				.setMessage("ȷ��Ҫ��������["+process.appName+"]��")
//				.setPositiveButton("ȷ��",new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog,int which) {
//						// TODO Auto-generated method stub
//						
//					}
//				}).setNegativeButton("ȡ��",null).create().show();
new AlertDialog.Builder(ProcessManageActivity.this)
.setTitle("��ʾ")
.setMessage("ȷ��Ҫ��������["+process.appName+"]��")
.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						ActivityManager am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
						for(String pkg:process.pkgList){
							am.killBackgroundProcesses(pkg);
						}
						arrProcess=getProcessDate();
						processAdapter.notifyDataSetChanged();
						arg0.dismiss();
					}
				}).setNegativeButton("ȡ��",null).create().show();
			}
		});
	}
	//��ȡ�����б�
	private List<ProcessInfo> getProcessDate(){
		ArrayList<ProcessInfo> arr=new ArrayList<ProcessManageActivity.ProcessInfo>();
		//List<ProcessInfo> arr=new ArrayList<ProcessInfo>(); 
		ActivityManager am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> arrRunning=am.getRunningAppProcesses();
		for(RunningAppProcessInfo runningApp:arrRunning){
			ProcessInfo process=new ProcessInfo();
			//
			process.pid=runningApp.pid;
			process.process=runningApp.processName;
			process.pkgList=runningApp.pkgList;
			process.importance=runningApp.importance;
			int[] men =new int[]{process.pid};
			MemoryInfo[] mi =am.getProcessMemoryInfo(men);
			process.memory=mi[0].dalvikPrivateDirty;
			PackageManager pm=getPackageManager();
			try {
				ApplicationInfo app =pm.getApplicationInfo(process.pkgList[0],0 );
				process.appName=app.loadLabel(pm).toString();
				process.icon=app.loadIcon(pm);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			arr.add(process);
		}
		return arr;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.process_manage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	class ProcessAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrProcess.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrProcess.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ProcessInfo process=(ProcessInfo) getItem(position);
			ViewHolder viewHolder=null;
			if(convertView==null){
				convertView=View.inflate(ProcessManageActivity.this,R.layout.item_process,null);
				viewHolder=new ViewHolder();
				viewHolder.ivAppIcon=(ImageView) convertView.findViewById(R.id.iv_appicon);
				viewHolder.tvAppName=(TextView) convertView.findViewById(R.id.tv_appname);
				viewHolder.tvProcess=(TextView) convertView.findViewById(R.id.tv_process);
				viewHolder.tvMemory=(TextView) convertView.findViewById(R.id.tv_memory);
				convertView.setTag(viewHolder);
			}
			else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			viewHolder.ivAppIcon.setImageDrawable(process.icon);
			viewHolder.tvAppName.setText(process.appName);
			viewHolder.tvMemory.setText(process.memory+"KB");
			viewHolder.tvProcess.setText(process.process);
			return convertView;
		}
		
	}
	class ViewHolder {
		ImageView ivAppIcon;
		TextView tvAppName;
		TextView tvProcess;
		TextView tvMemory;
	}
	//���һ��ProcessInfo�࣬������ʾ������Ϣ
	class ProcessInfo{
		int pid;//����id;
		String appName;//Ӧ����
		String process;//������
		String[] pkgList;//���б�
		Drawable icon;//Ӧ��ͼ��
		int memory;//�ڴ�ռ��
		int importance;//����
	}
}
