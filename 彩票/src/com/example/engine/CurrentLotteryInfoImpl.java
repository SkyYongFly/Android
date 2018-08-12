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
		// ��Ҫ����Ĳ���id��ӵ��ڵ���
		XmlElement xmlElement = new XmlElement();
		xmlElement.setLotteryidTag(lotteryId + "");

		// ���������xml�ļ�
		XmlUtil xmlUtil = new XmlUtil();
		xmlUtil.serializerXML();
		String xmlStr = xmlUtil.getXml();

		// ���ø��෽�����������ȡ���ص���Ϣ
		Message result = super.getCurrentLottoryInfo(xmlStr);

		// ���ж��ν�����������������body����
		if (result != null) {
			XmlPullParser pullParser = Xml.newPullParser();

			Message message = new Message();

			// body���ģ�����+����DES��
			DES des = new DES();
			// ----��ȡbody����
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
