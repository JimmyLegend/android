package edu.sdut.phonekeeper.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.sdut.phonekeeper.db.DBOpenHelper;
import edu.sdut.phonekeeper.domain.BlackNumInfo;

public class BlackNumberDao {
	private DBOpenHelper helper;
	
	public BlackNumberDao(Context context){
		helper = new DBOpenHelper(context);
	}
	
	// ��ѯ����
	public boolean exist(String number){
		boolean result = false;
		
		// ��ȡ���ݿ�
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select * from BlackNum where number=?", new String[]{number});
		if(cursor.moveToNext()){
			result = true;
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	// ��ѯ���к���
	public List<BlackNumInfo> findAll(){
		List<BlackNumInfo> list = new ArrayList<BlackNumInfo>();
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select _id,number from BlackNum order by _id desc", null);
		while(cursor.moveToNext()){
			int _id = cursor.getInt(0);
			String num = cursor.getString(1);
			BlackNumInfo numInfo = new BlackNumInfo(_id, num);
			
			list.add(numInfo);
		}
		
		cursor.close();
		db.close();
		
		return list;
	}
	// �������
	public void add(String number){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		db.insert("BlackNum", null, values);
		db.close();
	}
	// ɾ������
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("BlackNum",  "number=?", new String[]{number});
		db.close();
	}
}
