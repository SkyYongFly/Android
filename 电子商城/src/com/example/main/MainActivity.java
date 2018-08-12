package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

/**
 * ��ʼҳ�� ������ݵĳ�ʼ��
 * 
 * @author yzas
 *
 */
public class MainActivity extends Activity {

	private FrameLayout fl_loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fl_loading = (FrameLayout) findViewById(R.id.fl_loading);

		initView();
	}

	/**
	 * ���ؽ���������
	 */
	private void initView() {
		Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.spalsh_loading);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(MainActivity.this,HomeActivity.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
				// ���õ�ǰ����뵽ϵһ������м䶯���ĵ��뽥����Ч��
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

			}

		});
		fl_loading.startAnimation(animation);
	}

}
