package com.example.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.utils.TaskUtils;

import java.util.List;

/**
 * Created by yzas on 2015/10/9.
 */
public class KillAllTask  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo>  list = activityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info :list){
            activityManager.killBackgroundProcesses(info.processName);
        }
    }
}
