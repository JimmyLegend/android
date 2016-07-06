package com.example.phonekeeper;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SpeedActivity extends Activity {

	private TextView tvMemory;
	private Button btnSpeed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speed);
		
		tvMemory=(TextView) findViewById(R.id.tv_memory);
		btnSpeed=(Button) findViewById(R.id.btn_speed);
		//初始化内存信息
		getMemory();
		//加速按钮
		btnSpeed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//加速
				ActivityManager am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
				List<RunningAppProcessInfo> arrRunning=am.getRunningAppProcesses();
				for(RunningAppProcessInfo runningApp:arrRunning){
					for(String pgk:runningApp.pkgList){
					am.killBackgroundProcesses(pgk);
					}
				}
				
				//刷新一下内存的显示
				getMemory();
			}
		});
	}
private void getMemory(){
	ActivityManager am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
	MemoryInfo memoryInfo=new MemoryInfo();
	am.getMemoryInfo(memoryInfo);
	tvMemory.setText((memoryInfo.availMem/1024/1024+"MB"));
	
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.speed, menu);
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
}
