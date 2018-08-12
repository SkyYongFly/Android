package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


public class FindSetting4   extends SetBaseActivity{

    private CheckBox  cb_setSafe ;
    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_setting4);

        cb_setSafe = (CheckBox) findViewById(R.id.cb_open);
        tv_show = (TextView) findViewById(R.id.tv_show);

        //根据之前的设置设定单选框状态
        String setSafe = sp.getString("setSafe",null);
        if("true".equals(setSafe)){
            cb_setSafe.setChecked(true);
            tv_show.setText("防盗保护成功开启");

        }else{
            cb_setSafe.setChecked(false);
            tv_show.setText("防盗保护没有开启");
        }

    }

    //开启防盗保护选择按钮触发事件
    public void setSafe(View v){
        SharedPreferences.Editor  editor = sp.edit();
        if(cb_setSafe.isChecked()){
            tv_show.setText("防盗保护成功开启");

            editor.putString("setSafe","true");
            editor.commit();
        }else{
            tv_show.setText("防盗保护没有开启");
            editor.putString("setSafe","false");
            editor.commit();
        }
    }



    @Override
    public void showNext() {
        Intent intent = new Intent(this,FindLocationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,FindSetting3.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,FindSetting3.class);
        startActivity(intent);
        finish();
    }
}
