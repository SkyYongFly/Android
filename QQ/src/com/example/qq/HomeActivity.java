package com.example.qq;

import java.util.List;

import com.example.fragment.HomeFragmentBody1;
import com.example.fragment.HomeFragmentBody2;
import com.example.fragment.HomeFragmentBody3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener {

	private static final String TAG = "test";
	private ImageView iv_body1;
	private ImageView iv_body2;
	private ImageView iv_body3;

	private ImageView iv_home_title1;
	private TextView tv_home_title2;
	private TextView  ll_home_title3;

	private int currentIndex; // ��ǰ�ڵڼ���ҳ��

	private FragmentManager fragmentManager;
	private Fragment fragmentBody1;
	private Fragment fragmentBody2;
	private Fragment fragmentBody3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initImageView();
		initFragment();
	}

	/**
	 * ��ȡ�����еײ���������ť�����ü����¼�
	 */
	private void initImageView() {
		iv_home_title1 = (ImageView) findViewById(R.id.home_title1);
		tv_home_title2 = (TextView) findViewById(R.id.home_title2);
		ll_home_title3 = (TextView) findViewById(R.id.home_title3);

		iv_body1 = (ImageView) findViewById(R.id.body1);
		iv_body2 = (ImageView) findViewById(R.id.body2);
		iv_body3 = (ImageView) findViewById(R.id.body3);

		iv_body1.setOnClickListener(this);
		iv_body2.setOnClickListener(this);
		iv_body3.setOnClickListener(this);
	}

	/**
	 * ����Fragment��ع��ܵĴ���
	 */
	private void initFragment() {
		fragmentManager = getFragmentManager();
		fragmentBody1 = fragmentManager.findFragmentById(R.id.fm_contain_fragment);
		// û���½�
		if (fragmentBody1 == null) {
			// һ��ʼ���ص��ǵ�һ��ҳ��
			currentIndex = 1;
			fragmentBody1 = new HomeFragmentBody1();
			fragmentManager.beginTransaction().add(R.id.fm_contain_fragment, fragmentBody1).commit();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.body1: {
			if (currentIndex != 1) {//�ڵ�һ��ҳ�����л�����һ��ҳ����Ч
				ll_home_title3.setText(null);
				//ll_home_title3.setBackgroundResource(R.drawable.more);
				tv_home_title2.setText(R.string.tv_home_title1);
				
				// û���½�
				if (fragmentBody1 == null) {
					// һ��ʼ���ص��ǵ�һ��ҳ��
					fragmentBody1 = new HomeFragmentBody1();
					//��ת����ǰҳ���ֻ���ǵڶ������ߵ�����ҳ�棬ֻҪ��ת������ҳ��϶���Ϊ��
					Fragment oldFragment = fragmentBody2 ==null?fragmentBody3:fragmentBody2;
					FragmentTransaction tran = fragmentManager.beginTransaction();
					tran.remove(oldFragment);
					tran.add(R.id.fm_contain_fragment,fragmentBody1).commit();
					currentIndex =1;
					fragmentBody2=null;
					fragmentBody3=null;
				}
			}
		}
			break;
		case R.id.body2: {
			if (currentIndex != 2) {//�ڵ�ǰҳ�����л�����ǰҳ����Ч
				tv_home_title2.setText(R.string.tv_home_title2);
				ll_home_title3.setBackgroundColor(Color.TRANSPARENT);
				ll_home_title3.setText(R.string.tv_home_title2_add);
				
				// û��,�½�
				if (fragmentBody2 == null) {
					// һ��ʼ���ص��ǵ�һ��ҳ��
					fragmentBody2 = new HomeFragmentBody2();
					//��ת����ǰҳ���ֻ���ǵڶ������ߵ�����ҳ�棬ֻҪ��ת������ҳ��϶���Ϊ��
					Fragment oldFragment = fragmentBody1 ==null?fragmentBody3:fragmentBody1;
					FragmentTransaction tran = fragmentManager.beginTransaction();
					tran.remove(oldFragment);
					tran.add(R.id.fm_contain_fragment,fragmentBody2).commit();
					currentIndex =2;
					fragmentBody1=null;
					fragmentBody3=null;
				}
			}
		}
			break;
		case R.id.body3: {
			if (currentIndex != 3) {//�ڵ�ǰҳ�����л�����ǰҳ����Ч
				//���ñ������Ҳ����Ӱ�ť
				tv_home_title2.setText(R.string.tv_home_title3);
				ll_home_title3.setBackgroundColor(Color.TRANSPARENT);
				ll_home_title3.setText(R.string.tv_home_title3_more);
				
				// û���½�
				if (fragmentBody3== null) {
					// һ��ʼ���ص��ǵ�һ��ҳ��
					fragmentBody3 = new HomeFragmentBody3();
					//��ת����ǰҳ���ֻ���ǵڶ������ߵ�����ҳ�棬ֻҪ��ת������ҳ��϶���Ϊ��
					Fragment oldFragment = fragmentBody2 ==null?fragmentBody1:fragmentBody2;
					FragmentTransaction tran = fragmentManager.beginTransaction();
					tran.remove(oldFragment);
					tran.add(R.id.fm_contain_fragment,fragmentBody3).commit();
					currentIndex =3;
					fragmentBody2=null;
					fragmentBody1=null;
				}
			}
		}
			break;

		default:
			break;
		}
	}
	
	/**
	 * ��ӱ������Ҳ���Ӱ�ť
	 * @param v
	 */
	public void addOrMore(View v){
		switch (currentIndex) {
		case 1:

			break;

		case 2:{//�ڵڶ���ҳ�棬���
			Intent intent = new Intent(HomeActivity.this,SearchFriend.class);
			startActivity(intent);
		}break;

		case 3:

			break;

		default:
			break;
		}
		
	}

}
