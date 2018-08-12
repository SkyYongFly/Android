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
		
		// ��ȡfragment�Ĺ�����
		FragmentManager fm = getSupportFragmentManager();
		
		// �ӹ������л�ȡ�Ѿ����صĲ���
		Fragment fragment = fm.findFragmentById(R.id.main_layout);
		// ���֮ǰ���ص�fragmentΪ�գ�û�м��أ������½�
		if (fragment == null) {
			//����fragment
			fragment = new MyFragment();
			// �������� ��� �ύ
			fm.beginTransaction().add(R.id.main_layout, fragment).commit();
		}
	}
}
