package com.example.main;

import com.example.fragment.BuyFragment;
import com.example.fragment.PersonFragment;
import com.example.fragment.SearchFragment;
import com.example.fragment.StartFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class HomeActivity extends FragmentActivity {
	
	//Fragment������
	private Class fragments[] = {StartFragment.class,SearchFragment.class,BuyFragment.class,PersonFragment.class};
	//�����ǩ���Ƶ�����
	private String tabName[] ={"��ҳ","����","���ﳵ","����"};
	//����ײ���ť��ͼ����ʽ
	private int tabIcon[] = {R.drawable.tab_icon_start,R.drawable.tab_icon_search,
			R.drawable.tab_icon_buy,R.drawable.tab_icon_more};
	private LayoutInflater inflater;
	//
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		inflater = getLayoutInflater();
		
		FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(HomeActivity.this,getSupportFragmentManager(),R.id.realtabcontent);
		
		for(int i=0;i<fragments.length;i++){
			TabSpec tabSpec = tabHost.newTabSpec(tabName[i]).setIndicator(getTabView(i));
			tabHost.addTab(tabSpec,fragments[i],null);
			//tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_background);
		
		}
		
	}
	
	/**
	 * �Զ����ǩ����ʽ
	 * @param i
	 * @return
	 */
	private View getTabView(int i) {
		View view = inflater.inflate(R.layout.tab_view_item,null);
		
		ImageView iv_tabItem = (ImageView) view.findViewById(R.id.iv_tabItem);
		iv_tabItem.setImageResource(tabIcon[i]);
		
		TextView  tv_tabItem  = (TextView) view.findViewById(R.id.tv_tabItem);
		tv_tabItem.setText(tabName[i]);
		
		
		return view;
	}
}
