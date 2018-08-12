package com.example.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private String url;
	private ProgressDialog dialog;
	private ImageView iv_showPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		url = "http://10.50.12.250:8080/1.jpg";
		
		iv_showPicture = (ImageView) findViewById(R.id.iv_show_picture);
		
		dialog = new ProgressDialog(MainActivity.this);
		//dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("����ͼƬ");
		dialog.setMessage("��������");
	}
	
	/**
	 * �����ť����ͼƬ
	 * @param v
	 */
	public void download(View v){
		new MyTask().execute(url);
	}
	
	/**
	 * �첽����ִ��������·����ͼƬ������
	 *
	 */
	private class MyTask extends AsyncTask<String,Void,Bitmap>{
		//����ʼ֮ǰִ�еķ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		//����ִ��
		@Override
		protected Bitmap doInBackground(String... params) {
		//����ĵ�һ������������·��ַ�����Ͳ����ʺ�AsyncTask��Ӧ��
			try {
				URL  url = new URL(params[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				InputStream inputStream = connection.getInputStream();
				Bitmap  bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//ִ��һЩ��ʾ������ȵĹ���
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		//ִ��UI����
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			dialog.dismiss();
			iv_showPicture.setImageBitmap(result);
		}
		
	}
}
