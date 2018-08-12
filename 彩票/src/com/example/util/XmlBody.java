package com.example.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class XmlBody {
	
	private  static List<XmlElement> list = new ArrayList<XmlElement>();

	public   void addElementTag(XmlElement xmlElement) {
		list.add(xmlElement);
	}
	
	public List<XmlElement> getElements(){
		return list;
	}
	
	//服务器返回的加密的body内容
	private String bodyDESInfo ;
	//服务器返回的内容成功、失败标志
	private String errorcode;
	//服务器返回的内容成功、失败内容提示
	private String errormsg;
	
	public void setBodyDESInfo(String bodyDESInfo){
		this.bodyDESInfo = bodyDESInfo;
	}
	
	public String getBodyDESInfo(){
		return bodyDESInfo;
	}
	
	
	public void setErrorcode(String errorcode){
		this.errorcode = errorcode;
	}
	
	public void setErrormsg(String errormsg){
		this.errormsg = errormsg;
	}
	
	public String getErrormsg(){
		return errormsg;
	}
	

	/**
	 * 序列化body
	 * 
	 * @param serializer
	 */
	public void serializerBody(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "body");
			serializer.startTag(null, "elements");

//			XmlElement  element = new XmlElement();
//			element.serializerElement(serializer);
			
			for (XmlElement tag : list) {
				tag.serializerElement(serializer);
			}

			serializer.endTag(null, "elements");
			serializer.endTag(null, "body");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得序列化的body
	 */
	public String getBody() {
		StringWriter writer = new StringWriter();
		XmlSerializer serializer = Xml.newSerializer();
		try {
			serializer.setOutput(writer);
			this.serializerBody(serializer);
			serializer.flush();
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
