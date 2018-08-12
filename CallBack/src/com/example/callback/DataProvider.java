package com.example.callback;

import android.util.Log;

public class DataProvider {
	String TAG ="test";
	/**
	 * C����Java�еĿշ���
	 */
	public void helloFromJava(){
		Log.d(TAG,"������������Java�еķ���");
	}
	
	/**
	 * C����Java�еĴ���int�����ķ���
	 * @param a
	 * @param b
	 * @return
	 */
	public int add(int a,int b){
		int sum = a+b;
		Log.d(TAG,sum+"");
		return sum;
	}
	
	/**
	 * C����Java�еĴ���String���Ͳ����ķ���
	 * @param str
	 */
   public void strDemo(String str){
	   Log.d(TAG, str);
   }
   
   public native  void method1();
   public native  void method2();
   public native  void method3();

}
