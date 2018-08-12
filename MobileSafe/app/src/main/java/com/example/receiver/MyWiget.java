package com.example.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.example.service.UpdateWidgetService;

/**
 * Created by yzas on 2015/10/9.
 */
public class MyWiget  extends AppWidgetProvider { //需要注册

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,UpdateWidgetService.class);
        context.startService(i);
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        Intent intent = new Intent(context,UpdateWidgetService.class);
        context.startService(intent);
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        Intent intent = new Intent(context,UpdateWidgetService.class);
        context.stopService(intent);
        super.onDisabled(context);
    }
}
