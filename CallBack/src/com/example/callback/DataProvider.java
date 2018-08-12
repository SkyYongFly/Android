package com.example.callback;

import android.util.Log;

public class DataProvider {
	String TAG ="test";
	/**
	 * C调用Java中的空方法
	 */
	public void helloFromJava(){
		Log.d(TAG,"哈哈哈，我是Java中的方法");
	}
	
	/**
	 * C调用Java中的带有int参数的方法
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
	 * C调用Java中的带有String类型参数的方法
	 * @param str
	 */
   public void strDemo(String str){
	   Log.d(TAG, str);
   }
   
   public native  void method1();
   public native  void method2();
   public native  void method3();

}
