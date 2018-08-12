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
	 * �ڲ�Ĳ˵��Ƿ���ʾ,Ĭ�Ͽ���
	 */
	private boolean innerMenuInvisible = true;

	/**
	 * �м�Ĳ˵��Ƿ���ʾ
	 */
	private boolean centerMenuInvisible = true;

	/**
	 * �����Ĳ˵��Ƿ���ʾ
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
		case R.id.iv_home: {// ���ڲ��home�������
			if (bigMenuInvisible) {// ��������˵����ڣ�˵���м�Ĳ˵�һ�����ڣ�
				// ���м�ĺ������������
				AnimaUtil.startAnimOut(rl_big, 100);
				AnimaUtil.startAnimOut(rl_center);

				bigMenuInvisible = false;
				centerMenuInvisible = false;

			} else if (centerMenuInvisible) {// �м�Ĳ˵�����
				// ���м�Ĳ˵�����
				AnimaUtil.startAnimOut(rl_center);

				centerMenuInvisible = false;

			} else {// �м�ĺ�������Ĳ˵���������
					// ���м�ĺ��������ʾ
				AnimaUtil.startAnimIn(rl_big, 100);
				AnimaUtil.startAnimIn(rl_center);

				centerMenuInvisible = true;
				bigMenuInvisible = true;
			}

		}

			break;
		case R.id.iv_menu: {// �м��menu�����
			if (bigMenuInvisible) {// �������Ĳ˵�����
				AnimaUtil.startAnimOut(rl_big);
			} else {// �������Ĳ˵���ʾ
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
	 *�ֻ����������� 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {//���ֻ���menu������
			changeMenu();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �ı�˵�����ʾ
	 */
	private void changeMenu() {
		if(innerMenuInvisible){//������ڲ�Ĳ˵�����
			
			if(bigMenuInvisible){//��������˵����ڣ������м�Ĳ˵�Ҳ���ڣ�
				//�����еĲ˵�����
				AnimaUtil.startAnimOut(rl_big,300);
				AnimaUtil.startAnimOut(rl_inner);
				AnimaUtil.startAnimOut(rl_center,100);
				
				bigMenuInvisible = false;
				centerMenuInvisible = false;
				innerMenuInvisible = false;
			}else{//�����Ĳ˵������ڣ��м�Ĳ˵�����
				//���м�Ĳ˵����ڲ�Ĳ˵�����
				AnimaUtil.startAnimOut(rl_inner,100);
				AnimaUtil.startAnimOut(rl_center);
				
				centerMenuInvisible = false;
				innerMenuInvisible = false;
			}
		}else{//������ڲ�Ĳ˵�������
			//�����еĲ˵�����ʾ
			AnimaUtil.startAnimIn(rl_inner,200);
			AnimaUtil.startAnimIn(rl_center);
			AnimaUtil.startAnimIn(rl_big,100);
			
			bigMenuInvisible = true;
			centerMenuInvisible = true;
			innerMenuInvisible = true;
		}
	}

}
