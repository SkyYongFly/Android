package com.example.test;

public class DataProvider {
	
	
	/**
	 * �������������ĺ�
	 * @param a
	 * @return
	 */
	public native int sum(int a,int b);
	
	/**
	 * ��C���ڴ����ַ�������ƴ���ַ�
	 * @param string
	 * @return
	 */
	public native  String sayHelloInC(String string);
	
	/**
	 * ��C��������
	 * @param iNum
	 * @return
	 */
	public native int[] intMethod(int[] iNum);

}
