package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {

	public MySQLite(Context context) {
		super(context, "qqmessage.db", null, 1,null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table  message"
				+" ("
				+"id integer primary key autoincrement,"
				+"type varchar(5),"
				+"personal  varchar(5),"
				+"desphone varchar(20),"
				+"body varchar(512),"
				+"time varchar(100),"
				+"hasread varchar(5)"
				+")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
