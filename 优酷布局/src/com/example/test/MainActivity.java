package com.example.test;

import com.example.utils.AnimaUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView iv_home;
	private ImageView iv_menu;
	private RelativeLayout rl_big;
	private RelativeLayout rl_center;
	private RelativeLayout rl_inner;
	/**
	 * 内层的菜单是否显示,默认开启
	 */
	private boolean innerMenuInvisible = true;

	/**
	 * 中间的菜单是否显示
	 */
	private boolean centerMenuInvisible = true;

	/**
	 * 最外层的菜单是否显示
	 */
	private boolean bigMenuInvisible = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		iv_home = (ImageView) findViewById(R.id.iv_home);
		iv_menu = (ImageView) findViewById(R.id.iv_menu);

		rl_big = (RelativeLayout) findViewById(R.id.rl_big);
		rl_center = (RelativeLayout) findViewById(R.id.rl_center);
		rl_inner = (RelativeLayout) findViewById(R.id.rl_inner);

		iv_home.setOnClickListener(this);
		iv_menu.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_home: {// 最内层的home键被点击
			if (bigMenuInvisible) {// 如果最外层菜单存在（说明中间的菜单一定存在）
				// 让中间的和最外面的隐藏
				AnimaUtil.startAnimOut(rl_big, 100);
				AnimaUtil.startAnimOut(rl_center);

				bigMenuInvisible = false;
				centerMenuInvisible = false;

			} else if (centerMenuInvisible) {// 中间的菜单存在
				// 让中间的菜单隐藏
				AnimaUtil.startAnimOut(rl_center);

				centerMenuInvisible = false;

			} else {// 中间的和最外面的菜单都不存在
					// 让中间的和外面的显示
				AnimaUtil.startAnimIn(rl_big, 100);
				AnimaUtil.startAnimIn(rl_center);

				centerMenuInvisible = true;
				bigMenuInvisible = true;
			}

		}

			break;
		case R.id.iv_menu: {// 中间的menu被点击
			if (bigMenuInvisible) {// 将最外层的菜单隐藏
				AnimaUtil.startAnimOut(rl_big);
			} else {// 将最外层的菜单显示
				AnimaUtil.startAnimIn(rl_big);
			}
			bigMenuInvisible = !bigMenuInvisible;

		}
			break;

		default:
			break;
		}

	}

	/**
	 *手机按键被按下 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {//当手机的menu键按下
			changeMenu();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 改变菜单的显示
	 */
	private void changeMenu() {
		if(innerMenuInvisible){//如果最内层的菜单存在
			
			if(bigMenuInvisible){//如果最外层菜单存在，（则中间的菜单也存在）
				//将所有的菜单隐藏
				AnimaUtil.startAnimOut(rl_big,300);
				AnimaUtil.startAnimOut(rl_inner);
				AnimaUtil.startAnimOut(rl_center,100);
				
				bigMenuInvisible = false;
				centerMenuInvisible = false;
				innerMenuInvisible = false;
			}else{//最外层的菜单不存在，中间的菜单存在
				//将中间的菜单和内层的菜单隐藏
				AnimaUtil.startAnimOut(rl_inner,100);
				AnimaUtil.startAnimOut(rl_center);
				
				centerMenuInvisible = false;
				innerMenuInvisible = false;
			}
		}else{//如果最内层的菜单不存在
			//将所有的菜单都显示
			AnimaUtil.startAnimIn(rl_inner,200);
			AnimaUtil.startAnimIn(rl_center);
			AnimaUtil.startAnimIn(rl_big,100);
			
			bigMenuInvisible = true;
			centerMenuInvisible = true;
			innerMenuInvisible = true;
		}
	}

}
