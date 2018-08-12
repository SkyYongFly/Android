package com.example.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView mListView;
	private DrawerLayout mDrawerLayout;
	private List<String> itemList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// �򵥵ĸ�List��ֵ
		for (int i = 0; i < 10; i++) {
			itemList.add("�����" + i);
		}

		// ������
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_layout);
		// ������ʾ��ListView
		mListView = (ListView) findViewById(R.id.lv_show);
		// ����������
		mListView.setAdapter(new ArrayAdapter<String>(
										MainActivity.this,
										android.R.layout.simple_list_item_1,
										itemList));
		
		// ����ѡ�����¼�(��������ҳ����ʾ������ҳ��˴��򵥵���ʾ��ѡ������)
		mListView.setOnItemClickListener(new MyItemListener());
		
		//��������������͹ر�״̬
		mDrawerLayout.setDrawerListener(new MyActionBarDT(MainActivity.this,mDrawerLayout,R.drawable.ic_launcher,R.string.open,R.string.close));
		
		

	}

	private class MyItemListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Fragment itemFrag = new FragmentItem();
			// ����bundle������fragment���󴫵�����
			Bundle bundle = new Bundle();
			bundle.putString("name", itemList.get(position));
			itemFrag.setArguments(bundle);

			// ���½���Fragment���󸲸���ԭ�����������FrameLayout��
			FragmentManager manager = getFragmentManager();
			manager.beginTransaction().replace(R.id.fl_content, itemFrag).commit();
			// ���õ������ѡ�������Զ�����
			mDrawerLayout.closeDrawer(mListView);

		}
	}
	
	@SuppressWarnings("deprecation")
	private class MyActionBarDT extends ActionBarDrawerToggle{

		public MyActionBarDT(Activity activity, DrawerLayout drawerLayout, int drawerImageRes,
				int openDrawerContentDescRes, int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
		}

		//����رյ�ʱ��
		@Override
		public void onDrawerClosed(View drawerView) {
			super.onDrawerClosed(drawerView);
		}

		//���뿪����ʱ��
		@Override
		public void onDrawerOpened(View drawerView) {
			super.onDrawerOpened(drawerView);
		}

		
	}

}
