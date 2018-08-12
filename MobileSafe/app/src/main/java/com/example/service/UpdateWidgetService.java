package com.example.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.mobilesafe.mobilesafe.R;
import com.example.receiver.MyWiget;
import com.example.utils.TaskUtils;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateWidgetService extends Service {
    private   ScreenOnReceiver screenOnReceiver;//接收屏幕点亮的广播接受者
    private   ScreenOffReceiver screenOffReceiver;//接受屏幕熄灭的广播接受者
    private Timer timer;
    private TimerTask task;
    private AppWidgetManager appWidgetManager;

    public UpdateWidgetService() {
    }

    //接收屏幕点亮的广播接受者
    private class  ScreenOnReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            startTimer();//开启定时器，开始定时工作
        }
    }

    //接受屏幕熄灭的广播接受者
    private class ScreenOffReceiver extends  BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            stopTimer();//关闭定时器，结束工作
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        //注册广播
        registerReceiver(screenOnReceiver,new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(screenOffReceiver,new IntentFilter(Intent.ACTION_SCREEN_OFF));

        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(screenOffReceiver);
            unregisterReceiver(screenOnReceiver);
            screenOffReceiver = null;
            screenOnReceiver = null;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("test","没有注册");
        }
        stopTimer();
    }


    private void startTimer(){
        if(timer == null && task ==null){
            timer = new Timer();
            //定时器到点做的事情
            task = new TimerTask() {
                @Override
                public void run() {
                    //在服务和悬浮窗组件之间建立联系
                    ComponentName  provider = new ComponentName(UpdateWidgetService.this, MyWiget.class);
                    //获取悬浮窗组件对应的布局
                    RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
                    //设置显示正在运行的软件数量
                    views.setTextViewText(R.id.process_count,
                           "运行的软件:"+ TaskUtils.getRunningTasks(getApplicationContext())+"个");
                    //设置显示可用的内存大小
                    views.setTextViewText(R.id.process_memory,
                            "可用的内存大小："+Formatter.formatFileSize(getApplicationContext(), TaskUtils.getTaskAvailMemory(getApplicationContext())));

                    //设置清理按钮的点击事件：发送一个广播，让其他服务执行
                    Intent intent = new Intent();
                    intent.setAction("com.example.mobilesafe.killtask");
                    PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    views.setOnClickPendingIntent(R.id.btn_clear,pendingIntent);
                    //使设置生效
                    appWidgetManager.updateAppWidget(provider,views);
                }
            };

            timer.schedule(task,0,3000);//3秒执行一次

        }else{

        }



    }

    /**
     * 停止任务
     */
    private void stopTimer(){
        if(timer !=null && task!=null){
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }


    }
}
