package com.example.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 第一个界面
 * @author yzas
 *
 */
public class SecondView  extends BaseView {
	private static final String TAG = "test";
	private Context context;
	private View view;
	
	public SecondView(Context context){
		this.context = context;
		view = init();
	}
	
	
	public  View  init(){
		TextView tv_secondView = new TextView(context);
		LayoutParams layoutParams = tv_secondView.getLayoutParams();
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,layoutParams.FILL_PARENT);
		tv_secondView.setLayoutParams(layoutParams);
		tv_secondView.setText("我是第二个界面");
		tv_secondView.setTextSize(30);
		
		Log.d(TAG, "我是第二个界面，我被创建了");
		return tv_secondView;
		
	}
	
	
	/**
	 * 获取view实例
	 * @return
	 */
	public  View getView(){
		return view;
	}


}
