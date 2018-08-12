package com.example.jni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("test",print("我是Java方法,哈哈哈"));
		
	}
	//导入类库
	static{
		System.loadLibrary("hello");
	}
	
	//1、声明一个本地方法，该方法的具体实现是由C/C++实现
	public native String print(String str);
	
}
