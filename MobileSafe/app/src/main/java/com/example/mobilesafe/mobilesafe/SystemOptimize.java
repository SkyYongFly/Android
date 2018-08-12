package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.utils.SmsUtils;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 系统优化
 */
public class SystemOptimize extends Activity {
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_optimize);
    }

    /**
     * 查询号码归属地
     * @param v
     */
    public void searchPhoneBelong(View v){
        Intent intent = new Intent(this,SearchPhoneBelong.class);
        startActivity(intent);
    }

    /**
     * 备份短信到本地
     * @param v
     */
    public void saveSms(View v){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在备份短信");
        //开启子线程去备份短信
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //利用接口和回调函数完成进度条进度的设置，且UI界面增加控件和逻辑不用修改UtilS中的方法
                    SmsUtils.saveSms(SystemOptimize.this, new SmsUtils.BackUpCallBack() {
                        @Override
                        public void beforeBackup(int max) {
                            progressDialog.setMax(max);
                        }

                        @Override
                        public void onBackup(int progress) {
                            progressDialog.setProgress(progress);
                        }
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SystemOptimize.this, "备份短信成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SystemOptimize.this, "备份短信失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }finally {
                    progressDialog.dismiss();
                }
            }
        }).start();

        progressDialog.show();


    }

    /**
     * 手机短信一键还原
     */
    public void getbackSms(View v){
        try {
            SmsUtils.getbackSms(this);
            Toast.makeText(SystemOptimize.this,"短信还原成功",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            if("1".equals(e.toString())){
                Toast.makeText(SystemOptimize.this,"没有短信备份历史，无法还原",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SystemOptimize.this,"短信还原失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
