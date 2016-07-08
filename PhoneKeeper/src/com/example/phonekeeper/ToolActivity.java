package com.example.phonekeeper;
import edu.sdut.phonekeeper.service.APPLockService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolActivity extends Activity {
	private GridView gridView;
	private int[] icon={
			R.drawable.caculator
	};

	//Ҫ��ʾ������
	private String[] iconName = { "������"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tool);
        //�ڼ��ڲ����ļ�֮���ȡ�����ļ��еĿؼ�
        gridView=(GridView) findViewById(R.id.gridView);
        
        GridAdapter adapter=new GridAdapter();
        gridView.setAdapter(adapter);
        //ΪGridView����������¼�
        gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//��Ӧָ�����¼�
				//ͨ��arg2����ȷ���û����������һ������
				switch (arg2) {
				case 0://Ӧ�ù���
				{
					Intent intent=new Intent(ToolActivity.this,CaculatorActivity.class);
					startActivity(intent);
					break;
				}
				default:
					break;
				}
			}
        	
		});
        Intent intent=new Intent(ToolActivity.this,APPLockService.class);
        startService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tool, menu);
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
	class GridAdapter extends BaseAdapter{

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		//��������ķ���ֵ��������GridView
    		return iconName.length;
    	}

    	@Override
    	public Object getItem(int arg0) {
    		// TODO Auto-generated method stub
    		return null;
    	}

    	@Override
    	public long getItemId(int position) {
    		// TODO Auto-generated method stub
    		return position;
    	}

    	@Override
    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		// TODO Auto-generated method stub
    		View view=View.inflate(ToolActivity.this,R.layout.item_gridview,null);
    		//��ȡ�����ļ��еģ�ͼƬ���ı�����
    		ImageView iv_Item=(ImageView) view.findViewById(R.id.imageView);
    		TextView tv_Item=(TextView) view.findViewById(R.id.textView);
    		
    		iv_Item.setImageResource(icon[arg0]);
    		tv_Item.setText(iconName[arg0]);
    		
    		return view;
    	}}
}
