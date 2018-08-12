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
	
		//��ȡTabHost
		FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		//��ʼ�������ù���Fragment�Ĺ�����   ��ʾ���ݵ�����
		tabHost.setup(MainActivity.this, getSupportFragmentManager(), R.id.realtabcontent);
		
		//���Tab
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("��ǩ1"), MyFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("��ǩ2"), MyFragment2.class, null);
	}
}
