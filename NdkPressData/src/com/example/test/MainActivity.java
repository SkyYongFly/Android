package com.example.test;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	DataProvider  provider;
	
	//����C��
	static{
		System.loadLibrary("hello");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		provider = new DataProvider();
	}
	/**
	 * ��ť1���������������
	 * @param v
	 */
	public void  click1(View v){
		int sum = provider.sum(2, 3);
		Toast.makeText(this,"2��3�ĺ���"+sum,Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ��ť2���ƴ���ַ���
	 * @param v
	 */
	public void  click2(View v){
		String str = provider.sayHelloInC("Hello I am Java");
		Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ��ť3�����������
	 * @param v
	 */
	public void click3(View v){
		int[] num1 = new int[]{2,4,5};
		provider.intMethod(num1);
		int sum = 0;
		for(int i:num1){
			sum+=i;
		}
		Toast.makeText(this,"�����"+sum, Toast.LENGTH_SHORT).show();
	}

}
