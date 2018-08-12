package com.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.mobilesafe.R;

/**
 * Created by yzas on 2015/10/2.
 */
public class MySettingItem extends RelativeLayout {


    private TextView tv_title;
    private TextView tv_text;
    private CheckBox cb_select;
    private String cb_on;
    private String cb_off;

    /**
     * 初始化布局
     *
     * @param context
     */
    private void initView(Context context) {
        View.inflate(context, R.layout.setting_item, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_text = (TextView) findViewById(R.id.tv_text);
        cb_select = (CheckBox) findViewById(R.id.cb_update);
    }


    public MySettingItem(Context context) {
        super(context);
        initView(context);
    }

    public MySettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        String title =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe.mobilesafe","title2");
        cb_on =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe.mobilesafe","cb_on");
        cb_off =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe.mobilesafe","cb_off");

        tv_title.setText(title);//设置标题
        setComment(cb_off);//设置描述信息，默认关闭
    }



    public MySettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
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

    /**
     *判断组合控件是否被选中
     * @return  整体的是否选中状态和单选框的状态是一致的
     */
    public boolean isChecked(){
        return  cb_select.isChecked();
    }

    /**
     * 设置选中状态
     * @param result
     */
    public void setChecked(boolean result){
        //描述内容随着选择状态改变
        if(result){
            tv_text.setText(cb_on);
        }else{
            tv_text.setText(cb_off);
        }

        cb_select.setChecked(result);
    }

}
