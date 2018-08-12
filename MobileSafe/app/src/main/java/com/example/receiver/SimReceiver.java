package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.mobilesafe.mobilesafe.R;

/**
 * 监听手机开机状态，如果手机的sim卡发生变更，发送当前sim卡信息到安全号码
 */
public class SimReceiver extends BroadcastReceiver {
    public SimReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //得到手机管理者，为后来的和获取sim卡号
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //得到手机sim卡序列号
        String  simNum = telephonyManager.getSimSerialNumber();
        //得到之前设置过的sim卡序列号
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.applicationSaveFile),Context.MODE_PRIVATE);
        String safePhone = sp.getString("safePhone", null);
        String  safeSimNum = sp.getString("simNum",null);
        //判断
        if(simNum.equals(safeSimNum) || TextUtils.isEmpty(safePhone)){
            //do nothing
        }else{
            SmsManager.getDefault().sendTextMessage(safePhone, null,"sim卡发生变化，当前sim卡序列号："+simNum, null, null);
        }
    }
}
