package com.example.secret.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.secret.daomain.Config;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class ContentsUtil {

	private static final String TAG ="test";

	/**
	 * 获取手机中的手机号码
	 * @param context
	 * @return
	 */
	public static String getPhones(Context context) {
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,null,null,null,null);
		JSONArray jsonArray = new JSONArray();
		if(cursor!=null && cursor.getCount()>0){
			while(cursor.moveToNext()){
			  String phoneNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			  Log.d(TAG,phoneNum);
			  JSONObject jsonObject = new JSONObject();
			  try {
				jsonObject.put(Config.PHONE_NUM_MD5,MD5Util.md5(phoneNum));
				jsonArray.put(jsonObject);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			}
		}
		
		return jsonArray.toString();
	}

}
