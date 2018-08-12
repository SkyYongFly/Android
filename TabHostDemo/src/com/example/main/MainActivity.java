package com.example.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//��ȡTabHostʵ��
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		//��ʼ��
		tabHost.setup();

		//���Tab ����Tab�ı�ʶ����tab1���� ������("��ǩ1")����ʾ��ͼ�꣨����Ϊ��û����ʾ�� ����ʾ����������������Ҳ���Ե���д�������ٲ����ļ��е�TabHost�У�
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("��ǩ1",null).setContent(R.id.tv1));
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("��ǩ2",null).setContent(R.id.tv2));
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("��ǩ3", null).setContent(R.id.tv3));
	}
}
