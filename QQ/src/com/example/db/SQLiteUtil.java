package com.example.db;

import java.util.ArrayList;
import java.util.List;

import com.example.daomain.DataMessage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;

public class SQLiteUtil {
	private   MySQLite mySQLite ;
	
	public SQLiteUtil(Context context){
		mySQLite = new MySQLite(context);
	}
	public  void addMessage(DataMessage message){
		SQLiteDatabase database =  mySQLite.getWritableDatabase();
		database.execSQL("insert into message values (null,?,?,?,?,null,?)",
																	new String[]{message.getType(),
																						message.getPersonal(),
																						message.getDesphone(),
																						message.getBody(),
																						message.getHasread()});
		database.close();
	}
	
	public boolean exist(DataMessage message){
		SQLiteDatabase database = mySQLite.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from message where type =? and desphone=? and hasread =? and body=?", 
				new String[]{message.getType(),message.getDesphone(),message.getHasread(),message.getBody()});
		boolean result =false;
		if(cursor!=null && cursor.getCount()>0){
			result= true;
		}
		database.close();
		return result;
	}
	
	/**
	 * 获取未读的信息
	 * @return
	 */
	public List<DataMessage> getUnReadMessage() {
		SQLiteDatabase database = mySQLite.getReadableDatabase();
		List<DataMessage> list  =null;
		Cursor cursor = database.rawQuery("select * from message where hasread =?",new String[]{"1"} );
		if(cursor!=null && cursor.getCount()>0){
			list = new ArrayList<DataMessage>();
			while(cursor.moveToNext()){
				DataMessage message = new DataMessage();
				message.setType(cursor.getString(cursor.getColumnIndex("type")));
				message.setPersonal(cursor.getString(cursor.getColumnIndex("personal")));
				message.setDesphone(cursor.getString(cursor.getColumnIndex("desphone")));
				message.setBody(cursor.getString(cursor.getColumnIndex("body")));
				message.setHasread(cursor.getString(cursor.getColumnIndex("hasread")));
				message.toString();
				list.add(message);
			}
		}
		database.close();
		return list;
	}

}
