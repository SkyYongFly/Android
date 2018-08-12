package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 保存开启应用程序锁的应用
 * Created by yzas on 2015/10/10.
 */
public class LockAppDB  extends SQLiteOpenHelper{
    public LockAppDB(Context context) {
        super(context,"lockApp.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  sql = new String("create table  lockApp (id integer  primary key  autoincrement ,packageName  varchar(100))");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
