package com.example.view;

import com.example.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义布局，用于界面中间布局的子项
 * 
 * @author yzas
 *
 */
public class MyMiddleItemView extends LinearLayout {

	private static final String TAG = "test";
	private ImageView iv_show_sign;
	private TextView tv_show_tip;
	private TextView tv_show_message;
	// private ImageView iv_betting;
	private ImageView iv_betting;

	/**
	 * 初始化布局控件引用
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		 View.inflate(context, R.layout.middle_item_layout, this);
		iv_show_sign = (ImageView) findViewById(R.id.iv_show_sign);
		tv_show_tip = (TextView) findViewById(R.id.tv_show_title);
		tv_show_message = (TextView) findViewById(R.id.tv_show_message);
		iv_betting = (ImageView) findViewById(R.id.iv_betting);

	}

	public MyMiddleItemView(Context context) {
		super(context);
		initView(context);
	}

	public MyMiddleItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化布局控件引用
		initView(context);

		// 获取设置的参数
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MiddleItem);
		// ----获取彩票种类标志
		int drawable = typedArray.getResourceId(R.styleable.MiddleItem_iv_sign, R.drawable.ic_launcher);
		// ----获取提示信息
		String tv_tip = typedArray.getString(R.styleable.MiddleItem_tv_title);
		// ----获取彩票信息
		String tv_message = typedArray.getString(R.styleable.MiddleItem_tv_message);

		typedArray.recycle();

		// 设置相关属性
		setSign(drawable);
		setTipAndMessage(tv_tip, tv_message);

	}

	/**
	 * 设置提示和彩票信息
	 * 
	 * @param tv_tip
	 * @param tv_message
	 */
	private void setTipAndMessage(String tv_tip, String tv_message) {
		tv_show_tip.setText(tv_tip);
		tv_show_message.setText(tv_message);
	}

	/**
	 * 设置彩票种类标志
	 * 
	 * @param drawable
	 */
	private void setSign(int drawable) {
		Log.d(TAG, iv_show_sign.toString());
		iv_show_sign.setImageResource(drawable);
	}

	public void setTip(String tip) {
		tv_show_tip.setText(tip);

	}

	public void setMessage(String message) {
		tv_show_message.setText(message);

	}
	
	/**
	 * 返回立即投注的ImageView对象
	 * @return
	 */
	public ImageView getSettingImageView(){
		return iv_betting;
	}

}
