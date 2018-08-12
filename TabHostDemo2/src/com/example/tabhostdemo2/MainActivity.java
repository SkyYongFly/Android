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
		
		//��Ҫ��ʾ�Ĳ�����ӵ��������ʾ���ݵ������ϣ�idΪtabcontent�Ĳ��֣�
		LayoutInflater  inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
		inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
		
		//Ϊ����ʾͼ�꣬��Ҫ�Զ��岼�֣�����View���ؽ����������ڴ�����ֱ�Ӽ���ͼƬֻ��ʾ���ֲ���ʾͼƬ
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getLayoutInflater().inflate(R.layout.tab1_indector,null)).setContent(R.id.ll_tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getLayoutInflater().inflate(R.layout.tab2_indector, null)).setContent(R.id.ll_tab2));
		
		
	}
}
