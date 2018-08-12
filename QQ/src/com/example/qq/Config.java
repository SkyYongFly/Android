package com.example.qq;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Ӧ��������
 * 
 * @author yzas
 *
 */
public class Config {
	// �����������ַ
	public final static String SERVER_URL = "10.50.14.103";
	// ���ӷ������Ķ˿�
	public static final int PORT = 1234;
	// ����
	public static final String CHARSET = "UTF-8";
	// ������û�з�������
	public static final String NO_BACK = "there is no data back";
	// ���������ص���ʾ������Ч
	public static final Object NO_EFFECT_PASSWORD = "no effect password";
	// ���������ص���ʾ�ֻ�����Ч
	public static final Object NO_EFFECT_PHONE_NUM = "no effect phone number";

	// ���屾�ص����ݴ洢�ļ���
	private final static String SAVE_FILE = "com.example.qq.info";
	//���������Ϣ���ļ�
	//public static final String FRIENDS_FILE= "friends.xml";
	// �����ڱ��ص��ֻ�����ļ�ֵ�Ա�ʾ
	private final static String CACHE_PHONENUM = "cachePhoneNum";
	// �����ڱ��ص������ֵ�Ա�ʶ
	private final static String CACHE_PASSWORD = "cachePassword";

	// ָ�����������������������
	public static final String ACTION = "action";
	// ��˵����������������������������֤�û���������
	public static final String ACTION_CHECK_PASSWORD = "check_password";
	public static final String DATA_PHONENUM = "data_phoneNum";
	public static final String DATA_PASSWORD = "data_password";
	public  static final Object SUCCESS = "auto_login_success";
	//���߷����������͵�����������½��
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	public static final String SEARCH_FRIEND = "search_friend";
	public static final String ADD_FRIEND = "add_friend";
	public static final String GET_SIGN_AND_NICKNAME= "get_nickname_and_sign";
	public static final String RESPONSE_ADD_FRIEND = "response_add_friend";
	
	protected static final Object PHONE_NUM_HAS_EXISTS = "the phone has existed";
	protected static final Object REGISTER_FAIL = "register fail";
	protected static final Object LOGIN_ERROR = "login error";
	
	//���Һ���
	public  static final String EXIST = "the user exist";
	public  static final String NOT_EXIST = "the user is not exist";
	public  static final String SEARCH_FAIL = "search user fail";
	
	//��Ӻ���
	public static final String ADD_FRIEND_SUCCESS ="add friend success";
	public static final String HAS_ARE_FRIENDS ="the friend is exist";
	
	//��ѯ�ǳƺ�ǩ��
	public static final String GET_SIGN_FAIL ="get nickname and sign fail";
	public static final String ADD_FRIEND_PHONE ="add_friend_phone";
	
	//��ȡ��Ϣ
	public static final String GET_NOREAD_MESSAGES = "get_no_read_message";
	public static final String NO_UNREAD_DATA = "no_data";
	
	
	public static final String DESPHONE = "des_phone";
	public static final String RESPONSE = "response";
	public static final String GET_FRIENDS = "get_friends";
	public static final String NO_FRIENDS = "no_friends";



	// ��ȡ�����ڱ��ص��ֻ�����
	public static String getCachePhoneNum(Context context) {
		return context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).getString(CACHE_PHONENUM, null);
	}

	// ��ȡ�����ڱ��ص����루MD5���ܣ�
	public static String getCachePassword(Context context) {
		return context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).getString(CACHE_PASSWORD, null);
	}

	// ����½���ֻ����뻺�浽����
	public static void setCachePhoneNum(Context context, String phoneNum) {
		Editor editor = context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).edit();
		editor.putString(CACHE_PHONENUM, phoneNum);
		editor.commit();
	}

	// ����½���ܴa���浽����
	public static void setCachePassword(Context context, String password) {
		Editor editor = context.getSharedPreferences(SAVE_FILE, context.MODE_PRIVATE).edit();
		editor.putString(CACHE_PASSWORD, password);
		editor.commit();
	}

}
