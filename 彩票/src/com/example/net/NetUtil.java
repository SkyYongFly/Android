package com.example.net;

import com.example.daomain.Config;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * 连接网络公共类
 * 主要功能：判断当前是否有网络、网络类型、如果是wap方式（APN的ip和端口是什么）
 * @author yzas
 *
 */
public class NetUtil {
	/**
	 *判断是否有网络链接
	 * @return
	 */
	public static boolean hasNetConnection(Context context){
		 boolean  isWifi = isWifiConnection(context);
		 boolean  isMobile = isMobileConnection(context);
		 
		 //如果是移动网络连接，判断是否是wap方式连接，如果是获取代理信息
		 if(isMobile){
			 readApn(context);
		 }
		 
		 if(!isWifi && !isMobile){
			 return false;
		 }
		 return true;
	}


	/**
	 * 读取APN信息
	 * @param context
	 */
	private static void readApn(Context context) {
		//读取的contentprovider地址
		Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");//4.0模拟器屏蔽掉该权限
		
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null, null);
		if(cursor!=null && cursor.moveToFirst()){
			Config.PROXY =cursor.getString(cursor.getColumnIndex("proxy"));
			Config.PORT  = cursor.getInt(cursor.getColumnIndex("port"));
		}
	}


	/**
	 * 是否有wifi链接
	 * @return
	 */
	private static boolean isWifiConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(info!=null){
			return info.isConnected();
		}
		return false;
	}
	
	/**
	 * 是否有移动网络连接
	 * @return
	 */
	private static boolean isMobileConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info!=null){
			return info.isConnected();
		}
		return false;
	}

}
