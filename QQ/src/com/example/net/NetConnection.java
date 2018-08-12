package com.example.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyStore.PrivateKeyEntry;

import org.apache.http.cookie.SetCookie;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.qq.Config;

import android.R;
import android.text.TextUtils;
import android.util.Log;

/**
 * 链接网络服务
 * 
 * @author yzas
 *
 */
public class NetConnection {

	protected static final String TAG = "test";

	/**
	 * 登陆、注册 时连接网络的具体实现
	 * 
	 * @param url
	 *            服务器连接地址
	 * @param successConnection
	 *            成功连接调用的接口
	 * @param failConnection
	 *            失败链接调用的接口
	 * @param params
	 *            可变参数
	 */
	public static void connection(final SuccessConnection successConnection, final FailConnection failConnection,
			final String... params) {
		// 网络耗时任务在子线程中执行
		new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						try {
							// 获取需要上传的参数列表
							JSONObject object = new JSONObject();
							for (int i = 0; i < params.length; i += 2) {
								object.put(params[i], params[i + 1]);
							}
							String paramUp = object.toString();
							Log.d(TAG, paramUp);

							Socket socket = new Socket(Config.SERVER_URL, Config.PORT);
							BufferedWriter writer = new BufferedWriter(
									new OutputStreamWriter(socket.getOutputStream(), Config.CHARSET));
							writer.write(paramUp + "eof\n");
							writer.flush();
							Log.d(TAG, "上传结束！");

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
						

							Log.d(TAG, "读取结束！");
							reader.close();
							socket.close();

							if (!TextUtils.isEmpty(str)) {
								if (successConnection != null) {
									Log.d(TAG,str);
									successConnection.onSuccess(str);
								}
							} else {
								if (successConnection != null) {
									Log.d(TAG,"返回数据为空");
									successConnection.onSuccess(Config.NO_BACK);
								}
							}

				
				}catch (JSONException e) {
					e.printStackTrace();
					Log.d(TAG, "数据上传解析失败");
					if (failConnection != null) {
						failConnection.onFail();
					}
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					System.out.println("读取超时");
					if (failConnection != null) {
						failConnection.onFail();
					}
				}catch (Exception e) {
					e.printStackTrace();
					Log.d(TAG, e.toString());
					if (failConnection != null) {
						failConnection.onFail();
					}
				} 
					}
				}
			).start();
		
	}

	public static interface SuccessConnection {
		void onSuccess(String result);
	}

	public static interface FailConnection {
		void onFail();
	}
}
