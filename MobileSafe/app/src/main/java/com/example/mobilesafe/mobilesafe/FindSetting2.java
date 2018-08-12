package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.MySettingItem;


public class FindSetting2 extends SetBaseActivity {

    private  String  hasSetBindStr = "hasSetBind";
    private  String    hasSetBind;
    private MySettingItem settingBindSim;

    public FindSetting2() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_setting2);

        //得到手机管理者，为后来的和获取sim卡号
        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        settingBindSim = (MySettingItem) findViewById(R.id.settingBindSim);

        //获取之前是否绑定过sim卡
        hasSetBind = sp.getString(hasSetBindStr,null);
        if("true".equals(hasSetBind)){//"sim卡绑定了"
            settingBindSim.setChecked(true);
            
        }else{//sim卡没有绑定
            settingBindSim.setChecked(false);
            
        }

        settingBindSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor  editor = sp.edit();

                if(settingBindSim.isChecked()){//如果选中代表要绑定SIM卡
                    //settingBindSim.setComment("sim卡绑定了");
                    settingBindSim.setChecked(false);
                    //将状态保存起来
                    editor.putString(hasSetBindStr,"true");
                    //得到手机sim卡序列号
                    String  simNum = telephonyManager.getSimSerialNumber();
                    editor.putString("simNum",simNum);
                    editor.commit();

                }else{//，没有选中则没有绑定SIM卡
                    //settingBindSim.setComment("sim卡没有绑定");
                    settingBindSim.setChecked(true);
                    editor.putString(hasSetBindStr, "false");
                    //手机sim卡序列号为空
                    editor.putString("simNum",null);
                    editor.commit();
                }
            }
        });

    }

    @Override
    public void showNext() {
        hasSetBind = sp.getString(hasSetBindStr,null);
        if("false".equals(hasSetBind)){//没有绑定sim卡
            Toast.makeText(this,"sim卡还未绑定",Toast.LENGTH_SHORT).show();
            return;
        }else {//绑定了sim卡
            Intent intent = new Intent(this, FindSetting3.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,FindSetting1.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,FindSetting1.class);
        startActivity(intent);
        finish();
    }
}
