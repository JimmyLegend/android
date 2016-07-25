package com.example.offerapp;

import edu.sdut.offerapp.db.DBOpenHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
private EditText userEditText;
private EditText passwordEditText;
private Button loginButton;
private Button registerButton;
private DBOpenHelper helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		helper=new DBOpenHelper(LoginActivity.this);
		userEditText=(EditText) findViewById(R.id.user_edit);
		passwordEditText=(EditText) findViewById(R.id.password_edit);
		loginButton=(Button) findViewById(R.id.login_button);
		registerButton=(Button) findViewById(R.id.register_button);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number=userEditText.getText().toString();
				String password=passwordEditText.getText().toString();
				if(number.equals("")||password.equals("")){
					Toast.makeText(LoginActivity.this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
				}
				else if(exist(number, password)){
					Intent intent=new Intent(LoginActivity.this,WelcomeActivity.class);
					startActivity(intent);
					Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(LoginActivity.this,"密码或用户名不正确！",Toast.LENGTH_SHORT).show();
				}
			}
		});
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	// 查询号码
		public boolean exist(String number,String password){
			boolean result = false;
			
			// 获取数据库
			SQLiteDatabase db = helper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("select * from User where number=?", new String[]{number});
			Cursor cursor1 = db.rawQuery("select * from User where password=?", new String[]{password});
			if(cursor.moveToNext()&&cursor1.moveToNext()){
				result = true;
			}
			
			cursor.close();
			db.close();
			
			return result;
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
