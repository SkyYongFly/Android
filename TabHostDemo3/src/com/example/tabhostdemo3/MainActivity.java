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
		
		//��ȡTabHost
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.activity_main,tabHost.getTabContentView(),true);
		
		//���Tab
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("��ǩһ").setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("��ǩ��").setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("��ǩ��").setContent(R.id.view3));
		
		//���ñ�ǩ������
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if("tab1".equals(tabId)){
					Toast.makeText(MainActivity.this,"��ǩ1�������",Toast.LENGTH_SHORT).show();
				}
				else if("tab2".contentEquals(tabId)){
					Toast.makeText(MainActivity.this,"��ǩ2�������",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this,"��ǩ3�������",Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
}
