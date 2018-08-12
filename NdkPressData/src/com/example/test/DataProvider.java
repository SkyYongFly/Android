package com.example.test;

public class DataProvider {
	
	
	/**
	 * 计算了两个数的和
	 * @param a
	 * @return
	 */
	public native int sum(int a,int b);
	
	/**
	 * 在C中在传入字符串后面拼接字符
	 * @param string
	 * @return
	 */
	public native  String sayHelloInC(String string);
	
	/**
	 * 让C处理数组
	 * @param iNum
	 * @return
	 */
	public native int[] intMethod(int[] iNum);

}
