package com.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.mobilesafe.R;

/**
 * Created by yzas on 2015/10/5.
 */
public class ClickableTextView  extends RelativeLayout {
    private TextView tv_title;
    private TextView tv_text;
    private String cb_on;

    /**
     * 初始化布局
     *
     * @param context
     */
    private void initView(Context context) {
        View.inflate(context, R.layout.address_background, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_text = (TextView) findViewById(R.id.tv_text);
    }


    public ClickableTextView(Context context) {
        super(context);
        initView(context);
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        String title =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe.mobilesafe","title2");
        cb_on =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe.mobilesafe","cb_on");

        tv_title.setText(title);//设置标题
        setComment(cb_on);//设置描述信息，默认关闭
    }



    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    /**
     * 设置描述信息
     * @param text
     */
    public void setComment(String text){
        tv_text.setText(text);
    }


}
