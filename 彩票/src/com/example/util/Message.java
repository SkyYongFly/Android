package com.example.util;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

/**
 * ����XML�ļ����м����Ϣ��
 * @author yzas
 *
 */
public class Message {

	private static final String TAG = "tag";
	private static XmlHeader header;
	private static XmlBody body;

	public  static  void createMessage(XmlSerializer serializer){
		try {
			serializer.startTag(null, "message");
			serializer.attribute(null, "version","1.0");//�汾��Ϣ
			
			header = new XmlHeader();
			body = new XmlBody();
			String  bodyStr =  body.getBody();
			Log.d(TAG,"body : "+bodyStr);
			
			header.serializerHeader(serializer,bodyStr);
			body.serializerBody(serializer);
			
			serializer.endTag(null, "message");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	public XmlHeader getHeader(){
		return header;
	}
	
	public XmlBody getBody(){
		return body;
	}
}
