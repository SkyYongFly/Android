package com.example.engine;

import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import com.example.daomain.Config;
import com.example.net.HttpClientUtil;
import com.example.util.DES;
import com.example.util.Message;

import android.util.Xml;

public  abstract class BaseEngine {
	
	// ���ڻ�ȡ���µĲ�Ʊ��Ϣ
	public Message getCurrentLottoryInfo(String xmlString){
		try {
			HttpClientUtil clientUtil = new HttpClientUtil();
			InputStream iStream = clientUtil.sendXml(Config.URI, xmlString);

			if (iStream != null) {
				XmlPullParser pullParser = Xml.newPullParser();
				pullParser.setInput(iStream, Config.CHARSET);
				
				Message message = new Message();
				
				int eventType = pullParser.getEventType();
				String name;
				while(eventType != XmlPullParser.END_DOCUMENT){
					switch (eventType) {
					case XmlPullParser.START_TAG:{
						name = pullParser.getName();
						if("timestamp".equals(name)){
							message.getHeader().getTimestamp().setTagValue(pullParser.nextText());
						}else if("digest".equals(name)){
							message.getHeader().getDigest().setTagValue(pullParser.nextText());
						}else if("body".equals(name)){
							message.getBody().setBodyDESInfo(pullParser.nextText());
							
						}
					}
						
						break;

					default:
						break;
					}
					
					eventType = pullParser.next();
				}
				
				
				// ԭʼ���ݻ�ԭ��ʱ�����������+���루������+body���ģ�����+����DES��
				// body���ģ�����+����DES��
				DES  des = new DES();
				//----��ȡbody����
				String body = "<body>"+des.authcode(message.getBody().getBodyDESInfo(),"ENCODE",Config.digest)+"<body>";
				//----ƴ�ӻ�ȡԭʼ����
				String bodyInfo = message.getHeader().getTimestamp().getTagValue()+Config.AGENTER_PASSWORD+body;
				
				//����MD5�룬�ͱ�������У��
				String  md5Str = DigestUtils.md5Hex(bodyInfo);
				if(md5Str.equals(message.getHeader().getDigest().getTagValue())){
					return message;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
