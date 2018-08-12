package com.example.qqtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView tv_showMessage;
	public int PORT = 1235;
	public String ADDRESS = "10.50.12.250";
	private EditText et_messge;
	private BufferedWriter writer;
	private String getMessage;
	public String TAG = "test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_showMessage = (TextView) findViewById(R.id.tv_showMessage);
		et_messge = (EditText) findViewById(R.id.et_message);

		new MyThread().start();
	}

	private class MyThread extends Thread {

		@Override
		public void run() {
			super.run();
			try {

				Socket socket = new Socket(ADDRESS, PORT);
				BufferedReader reader ;
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				while (true) {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
					String line = null;
					int index = -1;
					StringBuffer buffer = new StringBuffer();
					Log.d(TAG, "开始读取");
					while ((line = reader.readLine()) != null) {
						if ((index = line.indexOf("eof")) != -1) {
							break;
						}
						buffer.append(line);
					}
					buffer.append(line.substring(0, index));
					getMessage = buffer.toString();
					Log.d(TAG, "收到消息" + getMessage);
					// 将接收到的消息显示
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							tv_showMessage.append(getMessage);
						}
					});
				}

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 发送消息
	 * 
	 * @param v
	 */
	public void send(View v) {

		try {

			String message = et_messge.getText().toString();
			if (TextUtils.isEmpty(message)) {
				Toast.makeText(MainActivity.this, "发送内容为空", Toast.LENGTH_SHORT).show();
			} else {
				writer.write(message + "eof\n");
				Log.d(TAG, "发送数据" + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
