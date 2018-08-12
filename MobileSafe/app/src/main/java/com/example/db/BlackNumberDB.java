package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yzas on 2015/10/6.
 */
public class BlackNumberDB  extends SQLiteOpenHelper{
    public BlackNumberDB(Context context) {
        super(context,"blackNumber.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  sql = new String("create table  blackNum (id integer  primary key  autoincrement ,number  varchar(20),mode varchar(5))");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
