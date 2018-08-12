package com.example.callback;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	DataProvider provider ;
	
	static{
		System.loadLibrary("hello");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		provider = new DataProvider();
	}
	
	public void click1(View v){
		provider.method1();
	}
	public void click2(View v){
		provider.method2();
	}
	public void click3(View v){
		provider.method3();
	}
	
	
	
}
