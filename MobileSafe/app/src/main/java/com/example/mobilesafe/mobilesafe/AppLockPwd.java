package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 被锁定的应用程序打开的时候密码锁界面
 * 需要设置该活动为单例模式
 * 在配置文件中配置
 */
public class AppLockPwd extends Activity {
    private EditText et_lockAppPwd;
    private String  lockedPassword;
    private String packageName;
    private ImageView iv_showAppIcon;
    private TextView tv_showAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock_pwd);

        packageName = getIntent().getStringExtra("packageName");

        et_lockAppPwd = (EditText) findViewById(R.id.et_lockAppPwd);
        iv_showAppIcon = (ImageView) findViewById(R.id.iv_showAppIcon);
        tv_showAppName = (TextView) findViewById(R.id.tv_showAppName);

        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);//获取对应包名应用程序信息
            iv_showAppIcon.setImageDrawable(info.loadIcon(pm));//设置图标
            tv_showAppName.setText(info.loadLabel(pm));//设置名称
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }





    }

    /**
     * 确定
     * @param v
     */
    public  void enterApp(View v){
        String password = et_lockAppPwd.getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"密码输入为空",Toast.LENGTH_SHORT).show();
        }else{
            //这里密码应该从之前设置开启密码锁的时候输入保存的密码，此处简单演示功能
            if(!"1".equals(password)){
                Toast.makeText(AppLockPwd.this, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }else{
                //发送一个广播告诉监视程序的看门狗这个应用在在锁屏之前不在监视
                Intent intent = new Intent();
                intent.setAction("com.example.noWatch");
                sendBroadcast(intent);
                finish();
            }
        }
    }

    /**
     * 取消输入
     * @param v
     */
    public void exitApp(View v){
        onBackPressed();
    }

    /**
     * 返回键回到桌面
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_MONKEY);
        startActivity(intent);
    }

    /**
     * 不可见的时候就销毁
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
