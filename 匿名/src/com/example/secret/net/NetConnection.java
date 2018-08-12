package com.example.secret.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.secret.daomain.Config;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

/**
 * 连接网络，实现数据的上传和服务器返回值的获取
 * 
 * @author yzas
 *
 */
public class NetConnection {
	protected static final String TAG = "test";
	private static String reString;

	/**
	 * 连接网络的具体实现
	 */
	public static void connection(final String uri, final HttpMethod method, final SuccessCallBack successCallBack,
			final FaltureCallback failCallback, final String... params) {

		// 开启线程去执行耗时任务
		// new AsyncTask<Void, Void, String>() {
		//
		// @Override
		// protected String doInBackground(Void... params) {
		// // 获取提交的参数，按照一定的格式排列
		// StringBuffer stringBuffer = new StringBuffer();
		// for (int i = 0; i < params.length; i += 2) {
		// stringBuffer.append(params[i]).append("=").append(params[i +
		// 1]).append("&");
		// }
		// Log.d(TAG,stringBuffer.toString());
		//
		// try {
		// URLConnection connection = null;
		// switch (method) {
		// case POST: {// 如果是POST提交
		// URL url = new URL(uri);
		// connection = url.openConnection();
		// connection.getDoOutput();
		// BufferedWriter bWriter = new BufferedWriter(
		// new OutputStreamWriter(connection.getOutputStream(),
		// Config.CHARSET));
		// bWriter.write(stringBuffer.toString());
		// bWriter.flush();
		// }
		// break;
		//
		// default: {// 其他的默认是GET提交
		// connection = new URL(uri + "?" +
		// stringBuffer.toString()).openConnection();
		// }
		// break;
		// }
		// //获取输入流，得到服务器返回的对象
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(connection.getInputStream(),Config.CHARSET));
		// String line = null;
		// StringBuffer buffer = new StringBuffer();
		// if ((line = reader.readLine())!=null) {
		// buffer.append(line);
		// }
		// Log.d(TAG,buffer.toString());
		// return buffer.toString();
		//
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// return null;
		// }
		//
		// /**
		// * 返回的结果
		// */
		// protected void onPostExecute(String result) {
		//
		// if(result!=null){
		// Log.d(TAG,result);
		// if(successCallBack!=null){
		// successCallBack.onSuccess(result);
		// }
		// }else{
		// if(failCallback!=null){
		// Log.d(TAG,"fail connection adadd");
		// failCallback.onFail();
		// }
		// }
		//
		// };
		//
		// }.executeOnExecutor(Executors.newCachedThreadPool());

		new Thread(new Runnable() {

			@Override
			public void run() {
				// 获取提交的参数，按照一定的格式排列
				StringBuffer stringBuffer = new StringBuffer();
				for (int i = 0; i < params.length; i += 2) {
					stringBuffer.append(params[i]).append("=").append(params[i + 1]).append("&");
				}
				Log.d(TAG, stringBuffer.toString());

				try {
					URLConnection connection = null;
					switch (method) {
					case POST: {// 如果是POST提交
						URL url = new URL(uri);
						connection = url.openConnection();
						connection.setDoOutput(true);
						BufferedWriter bWriter = new BufferedWriter(
								new OutputStreamWriter(connection.getOutputStream(), Config.CHARSET));
						bWriter.write(stringBuffer.toString());
						bWriter.flush();
					}
						break;

					default: {// 其他的默认是GET提交
						connection = new URL(uri + "?" + stringBuffer.toString()).openConnection();
					}
						break;
					}
					
					// 获取输入流，得到服务器返回的对象
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(connection.getInputStream(), Config.CHARSET));
					String line = null;
					StringBuffer buffer = new StringBuffer();
					if ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					Log.d(TAG, buffer.toString());
					
					//服务器返回的数据
					reString = buffer.toString();

					if (!TextUtils.isEmpty(reString)) {

						if (successCallBack != null) {
							successCallBack.onSuccess(reString);
						}
					} else {
						if (failCallback != null) {
							Log.d(TAG, "fail connection adadd");
							failCallback.onFail();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (failCallback != null) {
						Log.d(TAG, "fail connection sg");
						failCallback.onFail();
					}
				}

			}
		}).start();

	}

	/**
	 * 成功的时候回调的接口
	 * 
	 * @author yzas
	 *
	 */
	public static interface SuccessCallBack {
		void onSuccess(String result);

	}

	/**
	 * 失败的时候回调的接口
	 * 
	 * @author yzas
	 *
	 */
	public static interface FaltureCallback {
		void onFail();
	}

}
