package com.example.secret.daomain;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ϵͳ������Ϣ��
 * @author yzas
 *
 */
public class Config {
	//��������ĵ�ַ
	public  final static String  URI= "http://10.50.12.250:8080/Secret/secret";
	
	//�������ݴ洢�ļ�
	private final  static String APP_ID ="com.example.secret";
	//token�ı��潨�ƶԽ�������
	private final static  String CacheToken ="token";
	//����
	public final static String CHARSET = "UTF-8";
	//ָ���ύ����Ŷ�ǵ绰����
	public final static String Phone_NUM = "phoneNum";
	//��½�󻺴浽���ص��ֻ�����
   public  static final String CACHE_PHONE = "cache_phone";

	public static final String PHONE_NUM_MD5 = "phone_num_md5";

	//���߷������ϴ������ֻ������б�
	public static final String CONTACTS_PHONE = "contacts_phone";

	//�ϴ�����������������
	public static final String CONNECT_ACTION ="action";
	public static final String ACTION_SEND_PHONENUM ="sendPhoneList";
	public static final String ACTION_REQUEST_CODE ="requestCode";
	public static final String ACTION_REQUEST_MESSAGE ="requestMessage";
	
	//�ϴ��ֻ������������ķ�������
	public static final String RETURN ="sendPhoneListBack";
	
	public static final String REQUEST_MESSAGE_PAGE ="request_message_page";
	
	
	
	
	/**
	 * ��ȡ�����ڱ��ص�token
	 * @param context
	 * @return
	 */
	public static String getCacheToken(Context context){
		return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(CacheToken,null);
	}
	
	
	/**
	 * ��token���ݱ��浽����
	 * @return
	 */
	public  static void saveToken(Context context,String token){
		Editor  editor = context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).edit();
		editor.putString(CacheToken,token);
		editor.commit();
	}


	/**
	 * ��ȡ���浽���ص��ֻ�����
	 * @param context
	 * @return
	 */
	public static String getCachePhone(Context context) {
		return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(CACHE_PHONE,null);
	}

}
