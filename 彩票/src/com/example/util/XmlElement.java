package com.example.util;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

/**
 * XML body������elements������element�ڵ�
 * 
 * @author yzas
 *
 */
public class XmlElement {
//	private  static List<XmlTag> list = new ArrayList<XmlTag>();
//
//	public  static void addElementTag(XmlTag xmlTag) {
//		list.add(xmlTag);
//	}
	

	
	private XmlTag lotteryidTag = new XmlTag("lotteryid");
	private XmlTag issuesTag  = new XmlTag("issues","1");
	private XmlTag lotterynameTag  = new XmlTag("lotteryname");
	private XmlTag lasttimeTag  = new XmlTag("lasttime");
	
	
	public void setLotteryidTag(String tagValue){
		lotteryidTag.setTagValue(tagValue);;
	}
	
	public XmlTag getLotteryidTag(){
		return lotteryidTag;
	}
	
	public void setIssuesTag(String tagValue){
		issuesTag.setTagValue(tagValue);
	}
	
	public XmlTag getIssuesTag(){
		return issuesTag;
	}
	
	public void setLotterynameTag(String name){
		lotterynameTag.setTagValue(name);
	}
	
	public XmlTag getLotterynameTag(){
		return lotterynameTag;
	}
	
	public void setLasttimeTag(String lastTime){
		lasttimeTag.setTagValue(lastTime);
	}
	
	public XmlTag getLastTimeTag(){
		return lasttimeTag;
	}
	

	// ���л�element�ڵ�
	public void serializerElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");

//			for (XmlTag tag : list) {
//				tag.serializerTag(serializer);
//			}
			
			lotteryidTag.serializerTag(serializer);
			issuesTag.serializerTag(serializer);
			
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
