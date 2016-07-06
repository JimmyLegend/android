package edu.sdut.phonekeeper.service;

import com.example.phonekeeper.APPLockActivity;

import edu.sdut.phonekeeper.db.dao.APPLockDao;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class APPLockService extends Service {
	public static String oldPkgName="";
	public void onCreate(){
		super.onCreate();
	}
	public void onStart(Intent intent,int startId){
		super.onStart(intent, startId);
		Log.i("·þÎñ","Æô¶¯");
		new Thread(){
			public void run(){
				do {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					ActivityManager am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
					ComponentName CurComName=am.getRunningTasks(1).get(0).topActivity;
					String runPkgName=CurComName.getPackageName();
					if(getPackageName().equals(runPkgName)){
						continue;
					}
					if(oldPkgName.equals(runPkgName)){
						continue;
					}
					APPLockDao appLockDao=new APPLockDao(APPLockService.this);
					if(appLockDao.exist(runPkgName)){
						showScreenLock(runPkgName);
					}
					else{
						oldPkgName="";
					}
				} while (true);
			};
		}.start();
	}
	public void showScreenLock(String pkgName){
		Intent intent=new Intent(this,APPLockActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("PkgName",pkgName);
		startActivity(intent);
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
