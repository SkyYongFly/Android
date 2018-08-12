package com.example.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.example.daomain.ProgressInfo;
import com.example.mobilesafe.mobilesafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 进程信息提供者
 * Created by yzas on 2015/10/9.
 */
public class TaskInfoProvider {

    public static List<ProgressInfo> getTasksInfo(Context context ){
        //获取应用管理者
        ActivityManager activityManager  = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        PackageManager  packageManager = context.getPackageManager();

        //获取正在运行的进程信息
        List<ActivityManager.RunningAppProcessInfo>  listRunningTasks = activityManager.getRunningAppProcesses();
        //存储进程对对象，作为返回对象
        List<ProgressInfo>  list = new ArrayList<>();
        for(ActivityManager.RunningAppProcessInfo  info : listRunningTasks){
            ProgressInfo  progressInfo  = new ProgressInfo();
            //获取包名
            String packageName = info.processName;
            progressInfo.setPackageName(packageName);
            //获取进程占用内存大小
            Debug.MemoryInfo[] memoryInfos =    activityManager.getProcessMemoryInfo(new int[]{info.pid});
            long memSize = memoryInfos[0].getTotalPrivateDirty()*1024L;
            progressInfo.setSize(memSize);

            try {
                //根据包名获取应用信息，进程的图标以及是否是用户进程等信息是应用相关的
                ApplicationInfo  appInfo = packageManager.getApplicationInfo(packageName, 0);
                //图标
                Drawable icon = appInfo.loadIcon(packageManager);
                progressInfo.setIcon(icon);
                //名称
                String name = appInfo.loadLabel(packageManager).toString();
                progressInfo.setName(name);
                //是否是用户进程
                if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                    //不是系统进程
                    progressInfo.setUserProgress(true);
                }else{
                    progressInfo.setUserProgress(false);
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                //异常找不到包名，我们是设置默认的名称为报名
                progressInfo.setName(packageName);
                progressInfo.setIcon(context.getResources().getDrawable(R.drawable.ic_default));
            }

            list.add(progressInfo);

        }
        return  list;
    }
}
