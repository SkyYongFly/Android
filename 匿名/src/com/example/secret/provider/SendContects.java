package com.example.secret.provider;

import java.util.List;

import com.example.secret.daomain.Config;
import com.example.secret.net.HttpMethod;
import com.example.secret.net.NetConnection;
import com.example.secret.net.NetConnection.FaltureCallback;
import com.example.secret.net.NetConnection.SuccessCallBack;
import com.example.secret.util.ContentsUtil;

import android.content.Context;
import android.util.Log;

/**
 * 操作系统的联系人
 * 
 * @author yzas
 *
 */
public class SendContects {

	private static final String TAG = "test";

	/**
	 * 将联系人上传到服务器
	 * 
	 * @param mainActivity
	 */
	public static void sendContects(Context context, final SuccessSendPhoneNum successSendPhoneNum,
			final FailtureSendPhoneNum failtureSendPhoneNum) {
		// 获取手机中的号码
		String jsonPhoneNum = ContentsUtil.getPhones(context);

		Log.d(TAG, jsonPhoneNum);
		NetConnection.connection(Config.URI, HttpMethod.POST, new SuccessCallBack() {

			@Override
			public void onSuccess(String result) {
				if (successSendPhoneNum != null) {
					successSendPhoneNum.onSuccess(result);
				}
			}
		}, new FaltureCallback() {

			@Override
			public void onFail() {
				if (failtureSendPhoneNum != null) {
					failtureSendPhoneNum.onFailture();
				}
			}
		}, Config.CONNECT_ACTION, Config.ACTION_SEND_PHONENUM, 
				Config.CACHE_PHONE, Config.getCachePhone(context),
				Config.CONTACTS_PHONE, jsonPhoneNum);
	}

	public static interface SuccessSendPhoneNum {
		void onSuccess(String reString);
	}

	public static interface FailtureSendPhoneNum {
		void onFailture();
	}
}
