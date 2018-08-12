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
 * �����������
 * 
 * @author yzas
 *
 */
public class NetConnection {

	protected static final String TAG = "test";

	/**
	 * ��½��ע�� ʱ��������ľ���ʵ��
	 * 
	 * @param url
	 *            ���������ӵ�ַ
	 * @param successConnection
	 *            �ɹ����ӵ��õĽӿ�
	 * @param failConnection
	 *            ʧ�����ӵ��õĽӿ�
	 * @param params
	 *            �ɱ����
	 */
	public static void connection(final SuccessConnection successConnection, final FailConnection failConnection,
			final String... params) {
		// �����ʱ���������߳���ִ��
		new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						try {
							// ��ȡ��Ҫ�ϴ��Ĳ����б�
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
							Log.d(TAG, "�ϴ�������");

							BufferedReader reader = new BufferedReader(
									new InputStreamReader(socket.getInputStream(), Config.CHARSET));
							String str = null;

							StringBuffer buffer = new StringBuffer();
							int index = -1;
							// ���ö�ȡ��ʱʱ��
							socket.setSoTimeout(3 * 1000);
								while ((str = reader.readLine()) != null) {
									if ((index = str.indexOf("eof")) != -1) {// �ж��Ƿ��н����ı�־
										break;
									}
									buffer.append(str);
								}
								buffer.append(str.substring(0, index));
								// ���������͹�������Ϣ����
								str = buffer.toString();
						

							Log.d(TAG, "��ȡ������");
							reader.close();
							socket.close();

							if (!TextUtils.isEmpty(str)) {
								if (successConnection != null) {
									Log.d(TAG,str);
									successConnection.onSuccess(str);
								}
							} else {
								if (successConnection != null) {
									Log.d(TAG,"��������Ϊ��");
									successConnection.onSuccess(Config.NO_BACK);
								}
							}

				
				}catch (JSONException e) {
					e.printStackTrace();
					Log.d(TAG, "�����ϴ�����ʧ��");
					if (failConnection != null) {
						failConnection.onFail();
					}
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					System.out.println("��ȡ��ʱ");
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
