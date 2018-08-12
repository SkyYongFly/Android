package com.example.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.example.daomain.Friend;
import com.example.qq.Config;

import android.R.xml;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

/**
 *  ���ӷ�������ȡ�ĺ������ݱ��浽����
 * @author yzas
 *
 */
public class SaveFriends {
	
	private static final String TAG = "test";

	/**
	 * ��������Ϣ���л��������ļ�
	 * @param list
	 * @param context
	 */
	public static void save(List<Friend> list,Context context){
		try{
			Log.d(TAG,"��ʼ�洢��������");
		XmlSerializer serializer = Xml.newSerializer();
		File file = new File(Environment.getExternalStorageDirectory(),"friend"+Config.getCachePhoneNum(context)+".xml");
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		
		serializer.setOutput(out, Config.CHARSET);
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "Friends");
		
		for(Friend f:list){
			serializer.startTag(null, "friend");
			
			serializer.startTag(null,"phoneNum");
			serializer.text(f.getPhoneNum());
			serializer.endTag(null, "phoneNum");
			
			serializer.startTag(null,"nickName");
			serializer.text(f.getNickName());
			serializer.endTag(null, "nickName");
			
			serializer.startTag(null,"sign");
			serializer.text(f.getSign());
			serializer.endTag(null, "sign");
			
			
			serializer.endTag(null, "friend");
		}
		
		serializer.endTag(null, "Friends");
		serializer.endDocument();
		
		out.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
}
