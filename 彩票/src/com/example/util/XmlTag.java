package com.example.util;

import org.xmlpull.v1.XmlSerializer;

import android.text.TextUtils;

/**
 * 生成一个简单的XML的节点
 * @author yzas
 *
 */
public class XmlTag {
	//节点名称
	private String tagName;
	//节点对应的文本信息
	private String tagValue;
	
	public XmlTag(String tagName) {
		super();
		this.tagName = tagName;
	}

	public XmlTag(String tagName, String tagValue) {
		super();
		this.tagName = tagName;
		this.tagValue = tagValue;
	}

	public String getTagValue(){
		return tagValue;
	}

	public void setTagValue(String tagValue){
		this.tagValue =tagValue;
	}


	/**
	 * 序列化一个简单的叶子节点
	 * @param serializer
	 */
	public void serializerTag(XmlSerializer serializer) {
		try{
			serializer.startTag(null, tagName);
			if(TextUtils.isEmpty(tagValue)){
				tagValue="";
			}
			serializer.text(tagValue);
			serializer.endTag(null, tagName);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
