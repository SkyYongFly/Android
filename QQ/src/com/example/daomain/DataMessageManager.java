package com.example.daomain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.db.SQLiteUtil;
import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;
import com.example.qq.Config;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DataMessageManager {
	private static Context context;
	protected static final String TAG = "test";
	protected static final int SUCCESS = 0;
	protected static final int NULL = 1;
	protected static final int FAIL = 2;
	private static List<DataMessage> listMessage = new ArrayList<DataMessage>();

	private static Handler mHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS: {
				// 将消息保存到本地数据库
				SQLiteUtil sqLiteUtil = new SQLiteUtil(context);
				listMessage = (List<DataMessage>) msg.obj;
				if (listMessage != null) {
					for (DataMessage m : listMessage) {
						if (sqLiteUtil.exist(m)) {// 信息已经存在
							Log.d(TAG, "消息已经存在");
						} else {// 不存在
							Log.d(TAG, "消息不存在");
							sqLiteUtil.addMessage(m);
						}
					}
				}
			}
				break;
			case NULL: {

			}
				break;
			case FAIL: {

			}
			default:
				break;
			}

		}

	};

	public DataMessageManager(Context context) {
		DataMessageManager.context = context;
	}

	/**
	 * 获取指定账号缓存在服务器端的未读的消息
	 * 
	 * @param context
	 * @return
	 */
	public void solveDataMessages() {
		// 联网请求缓存在服务器中的未读的消息
		NetConnection.connection(new SuccessConnection() {

			@Override
			public void onSuccess(String result) {
				Message message = new Message();

				if (Config.NO_UNREAD_DATA.equals(result)) {// 没有未读的数据
					message.what = NULL;
				} else {
					try {
						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject object = jsonArray.getJSONObject(i);
							DataMessage datamessage = new DataMessage();
							datamessage.setType((String) object.get("type"));
							datamessage.setPersonal((String) object.get("personal"));
							datamessage.setDesphone((String) object.get("desphone"));
							datamessage.setBody((String) object.get("body"));
							datamessage.setHasread((String) object.get("hasread"));
							listMessage.add(datamessage);

						}
						message.what = SUCCESS;
						message.obj = listMessage;

					} catch (JSONException e) {
						e.printStackTrace();
						message.what = FAIL;
					} finally {
						mHandler.sendMessage(message);
					}
				}
			}
		}, new FailConnection() {
			@Override
			public void onFail() {
				Message message = new Message();
				message.what = SUCCESS;
				mHandler.sendMessage(message);
			}
		}, Config.ACTION, Config.GET_NOREAD_MESSAGES, // 告诉服务器要做的事情是获取未读的数据
				Config.DATA_PHONENUM, Config.getCachePhoneNum(context));// 账号

	}

}
