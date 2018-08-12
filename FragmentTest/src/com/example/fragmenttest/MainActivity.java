package com.example.fragmenttest;

import com.example.fragment.MyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 获取fragment的管理者
		FragmentManager fm = getSupportFragmentManager();
		
		// 从管理者中获取已经加载的布局
		Fragment fragment = fm.findFragmentById(R.id.main_layout);
		// 如果之前加载的fragment为空（没有加载），则新建
		if (fragment == null) {
			//加载fragment
			fragment = new MyFragment();
			// 开启事物 添加 提交
			fm.beginTransaction().add(R.id.main_layout, fragment).commit();
		}
	}
}
