package com.example.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.example.db.LockAppSQLDao;
import com.example.mobilesafe.mobilesafe.AppLockPwd;

import java.util.List;

/**
 * 锁定应用程序的后台服務
 * 当应用程序加载的时候，监视应用程序是否是锁定的应用，如果是就加载解锁界面
 */
public class LockAppService extends Service {
    private List<String> listlockedApp ;
    private LockAppSQLDao dao ;
    private ActivityManager activityManager;
    private boolean unlock = false;
    private UnlockReceiver unlockReceiver ;
    private ScreenOffReceiver screenOffReceiver;
    private DataChanged dataChanged ;
    private  boolean flag;

    public LockAppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        flag = true;
        //查询加锁的应用
        dao = new LockAppSQLDao(this);
        //获取所有的锁定的应用，同时为了使数据能够得到更新，应该在点击锁定/不锁定的时候发送广播通知这里更新数据
        listlockedApp = dao.findAll();

        //广播接受者
        unlockReceiver = new UnlockReceiver();
        screenOffReceiver = new ScreenOffReceiver();
        dataChanged = new DataChanged();
        registerReceiver(unlockReceiver,new IntentFilter("com.example.noWatch"));
        registerReceiver(screenOffReceiver,new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(dataChanged,new IntentFilter("com.example.dataChanged"));


        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        //开启输入密码的活动
        final Intent intent = new Intent(LockAppService.this, AppLockPwd.class);
        //在服务中开启活动，需要使活动在另一个新的堆栈里面，这样从软件管家进入应用输入密码后不会进入软件管家
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    //获取正在运行的任务，只是获取一个，即最新的一个
                    List<ActivityManager.RunningTaskInfo> listTask = activityManager.getRunningTasks(1);
                    //获取堆栈上面的第一个活动，即当前可见的正在于运行的活动
                    String packageName = listTask.get(0).topActivity.getPackageName();
                    //如果当前运行的活动对应的应用被锁住
                    if(!unlock) {
                        if (listlockedApp.contains(packageName)) {

                            intent.putExtra("packageName", packageName);
                            startActivity(intent);
                        }
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        unregisterReceiver(unlockReceiver);
        unregisterReceiver(screenOffReceiver);
        unlockReceiver = null;
        screenOffReceiver = null;
    }

    /**
     * 接收暂时不用监视的应用广播
     */
    private class UnlockReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            unlock = true;
        }
    }

    //锁屏广播接受者
    private class  ScreenOffReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            stopSelf();
        }
    }

    private class DataChanged  extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //重新获取一遍数据
            listlockedApp = dao.findAll();
        }
    }
}
