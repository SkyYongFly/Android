package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.SearchPhoneBelongDB;

/**
 * 查询号码归属地
 */
public class SearchPhoneBelong extends Activity {

    private EditText et_phone;
    private TextView tv_show;
    private String phoneBelong ;
    private Vibrator  vibrator ;//系统提供的震动服务
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_belong);

        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_show  = (TextView) findViewById(R.id.tv_show);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //当输入的文本内容长度发生变化的时候调用
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=3){
                    String result = SearchPhoneBelongDB.search(SearchPhoneBelong.this,s.toString());
                    tv_show.setText(result);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    public void search(View v){
        String  phoneNum = et_phone.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNum)){
            Toast.makeText(SearchPhoneBelong.this, "查询号码不能为空", Toast.LENGTH_SHORT).show();

           //当输入内容为空时候震动
            Animation  shake  = AnimationUtils.loadAnimation(this,R.anim.shake);
            et_phone.startAnimation(shake);

            long[] pattern = {200,200,300,300,1000,2000};
            vibrator.vibrate(pattern,-1);//不重复

            return;
        }else{
            phoneBelong = SearchPhoneBelongDB.search(this,phoneNum);
        }
        tv_show.setText(phoneBelong);
    }


}
