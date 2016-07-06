package com.example.phonekeeper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Currency;

import android.R.integer;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
//import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BackupActivity extends Activity {

	private Button btnBackup;
	private Button btnRestore;
	private String filePath="/mnt/sdcard/text.txt";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);
		btnBackup=(Button) findViewById(R.id.btn_backup);
		btnRestore=(Button) findViewById(R.id.btn_restore);
		btnBackup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String contacts=readContacts();
				writeFile(contacts);
			}
		});
		btnRestore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String contacts=readFile();
				writeContacts(contacts);
			}
		});
	}
	private String readFile(){
		String contacts="";
		try {
			File file=new File(filePath);
			FileInputStream inStream=new FileInputStream(file);
			ByteArrayOutputStream outStream=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024 * 5];
			int length=-1;
			while((length=inStream.read(buffer))!=-1){
				outStream.write(buffer,0,length);
			}
			outStream.close();
			inStream.close();
			contacts=outStream.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return contacts;
	}
	//保存到文件
	private void writeFile(String contacts){
		File saveFile=new File(filePath);
		FileOutputStream outStream;
		try {
			outStream=new FileOutputStream(saveFile);
			outStream.write(contacts.getBytes());
			outStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private String readContacts(){
		String contacts="";
		Cursor cur=getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if(cur.moveToFirst()){
			int idColumn=cur.getColumnIndex(ContactsContract.Contacts._ID);
			int displayNameColumn=cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			do {
				String contactID=cur.getString(idColumn);
				String displayName=cur.getString(displayNameColumn);
				contacts+=displayName;
				int phoneCount=cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if(phoneCount>0){
					Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactID, null,null);
					int i=0;
					String phoneNumble;
					if(phones.moveToFirst()){
						do {
							i++;
							phoneNumble=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							if(i==1)
								contacts=contacts+","+phoneNumble;
								System.out.println(phoneNumble);
						} while (phones.moveToNext());
					}
					contacts+="&";
				}
			} while (cur.moveToNext());
		}
		
		return contacts;
	}
	private void writeContacts(String contacts){
		System.out.println(contacts);
		String[] arrContacts=contacts.split("&");
		for(String contact:arrContacts){
			if(contact==null||contact.length()==0){
				continue;
			}
			String[] value=contact.split(",");
			ContentValues values=new ContentValues();
			Uri rawContentUri=getContentResolver().insert(RawContacts.CONTENT_URI, values);
			long rawContentID=ContentUris.parseId(rawContentUri);
			values.clear();
			values.put(Data.RAW_CONTACT_ID,rawContentID);
			values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
			values.put(StructuredName.GIVEN_NAME, value[0]);
			getContentResolver().insert(Data.CONTENT_URI, values);
			values.clear();
			values.put(Data.RAW_CONTACT_ID, rawContentID);
			values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
			values.put(Phone.NUMBER, value[1]);
			values.put(Phone.TYPE,Phone.TYPE_HOME);
			getContentResolver().insert(Data.CONTENT_URI, values);//Data.CONTENT_URI出错
			
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.backup, menu);
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
