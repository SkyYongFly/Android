package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.service.ComingPhoneBelong;
import com.example.service.LockAppService;
import com.example.service.StopBlackNum;
import com.example.ui.ClickableTextView;
import com.example.ui.MySettingItem;
import com.example.utils.ServiceUtil;


public class SettingActivity extends Activity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MySettingItem  settingUpdate;
    private MySettingItem  settingComingPhone;
    private MySettingItem  settingStopBlack;
    private MySettingItem  settingLockApp;

    private ClickableTextView setToastBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settting);


        settingUpdate = (MySettingItem) findViewById(R.id.settingUpdate);
        settingComingPhone = (MySettingItem) findViewById(R.id.settingComingPhone);
        settingStopBlack = (MySettingItem) findViewById(R.id.settingStopBlack);
        settingLockApp= (MySettingItem) findViewById(R.id.lockApp);
        setToastBackground = (ClickableTextView) findViewById(R.id.selectToastBackground);

        sp = getApplication().getSharedPreferences("isUpdateFile",MODE_PRIVATE);
        editor = sp.edit();

        //一进设置界面显示自动更新是否开启
        String  isUpdate = sp.getString("isUpdate",null);
        if("true".equals(isUpdate)){
            settingUpdate.setChecked(true);
        }else{
            settingUpdate.setChecked(false);
        }


        settingUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否选中自动升级提醒
                if (settingUpdate.isChecked()) {//选中自动升级提醒
                   // settingUpdate.setComment("自动更新已经开启");
                    settingUpdate.setChecked(false);
                    //将自动更新提示的信息保存起来，当开机的时候判断是否设置了自动更新
                    editor.putString("isUpdate", "true");
                    editor.commit();
                } else {//没有选中自动升级提醒
                    //settingUpdate.setComment("自动更新已经关闭");
                    settingUpdate.setChecked(true);
                    editor.putString("isUpdate", "false");
                    editor.commit();
                }
            }
        });


        //一进设置界面显示来电归属地显示是否开启
        //根据来电归属地的后台服务是否存在来判断
        boolean  isServive  = ServiceUtil.isRunning(this, "com.example.service.ComingPhoneBelong");
        if(isServive){
            settingComingPhone.setChecked(true);
        }else{
            settingComingPhone.setChecked(false);
        }

        settingComingPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ComingPhoneBelong.class);
                if(settingComingPhone.isChecked()){
                    //settingComingPhone.setComment("来电归属地显示已经打开");
                    settingComingPhone.setChecked(false);
                    //开启服务
                    startService(intent);
                }else{
                    //settingComingPhone.setComment("来电归属地显示已经关闭");
                    settingComingPhone.setChecked(true);
                    //关闭服务
                    stopService(intent);
                }
            }
        });

        //锁定应用程序
        boolean  isRunning  = ServiceUtil.isRunning(this, "com.example.service.LockAppService");
        if(isRunning){
            settingLockApp.setChecked(true);
        }else{
            settingLockApp.setChecked(false);
        }

        settingLockApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SettingActivity.this, LockAppService.class);
                if(settingLockApp.isChecked()){
                    settingLockApp.setChecked(false);
                    editor.putBoolean("isLockApp",false);

//                    final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
//
//                    //加载布局文件
//                    View view = View.inflate(SettingActivity.this, R.layout.setpassword, null);
//                    //获取控件
//                    final EditText et_password1 = (EditText) view.findViewById(R.id.et_password1);
//                    final EditText et_password2 = (EditText) view.findViewById(R.id.et_password2);
//                    Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
//                    Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
//
//
//                    //确认输入的密码
//                    bt_confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            //获取输入的两次密码
//                            final String pwd1 = et_password1.getText().toString().trim();
//                            final String pwd2 = et_password2.getText().toString().trim();
//
//                            if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
//                                Toast.makeText(SettingActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
//                                return;
//                            } else {
//                                if (!pwd1.equals(pwd2)) {
//                                    Toast.makeText(SettingActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
//                                    return;
//                                } else {
//                                    SharedPreferences.Editor editor = sp.edit();
//                                    editor.putString("lockedAppPwd", pwd1);
//                                    editor.commit();
//                                }
//                            }
//                        }
//                    });
//
//                    //取消输入的密码
//                    bt_cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog = builder.create();
//                    dialog.setView(view, 0, 0, 0, 0);
//                    dialog.show();
                    //开启服务
                    //关闭服务
                    stopService(intent2);
                }else {
                    settingLockApp.setChecked(true);
                    editor.putBoolean("isLockApp",true);

                    startService(intent2);
                }

                editor.commit();

            }
        });

        //设置自定义土司的背景风格
        int which = sp.getInt("background",0);
        final String [] items = {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
        if(TextUtils.isEmpty(which+"")){
            Log.d("test","没有设置风格");
        }else{
            setToastBackground.setComment(items[which]);
        }

        setToastBackground.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder  builder  = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("归属地提示框风格");
                builder.setSingleChoiceItems(items,0,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setToastBackground.setComment(items[which]);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("background",which);
                        editor.commit();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("取消",null);
                builder.show();

            }


        });


        //设置拦截黑名单功能是是否开启
        boolean  isService = ServiceUtil.isRunning(this, "com.example.service.StopBlackNum");
        if(isService){
            settingStopBlack.setChecked(true);
        }else{
            settingStopBlack.setChecked(false);
        }

        settingStopBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, StopBlackNum.class);
                Log.d("test","组合控件被点击了");
                if(settingStopBlack.isChecked()){
                    //settingStopBlack.setComment("拦截黑名单已经打开");
                    settingStopBlack.setChecked(false);
                    //开启服务
                    startService(intent);
                }else{
                   // settingStopBlack.setComment("拦截黑名单已经关闭");
                    settingStopBlack.setChecked(true);
                    //关闭服务
                    stopService(intent);
                }
            }
        });


    }

    //当设置活动界面重新获得焦点打开来电归属地显示的单选框也要根据服务是否在运行判断
    @Override
    protected void onResume() {
        super.onResume();
        boolean  isServive  = ServiceUtil.isRunning(this, "com.example.service.ComingPhoneBelong");
        if(isServive){
            settingComingPhone.setChecked(true);
        }else{
            settingComingPhone.setChecked(false);
        }

        //设置拦截黑名单功能是是否开启
        boolean  isRunning = ServiceUtil.isRunning(this, "com.example.service.StopBlackNum");
        if(isRunning){
            settingStopBlack.setChecked(true);
        }else{
            settingStopBlack.setChecked(false);
        }

        //锁定应用程序
        boolean  isRunning2  = ServiceUtil.isRunning(this, "com.example.service.LockAppService");
        if(isRunning2){
            settingLockApp.setChecked(true);
        }else{
            settingLockApp.setChecked(false);
        }
    }
}
