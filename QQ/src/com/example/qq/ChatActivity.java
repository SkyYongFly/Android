package com.example.qq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.qq.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	private EditText et_message;
	private BufferedWriter writer;
	private String getMessage;
	public String TAG = "test";
	private List<String> listMsg = new ArrayList<String>();
	// ��ǵڼ���λ���Ƿ�����Ϣ���ǽ�����Ϣ,������true��������false
	private List<Boolean> listFlag = new ArrayList<Boolean>();
	private ListView lv_message;
	private MyAdapter adapter;
	private String desphone;//Ҫ�l����Ϣ��Ŀ�ĵ�ַ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		desphone = getIntent().getStringExtra("desphone");
		
		et_message = (EditText) findViewById(R.id.et_message);
		lv_message = (ListView) findViewById(R.id.lv_showMessage);
		adapter = new MyAdapter();
		lv_message.setAdapter(adapter);
		//�����ͻ��˺ͷ������˵��ȶ�����
		new connectToNet().start();
		//������Ϣ
		//������Ϣ
		acceptMessage();
	}

	/**
	 * ��ȡ�����������������Ϣ
	 */
	private void acceptMessage() {
		
	}

	/**
	 * �����ť������Ϣ
	 * 
	 * @param v
	 */
	public void sendMessage(View v) {
		try {

			String message = et_message.getText().toString();
			if (TextUtils.isEmpty(message)) {
				Toast.makeText(ChatActivity.this, "��������Ϊ��", Toast.LENGTH_SHORT).show();
			} else {
				JSONObject object = new JSONObject();
				object.put("message", message);
				object.put("action", "chat_message");
				object.put("desphone", desphone);
				Log.d(TAG, "������ϢΪ:"+object.toString());
				writer.write(object.toString() + "eof\n");
				listMsg.add(message);
				listFlag.add(true);
				adapter.notifyDataSetChanged();
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

	/**
	 * ���ӵ�������
	 */
	private class connectToNet extends Thread {
		@Override
		public void run() {
			super.run();
			try {

				Socket socket = new Socket(Config.SERVER_URL, Config.PORT);
				BufferedReader reader;
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				JSONObject object = new JSONObject();
				object.put("action", "chat");
				object.put("desphone", desphone);
				writer.write(object.toString() + "eof\n");
				
				
				
				while (true) {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					String line = null;
					int index = -1;
					StringBuffer buffer = new StringBuffer();
					Log.d(TAG, "��ʼ��ȡ");
					while ((line = reader.readLine()) != null) {
						if ((index = line.indexOf("eof")) != -1) {
							break;
						}
						buffer.append(line);
					}
					buffer.append(line.substring(0, index));
					getMessage = buffer.toString();
					Log.d(TAG, "�յ���Ϣ" + getMessage);
					// �����յ�����Ϣ��ʾ
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							listMsg.add(getMessage);
							listFlag.add(false);
							adapter.notifyDataSetChanged();
						}
					});
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listMsg == null ? 0 : listMsg.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder;
			if (convertView == null) {
				if (listFlag.get(position)) {//�l����Ϣ
					view = ChatActivity.this.getLayoutInflater().inflate(R.layout.message_item, null);
					holder = new ViewHolder();
					holder.iv_showPhoto = (ImageView) view.findViewById(R.id.iv_showPhoto);
					holder.tv_message = (TextView) view.findViewById(R.id.tv_message);
					view.setTag(holder);
				} else {//������Ϣ
					view = ChatActivity.this.getLayoutInflater().inflate(R.layout.message_receive, null);
					holder = new ViewHolder();
					holder.iv_showPhoto = (ImageView) view.findViewById(R.id.iv_receive_show_photo);
					holder.tv_message = (TextView) view.findViewById(R.id.tv_receive_message);
					view.setTag(holder);
				}
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}

			holder.tv_message.setText(listMsg.get(position));
			return view;
		}

	}

	private static class ViewHolder {
		TextView tv_message;
		ImageView iv_showPhoto;
	}
}
