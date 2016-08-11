package com.example.notebooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper{
	 public static final String TABLE_NAME = "notes";
	    public static final String CONTENT = "content";
	    public static final String ID = "_id";
	    public static final String TIME = "time";
	    public static final String VIDEO = "video";
	    public static final String PATH = "path";
	    public static final String NAME = "name";
	    public static final String MUSIC = "music";

	    public NotesDB(Context context) {
	        super(context, "Note.db", null, 1);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase sqLiteDatabase) {
	        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("
	                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	                + CONTENT + " TEXT NOT NULL,"
	                + TIME + " TEXT NOT NULL,"
	                + VIDEO + " TEXT,"
	                + NAME + " TEXT,"
	                + MUSIC + " TEXT,"
	                + PATH + " TEXT)");
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	    }

}
