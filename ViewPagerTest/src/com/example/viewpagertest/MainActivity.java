package com.example.viewpagertest;

import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.app.Activity;
import android.database.MergeCursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ViewPager  viewPager;
	private TextView tv_page1;
	private TextView tv_page2;
	private TextView tv_page3;
	private List<View> list;
	private ImageView iv_cursor;
	private int cursorWidth;
	private int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化
		initImageView();
		initView();
		initViewPager();
	}
	private void initImageView(){
		
		iv_cursor = (ImageView) findViewById(R.id.iv_cursor);
		//获取游标图片的宽度
		cursorWidth = BitmapFactory.decodeResource(getResources(),R.drawable.cursor).getWidth();
		//获取屏幕的宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);;
		int width = metrics.widthPixels;
	}
	
	private void initView(){
		//获取上面的页卡
		tv_page1 = (TextView) findViewById(R.id.tv_page1);
		tv_page2 = (TextView) findViewById(R.id.tv_page2);
		tv_page3 = (TextView) findViewById(R.id.tv_page3);
		
		tv_page1.setOnClickListener(new MyOnClickListener(0));
		tv_page2.setOnClickListener(new MyOnClickListener(1));
		tv_page3.setOnClickListener(new MyOnClickListener(2));
	}
	
	/**
	 * 页卡的监听事件
	 * 点击页卡的时候当前位置应该是页卡的位置
	 * @author yzas
	 */
	private class  MyOnClickListener implements OnClickListener{

		int index = 0;
		public MyOnClickListener(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}
	@SuppressWarnings("deprecation")
	private void initViewPager(){
		list = new ArrayList<View>();
		//获取在ViewPager中显示的布局
		LayoutInflater inflater = getLayoutInflater();
		list.add(inflater.inflate(R.layout.page_item,null));
		list.add(inflater.inflate(R.layout.page_item2,null));
		list.add(inflater.inflate(R.layout.page_item3,null));
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		//ViewPager设置适配器
		viewPager.setAdapter(new MyViewPagerAdapter());
		//设置状态监听器
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}
	
	private class MyViewPagerAdapter extends  PagerAdapter{

		/**
		 * 获取整个填充内容的数量
		 */
		@Override
		public int getCount() {
			return list.size();
		}
		/**
		 * 实例化填充选项
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}
		/**
		 * 销毁填充选项
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}
		
		/**
		 * 
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}
	
	private class MyPageChangeListener  implements OnPageChangeListener{
		/**
		 * 当页面滑动状态改变的时候调用
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		/**
		 * 当页面滑动的时候调用
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
			
		/**
		 * 当页面被选择的时候调用
		 */
		@Override
		public void onPageSelected(int arg0) {
			//当ViewPager滑动的时候，上面的游标也要跟着移动，设置动画
			TranslateAnimation animation = new TranslateAnimation(cursorWidth*currentIndex,cursorWidth*arg0,0,0);
			animation.setFillAfter(true);
			animation.setDuration(1000);
			iv_cursor.startAnimation(animation);
			//更新当前位置
			currentIndex = arg0;
		}
	}
}
