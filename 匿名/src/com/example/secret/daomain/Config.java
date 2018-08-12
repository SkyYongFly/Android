package com.example.secret.daomain;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 系统配置信息类
 * @author yzas
 *
 */
public class Config {
	//链接网络的地址
	public  final static String  URI= "http://10.50.12.250:8080/Secret/secret";
	
	//本地数据存储文件
	private final  static String APP_ID ="com.example.secret";
	//token的保存建制对建的名称
	private final static  String CacheToken ="token";
	//编码
	public final static String CHARSET = "UTF-8";
	//指明提交的内哦是电话号码
	public final static String Phone_NUM = "phoneNum";
	//登陆后缓存到本地的手机号码
   public  static final String CACHE_PHONE = "cache_phone";

	public static final String PHONE_NUM_MD5 = "phone_num_md5";

	//告诉服务器上传的是手机号码列表
	public static final String CONTACTS_PHONE = "contacts_phone";

	//上传服务器的内容类型
	public static final String CONNECT_ACTION ="action";
	public static final String ACTION_SEND_PHONENUM ="sendPhoneList";
	public static final String ACTION_REQUEST_CODE ="requestCode";
	public static final String ACTION_REQUEST_MESSAGE ="requestMessage";
	
	//上传手机号码后服务器的返回数据
	public static final String RETURN ="sendPhoneListBack";
	
	public static final String REQUEST_MESSAGE_PAGE ="request_message_page";
	
	
	
	
	/**
	 * 获取缓存在本地的token
	 * @param context
	 * @return
	 */
	public static String getCacheToken(Context context){
		return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(CacheToken,null);
	}
	
	
	/**
	 * 将token数据保存到本地
	 * @return
	 */
	public  static void saveToken(Context context,String token){
		Editor  editor = context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).edit();
		editor.putString(CacheToken,token);
		editor.commit();
	}


	/**
	 * 获取缓存到本地的手机号码
	 * @param context
	 * @return
	 */
	public static String getCachePhone(Context context) {
		return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(CACHE_PHONE,null);
	}

}
