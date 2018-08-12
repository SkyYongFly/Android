package com.example.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daomain.BlackNumInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzas on 2015/10/6.
 */
public class BlacknumSQLDao {

    BlackNumberDB  openHelper ;
    public BlacknumSQLDao(Context context ){
        openHelper = new BlackNumberDB(context);

    }

    /**
     * 查询黑名单数据库所有的数据
     * @return
     */
    public List<BlackNumInfo> queryAll(){
        SQLiteDatabase  database  = openHelper.getReadableDatabase();
        if(database.isOpen()) {
            Cursor cursor = database.rawQuery("select * from blackNum ", null);
            if (cursor != null && cursor.getCount() > 0) {
                Log.d("test", "cursor不为空");
                List<BlackNumInfo> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String number = cursor.getString(1);
                    String mode = cursor.getString(2);
                    BlackNumInfo num = new BlackNumInfo(number,mode);
                    list.add(num);
                }
                cursor.close();
                database.close();

                return list;
            }
        }
        if(database !=null){
            database.close();
        }
        Log.d("test", "数据库为空");
        return  null;
    }

    /**
     * 查询黑名单数据库一部分的数据
     * @return
     */
    public List<BlackNumInfo> queryPart(int start,int max){
        SQLiteDatabase  database  = openHelper.getReadableDatabase();
        if(database.isOpen()) {
            Cursor cursor = database.rawQuery("select * from blackNum  limit ?,?", new String[]{start+"",""+start+max});
            if (cursor != null && cursor.getCount() > 0) {
                Log.d("test", "cursor不为空");
                List<BlackNumInfo> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String number = cursor.getString(1);
                    String mode = cursor.getString(2);
                    BlackNumInfo num = new BlackNumInfo(number,mode);
                    list.add(num);
                }
                cursor.close();
                database.close();

                return list;
            }
        }
        if(database !=null){
            database.close();
        }
        Log.d("test", "数据库为空");
        return  null;
    }

    /**
     * 增加黑名单
     * @param number
     */
    public void add(BlackNumInfo   number){
           SQLiteDatabase database = openHelper.getWritableDatabase();
        if(database.isOpen()){
            database.execSQL("insert into blackNum values(null,?,?)",new Object[]{number.getBlackNumber(),number.getMode()});
            database.close();
        }

    }

    /**
     * 修改一条记录
     * @param number
     */
    public void update(BlackNumInfo  number){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        if(database.isOpen()){
            database.execSQL("update blackNum set mode=? where number=?",new Object[]{number.getMode(),number.getBlackNumber()});
            database.close();
        }


    }

    /**
     * 删除一条记录
     * @param number
     */
    public void delete(String number){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        if(database.isOpen()){
            database.execSQL("delete from blackNum  where number=?",new Object[]{number});
            database.close();
        }
    }

    /**
     * 根据手机号码查询出他被拦截的模式
     * @param sender
     * @return
     */

    public String findMode(String sender) {
        String mode = null;
        SQLiteDatabase  database = openHelper.getReadableDatabase();
        if(database.isOpen()){
            Cursor  cursor = database.rawQuery("select mode from blackNum where number=?", new String[]{sender});
            if(cursor!=null && cursor.getCount()>0){
                while(cursor.moveToNext()){
                    mode  = cursor.getString(0);
                }
                cursor.close();
                database.close();

                return mode;
            }
        }
        if(database!=null){
            database.close();
        }
        return null;
    }
}
