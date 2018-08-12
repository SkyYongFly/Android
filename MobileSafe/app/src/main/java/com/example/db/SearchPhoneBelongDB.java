package com.example.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

/**
 * 查询手机号码归属地
 * Created by yzas on 2015/10/5.
 */
public class SearchPhoneBelongDB {

    /**
     * 查询
     * @param phoneNum  待查询的号码
     * @return          号码归属地
     */
    public static String search(Context context,String phoneNum){
        String location = null;
        //获取数据库路径
        File file = new File(context.getFilesDir(),"address.db");
        String  path = file.getAbsolutePath();
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        if(phoneNum.matches("^1[34568]\\d{9}$")){//判断手机位数，这里是11位
            Cursor cursor = database.rawQuery("select  location  from data2  where id=(select outkey from data1  where id=?)", new String[]{phoneNum.substring(0,7)});
            if(cursor!=null && cursor.getCount()>0){
                while(cursor.moveToNext()){
                    location = cursor.getString(0);
                }
                cursor.close();
            }else{
                location = "无法识别1";
            }
        }else if(phoneNum.length() == 3){
            switch (Integer.parseInt(phoneNum)){
                case 110 : location = "匪警电话";break;
                case 119 : location = "火警电话";break;
                case 120 : location = "急救电话";break;
                default: break;
            }
        }else if(phoneNum.length() == 4){
                location = "模拟器";
        }else if(phoneNum.length() == 5){
                location = "客服号码";
        }else{
                location = "无法识别";//数据库有限，这里只是简单说明一下，实际需要增加数据库查询
        }

        database.close();
        return  location;
    }

}
