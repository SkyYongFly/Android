package com.example.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzas on 2015/10/10.
 */
public class LockAppSQLDao {
    LockAppDB  lockAppDB;
    public LockAppSQLDao(Context context){
        lockAppDB = new LockAppDB(context);
    }

    /**
     * 添加锁定的应用程序信息
     * @param packageName
     */
    public  void add(String packageName){
        SQLiteDatabase database = lockAppDB.getWritableDatabase();
        if(database.isOpen()){
            database.execSQL("insert into lockApp values (null,?)",new String[]{packageName});
        }

        if(database!=null){
            database.close();
        }

    }

    /**
     * 将解锁的应用程序从数据库中移出
     */
    public void delete(String packageName){
        SQLiteDatabase database = lockAppDB.getWritableDatabase();
        if(database.isOpen()){
            database.execSQL("delete  from lockApp where packageName=?",new String[]{packageName});
        }

        if(database!=null){
            database.close();
        }
    }

    /**
     * 查询某个应用程序是否锁起来了
     * @param packageName
     * @return
     */
    public boolean  find(String packageName){
        SQLiteDatabase database = lockAppDB.getWritableDatabase();
        if(database.isOpen()){
            Cursor cursor = database.rawQuery("select * from lockApp where packageName=?", new String[]{packageName});
            if(cursor!=null && cursor.getCount()>0){
                return  true;
            }
        }

        if(database!=null){
            database.close();
        }

        return  false;
    }

    /**
     * 查询出所有的被锁定的应用程序
     * @return  包含应用程序包名的集合
     */
    public  List<String> findAll(){
        SQLiteDatabase database = lockAppDB.getWritableDatabase();
        if(database.isOpen()){
            Cursor cursor = database.rawQuery("select * from lockApp ", null);
            List<String> list = new ArrayList<>();
            if(cursor!=null && cursor.getCount()>0){
                while(cursor.moveToNext()) {
                    int index = cursor.getColumnIndex("packageName");
                    Log.d("test",""+index+"列");
                    String packageName = cursor.getString(index);
                    list.add(packageName);
                }
                cursor.close();
                return  list;
            }
        }

        if(database!=null){
            database.close();
        }

        return  null;

    }



}
