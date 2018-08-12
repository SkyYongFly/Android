package com.example.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yzas on 2015/10/7.
 */
public class SmsUtils {

    /**
     * 用于回调的接口
     *
     * @param context
     * @throws Exception
     */
    public interface BackUpCallBack {
        /**
         * 备份之前调用的方法，用于设置进度条的最大值
         *
         * @param max
         */
        public void beforeBackup(int max);

        /**
         * 备份中调用，用于设置进度条进度
         *
         * @param progress
         */
        public void onBackup(int progress);

    }

    public static void saveSms(Context context, BackUpCallBack callBack) throws Exception {
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "date", "type", "body"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            XmlSerializer serializer = Xml.newSerializer();
            FileOutputStream out = new FileOutputStream(new File(context.getFilesDir(), "saveSms.xml"));
            serializer.setOutput(out, "utf-8");

            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "Smses");
            serializer.attribute(null, "counts", "" + cursor.getCount());
            //开始备份时设置进度条最大值
            int max = cursor.getCount();

            int progress = 0;

            while (cursor.moveToNext()) {
                serializer.startTag(null, "sms");

                serializer.startTag(null, "_id");
                serializer.text(cursor.getString(0));
                serializer.endTag(null, "_id");

                serializer.startTag(null, "address");
                serializer.text(cursor.getString(1));
                serializer.endTag(null, "address");

                serializer.startTag(null, "date");
                serializer.text(cursor.getString(2));
                serializer.endTag(null, "date");

                serializer.startTag(null, "type");
                serializer.text(cursor.getString(3));
                serializer.endTag(null, "type");

                serializer.startTag(null, "body");
                serializer.text(cursor.getString(4));
                serializer.endTag(null, "body");

                serializer.endTag(null, "sms");

                progress++;
            }
            serializer.endTag(null, "Smses");
            serializer.endDocument();
        } else {

        }
        if(cursor !=null){
            cursor.close();
        }
    }

    /**
     * 将短信从备份文件中还原到手机
     */
    public static void getbackSms(Context context) {
        try {
            Uri uri = Uri.parse("content://sms/inbox");

            //读取备份文件
            FileInputStream inputStream = new FileInputStream(new File(context.getFilesDir(), "saveSms.xml"));
            //解析xml文件
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "utf-8");
            int eventType = parser.getEventType();

            ContentValues values = new ContentValues();//这一句不能写在循环里面
            while (eventType != XmlPullParser.END_DOCUMENT) {

                //获取当前节点名称
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("Smses".equals(name)) {

                        } else if ("sms".equals(name)) {
                            values = new ContentValues();
                        } else if ("_id".equals(name)) {

                        } else if ("address".equals(name)) {
                            String address = parser.nextText();
                            Log.d("test","address"+address);
                            values.put("address", address);
                        } else if ("date".equals(name)) {
                           values.put("date", parser.nextText());
                        } else if ("type".equals(name)) {
                            values.put("type", parser.nextText());
                        } else if ("body".equals(name)) {
                            String body = parser.nextText();
                            Log.d("test","body"+body);
                            values.put("body", body);
                        }

                    }
                    break;

                    case XmlPullParser.END_TAG: {
                        if ("sms".equals(name)) {
                            Log.d("test",values.get("body")+"   "+values.get("address"));
                            context.getContentResolver().insert(uri, values);
                        }
                    }
                    break;

                }
                eventType = parser.next();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw  new RuntimeException("1");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("2");
        }
    }
}
