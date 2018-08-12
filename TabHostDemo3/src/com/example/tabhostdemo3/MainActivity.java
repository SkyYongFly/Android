package com.example.tabhostdemo3;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//获取TabHost
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.activity_main,tabHost.getTabContentView(),true);
		
		//添加Tab
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("标签一").setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("标签二").setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("标签三").setContent(R.id.view3));
		
		//设置标签监听器
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if("tab1".equals(tabId)){
					Toast.makeText(MainActivity.this,"标签1被点击了",Toast.LENGTH_SHORT).show();
				}
				else if("tab2".contentEquals(tabId)){
					Toast.makeText(MainActivity.this,"标签2被点击了",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this,"标签3被点击了",Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
}
