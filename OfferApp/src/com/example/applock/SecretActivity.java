package com.example.applock;

import java.util.ArrayList;
import java.util.List;

import com.example.clock.R;

import edu.sdut.offerapp.db.APPLockConfig;
import edu.sdut.offerapp.db.APPLockDao;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SecretActivity extends Activity {

	private EditText etPassword;
	private Button btnSet;
	private ListView lvAppList;
	private List<AppLockInfo> arrAppLock;
	private APPLockConfig appLockConfig;
	private AppLockAdapter appLockAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secret);
		etPassword=(EditText) findViewById(R.id.et_password);
		btnSet=(Button) findViewById(R.id.btn_set);
		lvAppList=(ListView) findViewById(R.id.lv_applist);
		appLockConfig=new APPLockConfig(SecretActivity.this);
		etPassword.setText(appLockConfig.getPassWord());
		arrAppLock=getAppLockData();
		appLockAdapter=new AppLockAdapter();
		lvAppList.setAdapter(appLockAdapter);
		btnSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				appLockConfig.putPassWord(etPassword.getText().toString().trim());
				Toast.makeText(SecretActivity.this,"…Ë÷√≥…π¶",Toast.LENGTH_LONG).show();
			}
		});
		lvAppList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AppLockInfo appLockInfo=arrAppLock.get(position);
				APPLockDao appLockDao=new APPLockDao(SecretActivity.this);
				if(appLockInfo.lock){
					appLockDao.delete(appLockInfo.pkgName);
					appLockInfo.lock=false;
				}
				else{
					appLockDao.add(appLockInfo.pkgName);
					appLockInfo.lock=true;
				}
				appLockAdapter.notifyDataSetChanged();
			}
		});
	}
	private List<AppLockInfo> getAppLockData(){
		List<AppLockInfo> arrAppLockInfos=new ArrayList<AppLockInfo>();
		List<PackageInfo> arrPkgInfo=getPackageManager().getInstalledPackages(0);
		APPLockDao appLockDao=new APPLockDao(SecretActivity.this);
		for(PackageInfo pkgInfo:arrPkgInfo){
			AppLockInfo appLockInfo=new AppLockInfo();
			appLockInfo.appNmae=pkgInfo.applicationInfo.loadLabel(getPackageManager()).toString();
			appLockInfo.appIcon=pkgInfo.applicationInfo.loadIcon(getPackageManager());
			appLockInfo.pkgName=pkgInfo.packageName;
			appLockInfo.lock=appLockDao.exist(pkgInfo.packageName);
			arrAppLockInfos.add(appLockInfo);
		}
		return arrAppLockInfos;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.secret, menu);
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
	class AppLockAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrAppLock.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrAppLock.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			AppLockInfo appLockInfo=(AppLockInfo) getItem(position);
			ViewHolder viewHolder=null;
			if(convertView==null){
				convertView=View.inflate(SecretActivity.this,R.layout.item_applock,null);
				viewHolder=new ViewHolder();
				viewHolder.ivAppIcon=(ImageView) convertView.findViewById(R.id.iv_appicon);
				viewHolder.tvAppNameTextView=(TextView) convertView.findViewById(R.id.tv_appname);
				viewHolder.ivLock=(ImageView) convertView.findViewById(R.id.iv_lock);
				convertView.setTag(viewHolder);
			}
			else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			viewHolder.ivAppIcon.setImageDrawable(appLockInfo.appIcon);
			viewHolder.tvAppNameTextView.setText(appLockInfo.appNmae);
			if(appLockInfo.lock){
				viewHolder.ivLock.setVisibility(View.VISIBLE);
			}else{
				viewHolder.ivLock.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}
		
	}
	class ViewHolder{
		ImageView ivAppIcon;
		TextView tvAppNameTextView;
		ImageView ivLock;
	}
	class AppLockInfo{
		Drawable appIcon;
		String appNmae;
		String pkgName;
		boolean lock;
	}
}
