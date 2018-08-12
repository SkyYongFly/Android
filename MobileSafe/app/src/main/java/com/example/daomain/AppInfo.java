package com.example.daomain;

import android.graphics.drawable.Drawable;

/**
 * Created by yzas on 2015/10/8.
 */
public class AppInfo {
    private String name;   //名称
    private Drawable icon; //图标
    private String packageName;  //包名
    private boolean  userApp;  //是否是用户应用
    private boolean  inRom;  //是否是安装在手机内存
    public boolean isLocked = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    public boolean isInRom() {
        return inRom;
    }

    public void setInRom(boolean inRom) {
        this.inRom = inRom;
    }

    public AppInfo(String name, Drawable icon, String packageName, boolean userApp, boolean inRom) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.userApp = userApp;
        this.inRom = inRom;
    }

    public AppInfo() {
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", packageName='" + packageName + '\'' +
                ", userApp=" + userApp +
                ", inRom=" + inRom +
                '}';
    }
}
