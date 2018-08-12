package com.example.qq;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * 应用配置类
 * 
 * @author yzas
 *
 */
public class Config {
	// 定义服务器地址
	public final static String SERVER_URL = "10.50.14.103";
	// 连接服务器的端口
	public static final int PORT = 1234;
	// 编码
	public static final String CHARSET = "UTF-8";
	// 服务器没有返回数据
	public static final String NO_BACK = "there is no data back";
	// 服务器返回的提示密码无效
	public static final Object NO_EFFECT_PASSWORD = "no effect password";
	// 服务器返回的提示手机号无效
	public static final Object NO_EFFECT_PHONE_NUM = "no effect phone number";

	// 定义本地的数据存储文件名
	private final static String SAVE_FILE = "com.example.qq.info";
	//保存好友信息的文件
	//public static final String FRIENDS_FILE= "friends.xml";
	// 缓存在本地的手机号码的键值对标示
	private final static String CACHE_PHONENUM = "cachePhoneNum";
	// 缓存在本地的密码键值对标识
	private final static String CACHE_PASSWORD = "cachePassword";

	// 指明传输给服务器的数据类型
	public static final String ACTION = "action";
	// 传说湖给服务器的数据类型是用来验证用户名和密码
	public static final String ACTION_CHECK_PASSWORD = "check_password";
	public static final String DATA_PHONENUM = "data_phoneNum";
	public static final String DATA_PASSWORD = "data_password";
	public  static final Object SUCCESS = "auto_login_success";
	//告诉服务器所传送的数据用来登陆的
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	public static final String SEARCH_FRIEND = "search_friend";
	public static final String ADD_FRIEND = "add_friend";
	public static final String GET_SIGN_AND_NICKNAME= "get_nickname_and_sign";
	public static final String RESPONSE_ADD_FRIEND = "response_add_friend";
	
	protected static final Object PHONE_NUM_HAS_EXISTS = "the phone has existed";
	protected static final Object REGISTER_FAIL = "register fail";
	protected static final Object LOGIN_ERROR = "login error";
	
	//查找好友
	public  static final String EXIST = "the user exist";
	public  static final String NOT_EXIST = "the user is not exist";
	public  static final String SEARCH_FAIL = "search user fail";
	
	//添加好友
	public static final String ADD_FRIEND_SUCCESS ="add friend success";
	public static final String HAS_ARE_FRIENDS ="the friend is exist";
	
	//查询昵称和签名
	public static final String GET_SIGN_FAIL ="get nickname and sign fail";
	public static final String ADD_FRIEND_PHONE ="add_friend_phone";
	
	//获取消息
	public static final String GET_NOREAD_MESSAGES = "get_no_read_message";
	public static final String NO_UNREAD_DATA = "no_data";
	
	
	public static final String DESPHONE = "des_phone";
	public static final String RESPONSE = "response";
	public static final String GET_FRIENDS = "get_friends";
	public static final String NO_FRIENDS = "no_friends";



	// 获取缓存在本地的手机号码
	public static String getCachePhoneNum(Context context) {
		return context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).getString(CACHE_PHONENUM, null);
	}

	// 获取缓存在本地的密码（MD5加密）
	public static String getCachePassword(Context context) {
		return context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).getString(CACHE_PASSWORD, null);
	}

	// 将登陆的手机号码缓存到本地
	public static void setCachePhoneNum(Context context, String phoneNum) {
		Editor editor = context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).edit();
		editor.putString(CACHE_PHONENUM, phoneNum);
		editor.commit();
	}

	// 将登陆的密碼缓存到本地
	public static void setCachePassword(Context context, String password) {
		Editor editor = context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).edit();
		editor.putString(CACHE_PASSWORD, password);
		editor.commit();
	}

}
