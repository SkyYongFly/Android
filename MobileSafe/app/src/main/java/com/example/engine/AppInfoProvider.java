package com.example.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.daomain.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机应用程序信息提供者
 * Created by yzas on 2015/10/8.
 */
public class AppInfoProvider {

    /**
     * 获取所有已经安装的应用信息
     * @return
     */
    public static List<AppInfo> getAppInfo(Context context){
        PackageManager  pm =  context.getPackageManager();
        //获取所有已经安装的应用
        List<PackageInfo>  listPackage =  pm.getInstalledPackages(0);
        List<AppInfo>  list = new ArrayList<>();
        for(PackageInfo pk:listPackage){
            AppInfo  appInfo = new AppInfo();
            //包名
            appInfo.setPackageName(pk.packageName);
            //图标
           appInfo.setIcon(pk.applicationInfo.loadIcon(pm));
            //名称
           appInfo.setName(pk.applicationInfo.loadLabel(pm).toString());

            int flags = pk.applicationInfo.flags;//应用的标记信息
            if((flags & ApplicationInfo.FLAG_SYSTEM )== 0){//是否是系统应用
                appInfo.setUserApp(true);
            }else{
                appInfo.setUserApp(false);
            }
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) ==0 ){//是否是安装在SD卡上
                appInfo.setInRom(true);
            }else{
                appInfo.setInRom(false);
            }

            list.add(appInfo);
        }
        return  list;
    }
}
