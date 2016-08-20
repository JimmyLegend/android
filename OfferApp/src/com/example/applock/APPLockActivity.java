package com.example.applock;
import com.example.clock.R;

import edu.sdut.offerapp.db.APPLockConfig;
import edu.sdut.offerapp.db.APPLockService;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class APPLockActivity extends Activity {

	private ImageView ivAppIcon;
	private TextView tvAppNme;
	private EditText etPassword;
	private Button btnUnLock;
	private String pkgNme;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applock);
		ivAppIcon=(ImageView) findViewById(R.id.iv_appicon);
		tvAppNme=(TextView) findViewById(R.id.tv_appname);
		etPassword=(EditText) findViewById(R.id.et_password);
		btnUnLock=(Button) findViewById(R.id.btn_unlock);
		pkgNme=getIntent().getStringExtra("PkgName");
		PackageManager pm=getPackageManager();
		try {
			ApplicationInfo app=pm.getApplicationInfo(pkgNme,0);
			ivAppIcon.setImageDrawable(app.loadIcon(pm));
			tvAppNme.setText(app.loadLabel(pm));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		btnUnLock.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password=etPassword.getText().toString();
				APPLockConfig appLockConfig=new APPLockConfig(APPLockActivity.this);
				if(password.equals(appLockConfig.getPassWord())){
					//APPLockDao.oldpkgNme=pkgNme;
					APPLockService.oldPkgName=pkgNme;
					APPLockActivity.this.finish();
				}
				else {
					Toast.makeText(APPLockActivity.this,"√‹¬Î¥ÌŒÛ£°",Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.applock, menu);
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
