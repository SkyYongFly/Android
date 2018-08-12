package com.example.qq;

import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;
import com.example.service.MainService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Ӧ�ó�ʼ������ ���ܣ�1�����Ӧ�õĿ�ʼ���涯������ 2���汾�����£����˹�����ȡ��Ӧ���������棬Ӧ�ÿ�ʼ�������������»��ʱ�����û������½���
 * 3����鱾�ػ����ֻ��ź����룬�Ӷ���������������½���滹��Ӧ��������
 * 
 * @author yzas
 *
 */
public class MainActivity extends Activity {
	protected static final String TAG = "test";
	protected static final int SUCCESS = 3;
	protected static final int FAIL = 4;
	private ImageView iv_start;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// ׼��������½����
			final Intent intentToLogin = new Intent(MainActivity.this, LoginActivity.class);
			// ׼������������
			final Intent intentToHome = new Intent(MainActivity.this, HomeActivity.class);

			super.handleMessage(msg);
			switch (msg.what) {
			case FAIL:
				startActivity(intentToLogin);
				Log.d(TAG,"���뵽�˵�½����");
				finish();
				break;
			case SUCCESS:
				//������̨������
				Intent intentToServie = new Intent(MainActivity.this,MainService.class);
				stopService(intentToServie);//ֹ֮ͣǰ�\�еķ��գ���Ҫ�����_�����ջ�ȡ��������
				startService(intentToServie);
				
				startActivity(intentToHome);
				Log.d(TAG,"���뵽��������");
				finish();
				break;

			default:
				startActivity(intentToLogin);
				finish();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ʼ������
		initImageView();
		// ���ұ��ػ�����Ϣ���ֻ��ź�����
		searchCache();
	}

	/**
	 * ���ؿ�ʼ����
	 */
	private void initImageView() {
		iv_start = (ImageView) findViewById(R.id.iv_start);
		AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
		animation.setDuration(3000);
		iv_start.startAnimation(animation);
		Log.d(TAG, "donghua");

	}

	/**
	 * ���ұ��ػ�����Ϣ���ֻ��ź�����
	 */
	private void searchCache() {
		String cachePhoneNum = Config.getCachePhoneNum(MainActivity.this);
		String cachePassword = Config.getCachePassword(MainActivity.this);

		if (TextUtils.isEmpty(cachePassword) || TextUtils.isEmpty(cachePhoneNum)) {
			// ��һ��Ϊ�վ���Ҫ���µ�½
			Message message = new Message();
			message.what = FAIL;
			mHandler.sendMessageDelayed(message, 3000);
		} else {
			// ���ӷ�������У���ֻ��ź�����
			NetConnection.connection( new SuccessConnection() {

				@Override
				public void onSuccess(String result) {
					Message message = new Message();

					if (result.equals(Config.SUCCESS)) {
						message.what = SUCCESS;
					} else{
						message.what = FAIL;
					}
					mHandler.sendMessageDelayed(message, 3000);
				}
			}, new FailConnection() {

				@Override
				public void onFail() {
					Message message = new Message();
					message.what = FAIL;
					mHandler.sendMessageDelayed(message, 3000);
					Log.d(TAG, "�Զ���½ʧ��");
				}
			}, Config.ACTION, Config.ACTION_CHECK_PASSWORD, 
					Config.DATA_PHONENUM, cachePhoneNum,
					Config.DATA_PASSWORD,
					cachePassword);
		}
	}
}
