package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.example.daomain.Friend;
import com.example.qq.Config;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

public class FriendUtil {

	public static List<Friend> getFriends(Context context) {
		try {
			List<Friend> list = null;
			Friend friend = null;
			XmlPullParser parser = Xml.newPullParser();
			FileInputStream input = new FileInputStream(new File(Environment.getExternalStorageDirectory(), "friend"+Config.getCachePhoneNum(context)+".xml"));
			parser.setInput(input, Config.CHARSET);

			int eventType = parser.getEventType();
			while (eventType != parser.END_DOCUMENT) {
				//获取当前节点名称
                String name = parser.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("Friends".equals(name)){
                                list = new ArrayList<Friend>();
                        }else if("friend".equals(name)){
                        	 friend = new Friend();
                        }else if("phoneNum".equals(name)){
                              friend.setPhoneNum(parser.nextText());
                        }else if("nickName".equals(name)){
                            friend.setNickName(parser.nextText());
                        }else if("sign".equals(name)){
                        	friend.setSign(parser.nextText());
                        }
                    }

                        break;
                    case  XmlPullParser.END_TAG:
                        if("friend".equals(name)){
                            list.add(friend);
                        }

                        break;

                }
                eventType = parser.next();

			}
			input.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
