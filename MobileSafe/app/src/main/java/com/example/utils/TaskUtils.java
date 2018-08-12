package com.example.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by yzas on 2015/10/9.
 */
public class TaskUtils{

    /**
     * 获取当前运行的进程数
     */
    public  static int  getRunningTasks(Context context ){
        //获取进程管理者
        ActivityManager  activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取正在运行的进程集合
        List<ActivityManager.RunningAppProcessInfo>   listRunning = activityManager.getRunningAppProcesses();
        return  listRunning.size();

    }

    /**
     * 获取当前手机可用的内存
     */
    public   static  long getTaskAvailMemory(Context context){
        ActivityManager  activityManager  = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outinfo  = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outinfo);
        return outinfo.availMem;
    }

    /**
     * 获取当前手机可用的内存
     */
    public   static  long getTaskAllMemory(Context context){
        //安卓4.0后能够使用的方法
//        ActivityManager  activityManager  = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        ActivityManager.MemoryInfo outinfo  = new ActivityManager.MemoryInfo();
//        activityManager.getMemoryInfo(outinfo);
//        return outinfo.totalMem;

        //兼容低版本
        try {
            //读取系统中记录内存信息的文件，将文件中的数字信息提取出来
            File file = new File("/proc/meminfo");
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String str = reader.readLine();
            StringBuffer buffer = new StringBuffer();
            for(char c:str.toCharArray()){
                if(c>='0' && c<='9'){
                    buffer.append(c);
                }
            }

            return  Long.parseLong(buffer.toString())*1024;

        }catch (Exception e){
            e.printStackTrace();
            return  0;
        }
    }





}
