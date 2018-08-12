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
		dialog.setTitle("下载图片");
		dialog.setMessage("正在下载");
	}
	
	/**
	 * 点击按钮下载图片
	 * @param v
	 */
	public void download(View v){
		new MyTask().execute(url);
	}
	
	/**
	 * 异步任务，执行连接网路下载图片的任务
	 *
	 */
	private class MyTask extends AsyncTask<String,Void,Bitmap>{
		//任务开始之前执行的方法
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		//任务执行
		@Override
		protected Bitmap doInBackground(String... params) {
		//数组的第一个参数就是网路地址，泛型参数适合AsyncTask对应的
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
		
		//执行一些显示任务进度的工作
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		//执行UI任务
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			dialog.dismiss();
			iv_showPicture.setImageBitmap(result);
		}
		
	}
}
