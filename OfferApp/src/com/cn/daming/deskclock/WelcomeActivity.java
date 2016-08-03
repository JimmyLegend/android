package com.cn.daming.deskclock;

import com.cn.daming.deskclock.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

public class WelcomeActivity extends Activity {
Handler mHandler=new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				Message msg=new Message();
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
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
