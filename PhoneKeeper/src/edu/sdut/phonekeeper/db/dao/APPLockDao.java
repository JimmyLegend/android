package edu.sdut.phonekeeper.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.sdut.phonekeeper.db.DBOpenHelper;

public class APPLockDao {
private DBOpenHelper helper;
	
	public APPLockDao(Context context){
		helper = new DBOpenHelper(context);
	}
	
	// 查询包名
	public boolean exist(String pkgName){
		boolean result = false;
		
		// 获取数据库
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select * from APPLock where pkgName=?", new String[]{pkgName});
		if(cursor.moveToNext()){
			result = true;
		}
		
		cursor.close();
		db.close();
		
		return result;
	}
	// 插入包名
	public void add(String pkgName){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("pkgName", pkgName);
		db.insert("APPLock", null, values);
		db.close();
	}
	// 删除包名
	public void delete(String pkgName){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("APPLock",  "pkgName=?", new String[]{pkgName});
		db.close();
	}
}
