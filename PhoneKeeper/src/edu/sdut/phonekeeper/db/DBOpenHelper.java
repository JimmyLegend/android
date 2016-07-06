package edu.sdut.phonekeeper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "PhoneKeeper.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建黑名单数据表
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ "BlackNum("
				+ "_id integer primary key autoincrement,"
				+ "number Text"
				+ ");");
		
		// 创建拦截记录数据表
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ "SMSFilter("
				+ "_id integer primary key autoincrement,"
				+ "content Text,"
				+ "number Text,"
				+ "time integer"
				+ ");");
		
		// 应用锁名单
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ "APPLock("
				+ "_id integer primary key autoincrement,"
				+ "pkgName Text"
				+ ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion ==0 ){
			SQLiteDatabase db1 = getWritableDatabase();

			// 插入测试黑名单
			for (int i = 0; i < 5; i++) {
				ContentValues values = new ContentValues();
				values.put("number", "1000" + i);
				db1.insert("BlackNum", null, values);
			}
			
			// 插入短信数据
			ContentValues values = new ContentValues();
			values.put("context", "我是一条被拦截的短信");
			values.put("number", "10001");
			values.put("time", 123123123L);
			db1.insert("SMSFilter", null, values);
			
			db1.close();
		}
	}

}
