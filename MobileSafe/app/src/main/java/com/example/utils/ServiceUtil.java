package com.example.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by yzas on 2015/10/5.
 */
public class ServiceUtil {
    public static  boolean isRunning(Context context,String serviceName){
        //获取系统活动服务管理
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(100);//获取运行的服务，最多100个

        for(ActivityManager.RunningServiceInfo serviceInfo:list){
            if(serviceInfo.service.getClassName().equals(serviceName)){
                return  true;
            }
        }
        return false;
    }
}
