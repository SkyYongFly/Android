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
 * �Զ��岼�֣����ڽ����м䲼�ֵ�����
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
	 * ��ʼ�����ֿؼ�����
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
		// ��ʼ�����ֿؼ�����
		initView(context);

		// ��ȡ���õĲ���
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MiddleItem);
		// ----��ȡ��Ʊ�����־
		int drawable = typedArray.getResourceId(R.styleable.MiddleItem_iv_sign, R.drawable.ic_launcher);
		// ----��ȡ��ʾ��Ϣ
		String tv_tip = typedArray.getString(R.styleable.MiddleItem_tv_title);
		// ----��ȡ��Ʊ��Ϣ
		String tv_message = typedArray.getString(R.styleable.MiddleItem_tv_message);

		typedArray.recycle();

		// �����������
		setSign(drawable);
		setTipAndMessage(tv_tip, tv_message);

	}

	/**
	 * ������ʾ�Ͳ�Ʊ��Ϣ
	 * 
	 * @param tv_tip
	 * @param tv_message
	 */
	private void setTipAndMessage(String tv_tip, String tv_message) {
		tv_show_tip.setText(tv_tip);
		tv_show_message.setText(tv_message);
	}

	/**
	 * ���ò�Ʊ�����־
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
	 * ��������Ͷע��ImageView����
	 * @return
	 */
	public ImageView getSettingImageView(){
		return iv_betting;
	}

}
