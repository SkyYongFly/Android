package com.example.jni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("test",print("����Java����,������"));
		
	}
	//�������
	static{
		System.loadLibrary("hello");
	}
	
	//1������һ�����ط������÷����ľ���ʵ������C/C++ʵ��
	public native String print(String str);
	
}
