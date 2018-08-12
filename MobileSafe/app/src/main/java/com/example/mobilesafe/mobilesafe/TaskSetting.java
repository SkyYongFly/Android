package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.service.AutoCleanService;
import com.example.utils.ServiceUtil;

public class TaskSetting extends Activity {
    private CheckBox cb_showSystemTask;
    private CheckBox cb_cleanTasksScreenOff;
    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_setting);

        sp = getSharedPreferences(getResources().getString(R.string.applicationSaveFile),MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        cb_showSystemTask = (CheckBox) findViewById(R.id.cb_showSystemTask);
        cb_cleanTasksScreenOff = (CheckBox) findViewById(R.id.cb_cleanTasksScreenOff);

        boolean  showSysTask = sp.getBoolean("showSystemTasks",false);
        if(showSysTask){
            cb_showSystemTask.setChecked(true);
        }else{
            cb_showSystemTask.setChecked(false);
        }

//        boolean clean = sp.getBoolean("cleanTasksScreenOff",false);
//        if(clean){
//            cb_cleanTasksScreenOff.setChecked(true);
//        }else{
//            cb_cleanTasksScreenOff.setChecked(false);
//        }



        cb_showSystemTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("showSystemTasks",true);
                }else{
                    editor.putBoolean("showSystemTasks",false);
                }
                editor.commit();
            }
        });

        cb_cleanTasksScreenOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    editor.putBoolean("cleanTasksScreenOff",true);
//                }else{
//                    editor.putBoolean("cleanTasksScreenOff",false);
//                }
//                editor.commit();

                Intent intent = new Intent(TaskSetting.this,AutoCleanService.class);
                if(isChecked){
                    startService(intent);
                }else{
                    stopService(intent);
                }

            }
        });


    }

    /**
     * 在onstart()方法中更新选择状态，因为锁屏的选择显示状态是根据服务是否正在运行的，
     * 而且每次返回该页面不一定新建页面但一id那个执行 START 方法
     */
    @Override
    protected void onStart() {
        //判断服务是否正在运行，checkbox选中
        if(ServiceUtil.isRunning(getApplicationContext(),"com.example.service.AutoCleanService")){
            cb_cleanTasksScreenOff.setChecked(true);
        }else{
            cb_cleanTasksScreenOff.setChecked(false);
        }
        super.onStart();
    }
}
