package com.example.util;

import java.io.BufferedWriter;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import com.example.daomain.Config;

import android.util.Xml;

/**
 * XML�ļ������л�
 * 
 * @author yzas
 *
 */
public class XmlUtil {

	private StringWriter writer;

	/**
	 * ����XML�ļ�
	 */
	public void serializerXML() {
		try {
			XmlSerializer serializer = Xml.newSerializer();
			writer = new StringWriter();
			serializer.setOutput(writer);
			serializer.startDocument(Config.CHARSET,null);
			
			//����message
			Message.createMessage(serializer);
			
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ��ȡ���л����ɵ�XML�ļ�
	 * @return
	 */
	public String getXml(){
		return writer.toString();
	}

}
