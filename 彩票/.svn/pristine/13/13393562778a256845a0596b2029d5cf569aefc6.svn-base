package com.example.util;

import java.io.BufferedWriter;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import com.example.daomain.Config;

import android.util.Xml;

/**
 * XML文件的序列化
 * 
 * @author yzas
 *
 */
public class XmlUtil {

	private StringWriter writer;

	/**
	 * 创建XML文件
	 */
	public void serializerXML() {
		try {
			XmlSerializer serializer = Xml.newSerializer();
			writer = new StringWriter();
			serializer.setOutput(writer);
			serializer.startDocument(Config.CHARSET,null);
			
			//生成message
			Message.createMessage(serializer);
			
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 获取序列化生成的XML文件
	 * @return
	 */
	public String getXml(){
		return writer.toString();
	}

}
