package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * 手机追踪防盗界面
 */
public class FindLocationActivity extends Activity {
    private ImageView iv_lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_location);

        iv_lock = (ImageView) findViewById(R.id.iv_lock);

        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.applicationSaveFile),MODE_PRIVATE);
        String  setSafe = sp.getString("setSafe",null);
        if("true".equals(setSafe)){
            iv_lock.setImageResource(R.drawable.lock);
        }else{
            iv_lock.setImageResource(R.drawable.unlock);
        }
    }


    //按下返回键应该回到软件主页而不是回到设置界面
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * 重新进入设置向导
     *
     * @param v
     */
    public void reToSetting(View v) {
        Intent intent = new Intent(this, FindSetting1.class);
        startActivity(intent);
    }

}
