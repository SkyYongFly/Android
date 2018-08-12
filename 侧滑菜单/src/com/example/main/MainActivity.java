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

		// 简单的给List赋值
		for (int i = 0; i < 10; i++) {
			itemList.add("大白兔" + i);
		}

		// 主布局
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_layout);
		// 抽屉显示的ListView
		mListView = (ListView) findViewById(R.id.lv_show);
		// 设置适配器
		mListView.setAdapter(new ArrayAdapter<String>(
										MainActivity.this,
										android.R.layout.simple_list_item_1,
										itemList));
		
		// 设置选项点击事件(进入具体的页面显示，具体页面此处简单的显示所选的文字)
		mListView.setOnItemClickListener(new MyItemListener());
		
		//监听抽屉的拉开和关闭状态
		mDrawerLayout.setDrawerListener(new MyActionBarDT(MainActivity.this,mDrawerLayout,R.drawable.ic_launcher,R.string.open,R.string.close));
		
		

	}

	private class MyItemListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Fragment itemFrag = new FragmentItem();
			// 利用bundle对象想fragment对象传递数据
			Bundle bundle = new Bundle();
			bundle.putString("name", itemList.get(position));
			itemFrag.setArguments(bundle);

			// 将新建的Fragment对象覆盖在原来的主界面的FrameLayout上
			FragmentManager manager = getFragmentManager();
			manager.beginTransaction().replace(R.id.fl_content, itemFrag).commit();
			// 设置点击抽屉选项后抽屉自动隐藏
			mDrawerLayout.closeDrawer(mListView);

		}
	}
	
	@SuppressWarnings("deprecation")
	private class MyActionBarDT extends ActionBarDrawerToggle{

		public MyActionBarDT(Activity activity, DrawerLayout drawerLayout, int drawerImageRes,
				int openDrawerContentDescRes, int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
		}

		//抽屉关闭的时候
		@Override
		public void onDrawerClosed(View drawerView) {
			super.onDrawerClosed(drawerView);
		}

		//抽屉开启的时候
		@Override
		public void onDrawerOpened(View drawerView) {
			super.onDrawerOpened(drawerView);
		}

		
	}

}
