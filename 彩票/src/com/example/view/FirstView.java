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
public class FirstView  extends BaseView{
	private static final String TAG = "test";
	
	private Context context;
	private View view;
	
	public FirstView(Context context){
		this.context = context;
		
		view =init();
	}
	
	
	public  View  init(){
		TextView tv_firstView = new TextView(context);
		LayoutParams layoutParams = tv_firstView.getLayoutParams();
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,layoutParams.FILL_PARENT);
		tv_firstView.setLayoutParams(layoutParams);
		tv_firstView.setText("���ǵ�һ������");
		tv_firstView.setTextSize(30);
		
		Log.d(TAG, "���ǵ�һ�����棬�ұ�������");
		return tv_firstView;
		
	}
	
	/**
	 * ��ȡviewʵ��
	 * @return
	 */
	public  View getView(){
		return view;
	}

	
}
