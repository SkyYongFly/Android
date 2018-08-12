package com.example.test;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	DataProvider  provider;
	
	//载入C库
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
	 * 按钮1点击计算两个数和
	 * @param v
	 */
	public void  click1(View v){
		int sum = provider.sum(2, 3);
		Toast.makeText(this,"2和3的和是"+sum,Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 按钮2点击拼接字符串
	 * @param v
	 */
	public void  click2(View v){
		String str = provider.sayHelloInC("Hello I am Java");
		Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 按钮3点击操作数组
	 * @param v
	 */
	public void click3(View v){
		int[] num1 = new int[]{2,4,5};
		provider.intMethod(num1);
		int sum = 0;
		for(int i:num1){
			sum+=i;
		}
		Toast.makeText(this,"数组和"+sum, Toast.LENGTH_SHORT).show();
	}

}
