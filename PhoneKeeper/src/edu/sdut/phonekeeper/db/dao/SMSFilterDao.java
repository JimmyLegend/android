package edu.sdut.phonekeeper.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.sdut.phonekeeper.db.DBOpenHelper;
import edu.sdut.phonekeeper.domain.SMSInfo;

public class SMSFilterDao {

	private DBOpenHelper helper;

	public SMSFilterDao(Context context){
		helper = new DBOpenHelper(context);
	}
	// 查询所有号码
	public List<SMSInfo> findAll() {
		List<SMSInfo> list = new ArrayList<SMSInfo>();

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select _id,content,number,time from SMSFilter order by _id desc", null);
		while (cursor.moveToNext()) {
			int _id = cursor.getInt(0);
			String context = cursor.getString(1);
			String num = cursor.getString(2);
			long time = cursor.getLong(3);

			SMSInfo sms = new SMSInfo(_id, num, context, time);

			list.add(sms);
		}

		cursor.close();
		db.close();

		return list;
	}
	// 插入
	public void add(SMSInfo sms) {

		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("content", sms.getContext());
		values.put("number", sms.getNum());
		values.put("time", sms.getTime());
		db.insert("SMSFilter", null, values);
		db.close();
	}

	// 删除
	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("SMSFilter", "_id=?", new String[] { ""+id });
		db.close();
	}
}
