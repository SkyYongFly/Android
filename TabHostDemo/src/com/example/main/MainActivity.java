package com.example.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//获取TabHost实例
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		//初始化
		tabHost.setup();

		//添加Tab 设置Tab的标识（“tab1”） 、名称("标签1")和显示的图标（这里为空没有显示） 、显示的内容容器（容器也可以单独写，不用再布局文件中的TabHost中）
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("标签1",null).setContent(R.id.tv1));
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("标签2",null).setContent(R.id.tv2));
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("标签3", null).setContent(R.id.tv3));
	}
}
