package com.example.util;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

/**
 * 处理XML文件的中间的消息体
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
			serializer.attribute(null, "version","1.0");//版本信息
			
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
