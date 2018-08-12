package com.example.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		//获取TabHost
		FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		//初始化，设置管理Fragment的管理者   显示内容的容器
		tabHost.setup(MainActivity.this, getSupportFragmentManager(), R.id.realtabcontent);
		
		//添加Tab
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("标签1"), MyFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("标签2"), MyFragment2.class, null);
	}
}
