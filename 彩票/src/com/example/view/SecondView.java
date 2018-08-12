package com.example.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * ��һ������
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
		tv_secondView.setText("���ǵڶ�������");
		tv_secondView.setTextSize(30);
		
		Log.d(TAG, "���ǵڶ������棬�ұ�������");
		return tv_secondView;
		
	}
	
	
	/**
	 * ��ȡviewʵ��
	 * @return
	 */
	public  View getView(){
		return view;
	}


}
