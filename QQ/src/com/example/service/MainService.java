package com.example.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.daomain.DataMessageManager;
import com.example.daomain.Friend;
import com.example.net.NetConnection;
import com.example.net.NetConnection.FailConnection;
import com.example.net.NetConnection.SuccessConnection;
import com.example.qq.Config;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 后台主服务，主要用于 1、应用进入主界面的之前加载未读消息 2、实时接收服务器端传过来的消息
 * 
 * @author yzas
 *
 */
public class MainService extends Service {
	protected static final String TAG = "test";
	protected static final int GET_FRIENDS_SUCCESS = 0;
	protected static final int NO_FRIENDS = 1;
	protected static final int GET_FRIENDS_FAIL = 2;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			List<Friend> listFriend = new ArrayList<Friend>();
			switch (msg.what) {
			case GET_FRIENDS_SUCCESS:
				try {
					JSONArray array = new JSONArray((String) msg.obj);
					Log.d(TAG,"好友信息"+array.toString());
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						Friend friend = new Friend();
						friend.setPhoneNum(object.getString("phoneNum"));
						friend.setNickName(object.getString("nickName"));
						friend.setSign(object.getString("sign"));
						listFriend.add(friend);
					}
					if (listFriend.size() > 0) {
						SaveFriends.save(listFriend, getApplicationContext());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;

			case NO_FRIENDS:
				Toast.makeText(getApplicationContext(), "还没有好友，赶快添加好友吧", Toast.LENGTH_SHORT).show();
				break;

			case GET_FRIENDS_FAIL:
				Toast.makeText(getApplicationContext(), "网路异常，获取好友失败", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}

		}

	};

	@Override
	public void onCreate() {
		super.onCreate();
		// 获取未读数据
		DataMessageManager manager = new DataMessageManager(getApplicationContext());
		manager.solveDataMessages();
		// 获取好友数据
		new MyGetFriends().getFriends();
		// 和服务器建立连接
		new MyThread().start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class MyThread extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				Socket socket = new Socket(Config.SERVER_URL, Config.PORT);

				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream(), Config.CHARSET));
				JSONObject object = new JSONObject();
				object.put("action", "service_socket");
				object.put(Config.DATA_PHONENUM, Config.getCachePhoneNum(getApplicationContext()));
				writer.write(object.toString() + "eof\n");
				writer.flush();
				Log.d(TAG, "上传结束！");

				while (true) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(socket.getInputStream(), Config.CHARSET));
					String str = null;

					StringBuffer buffer = new StringBuffer();
					int index = -1;
					// 设置读取超时时间
					socket.setSoTimeout(3 * 1000);
					while ((str = reader.readLine()) != null) {
						if ((index = str.indexOf("eof")) != -1) {// 判断是否有结束的标志
							break;
						}
						buffer.append(str);
					}
					buffer.append(str.substring(0, index));
					// 服务器发送过来的消息数据
					str = buffer.toString();
					Log.d(TAG, "接收到服务器信息" + str);
					// 将消息以广播的形式发送给前台显示
					Intent intent = new Intent("com.example.qq.mybroadcast");
					intent.putExtra("message", str);
					sendBroadcast(intent);
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 链接服务器获取好友信息保存到本地文件
	 * 
	 * @author yzas
	 *
	 */
	private class MyGetFriends {

		public void getFriends() {
			NetConnection.connection(new SuccessConnection() {

				@Override
				public void onSuccess(String result) {
					Message message = new Message();
					if (Config.NO_FRIENDS.equals(result)) {
						message.what = NO_FRIENDS;
					} else {
						message.obj = result;
						message.what = GET_FRIENDS_SUCCESS;
					}
					mHandler.sendMessage(message);
				}
			}, new FailConnection() {

				@Override
				public void onFail() {
					Message message = new Message();
					message.what = GET_FRIENDS_FAIL;
					mHandler.sendMessage(message);
				}
			}, Config.ACTION, Config.GET_FRIENDS, Config.DATA_PHONENUM,
					Config.getCachePhoneNum(getApplicationContext()));
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
