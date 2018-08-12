package com.example.viewtocuch;

import android.app.Application;
import android.content.Context;
import android.graphics.LinearGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TouchView extends LinearLayout {

	private static final String TAG = "test";
	private ImageView iv_touch;
	private Context context;
	

	public TouchView(Context context) {
		super(context);
		InitView(context);
	}

	public TouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		InitView(context);
		int  image_value = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.example.viewtocuch","image",0);
		Log.d(TAG, image_value+"");
		iv_touch.setImageResource(image_value);
		//iv_touch.setImageResource(R.drawable.id_betting);
	}

	private void InitView(Context context) {
		View view  = View.inflate(context, R.layout.item,this);
		iv_touch = (ImageView) view.findViewById(R.id.imageView_touch);
		
	}
	
	/**
	 * ªÒ»°ImageView
	 * @return
	 */
	public ImageView getImageView(){
		return iv_touch;
	}
	
	

}
