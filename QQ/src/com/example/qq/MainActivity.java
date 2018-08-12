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
 * 应用初始化界面 功能：1、完成应用的开始界面动画加载 2、版本检查更新（将此功能提取到应用设置里面，应用开始界面联网检查更新会耗时导致用户体验下降）
 * 3、检查本地缓存手机号和密码，从而决定接下里进入登陆界面还是应用主界面
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
			// 准备开启登陆界面
			final Intent intentToLogin = new Intent(MainActivity.this, LoginActivity.class);
			// 准备进入主界面
			final Intent intentToHome = new Intent(MainActivity.this, HomeActivity.class);

			super.handleMessage(msg);
			switch (msg.what) {
			case FAIL:
				startActivity(intentToLogin);
				Log.d(TAG,"进入到了登陆界面");
				finish();
				break;
			case SUCCESS:
				//开启后台主服务
				Intent intentToServie = new Intent(MainActivity.this,MainService.class);
				stopService(intentToServie);//停止之前運行的服務，主要重新開啓服務获取最新数据
				startService(intentToServie);
				
				startActivity(intentToHome);
				Log.d(TAG,"进入到了主界面");
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
		// 初始化布局
		initImageView();
		// 查找本地缓存信息：手机号和密码
		searchCache();
	}

	/**
	 * 加载开始动画
	 */
	private void initImageView() {
		iv_start = (ImageView) findViewById(R.id.iv_start);
		AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
		animation.setDuration(3000);
		iv_start.startAnimation(animation);
		Log.d(TAG, "donghua");

	}

	/**
	 * 查找本地缓存信息：手机号和密码
	 */
	private void searchCache() {
		String cachePhoneNum = Config.getCachePhoneNum(MainActivity.this);
		String cachePassword = Config.getCachePassword(MainActivity.this);

		if (TextUtils.isEmpty(cachePassword) || TextUtils.isEmpty(cachePhoneNum)) {
			// 有一个为空就需要重新登陆
			Message message = new Message();
			message.what = FAIL;
			mHandler.sendMessageDelayed(message, 3000);
		} else {
			// 链接服务器，校验手机号和密码
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
					Log.d(TAG, "自动登陆失败");
				}
			}, Config.ACTION, Config.ACTION_CHECK_PASSWORD, 
					Config.DATA_PHONENUM, cachePhoneNum,
					Config.DATA_PASSWORD,
					cachePassword);
		}
	}
}
