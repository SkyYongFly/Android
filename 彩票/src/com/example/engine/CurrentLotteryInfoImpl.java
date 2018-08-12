package com.example.engine;

import java.io.StringReader;
import java.security.PublicKey;
import java.security.spec.ECField;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.daomain.Config;
import com.example.util.DES;
import com.example.util.Message;
import com.example.util.XmlElement;
import com.example.util.XmlTag;
import com.example.util.XmlUtil;

import android.R.xml;
import android.util.Xml;

public class CurrentLotteryInfoImpl extends BaseEngine implements CurrentLotteryInfo {

	private XmlElement element;

	@Override
	public Message getLottryInfoAtId(Integer lotteryId) {
		// 将要请求的彩种id添加到节点中
		XmlElement xmlElement = new XmlElement();
		xmlElement.setLotteryidTag(lotteryId + "");

		// 生成请求的xml文件
		XmlUtil xmlUtil = new XmlUtil();
		xmlUtil.serializerXML();
		String xmlStr = xmlUtil.getXml();

		// 利用父类方法链接网络获取返回的信息
		Message result = super.getCurrentLottoryInfo(xmlStr);

		// 进行二次解析，解析的内容是body明文
		if (result != null) {
			XmlPullParser pullParser = Xml.newPullParser();

			Message message = new Message();

			// body明文（解析+解密DES）
			DES des = new DES();
			// ----获取body明文
			String body = "<body>" + des.authcode(result.getBody().getBodyDESInfo(), "ENCODE", Config.digest)
					+ "<body>";

			try {
				pullParser.setInput(new StringReader(body));
				String name;

				int eventType = pullParser.getEventType();

				while (eventType != pullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG: {
						name = pullParser.getName();
						if ("errorcode".equals(name)) {
							message.getBody().setErrorcode(pullParser.nextText());
						} else if ("errormsg".equals(name)) {
							message.getBody().setErrormsg(pullParser.nextText());
						} else if ("element".equals(name)) {
							element = new XmlElement();
							message.getBody().addElementTag(element);
						} else if ("issue".equals(name)) {
							if (element != null) {
								element.setIssuesTag(pullParser.nextText());
							}
						} else if ("lasttime".equals(name)) {
							if (element != null) {
								element.setLasttimeTag(pullParser.nextText());
							}
						}
					}
						break;

					default:
						break;
					}

					eventType = pullParser.next();
				}

				return message;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
