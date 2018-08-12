package com.example;

import java.util.LinkedList;

import com.example.view.BaseView;
import com.example.view.ViewControl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.usage.UsageEvents.Event;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "test";
	private ViewControl viewControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewControl = new ViewControl(MainActivity.this);
		viewControl.showStart();
	}

	
	
	
	
	/**
	 * 处理回退键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: {// 按下返回键
			LinkedList<Class<? extends BaseView> > historyList = viewControl.getHistoryList();
			Log.d(TAG, "list大小"+historyList.size());
			if(historyList.size() == 1){
				//弹出对话框
				AlertDialog.Builder builder = new Builder(MainActivity.this);//getApplicationContext会出错
				builder.setMessage("确认退出吗？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定",new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						viewControl.clearHistoryList();
						MainActivity.this.finish();
						
					}
				});
				builder.setNegativeButton("取消",new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
				
						
				
			}else{
				viewControl.backUI();
				return  false;
			}
			
			
		}
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
