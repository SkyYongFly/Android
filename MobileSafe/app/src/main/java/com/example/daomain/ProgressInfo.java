package com.example.daomain;

import android.graphics.drawable.Drawable;

/**
 * Created by yzas on 2015/10/9.
 */
public class ProgressInfo {
    private String name;
    private Drawable icon;
    private String packageName;
    private boolean userProgress;
    private long  size;
    public boolean  isChecked = false;

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

    public boolean isUserProgress() {
        return userProgress;
    }

    public void setUserProgress(boolean userProgress) {
        this.userProgress = userProgress;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public ProgressInfo(String name, Drawable icon, String packageName, boolean userProgress, long size) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.userProgress = userProgress;
        this.size = size;
    }

    public ProgressInfo() {
    }
}
