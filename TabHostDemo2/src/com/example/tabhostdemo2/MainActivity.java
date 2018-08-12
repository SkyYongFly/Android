package com.example.tabhostdemo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		
		//将要显示的布局添加到定义的显示内容的容器上（id为tabcontent的布局）
		LayoutInflater  inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
		inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
		
		//为了显示图标，需要自定义布局，将该View加载进来，否则在代码里直接加载图片只显示文字不显示图片
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getLayoutInflater().inflate(R.layout.tab1_indector,null)).setContent(R.id.ll_tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getLayoutInflater().inflate(R.layout.tab2_indector, null)).setContent(R.id.ll_tab2));
		
		
	}
}
