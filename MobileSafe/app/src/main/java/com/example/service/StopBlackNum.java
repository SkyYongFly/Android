package com.example.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.db.BlacknumSQLDao;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

public class StopBlackNum extends Service {

    private BlacknumSQLDao  dao ;
    private SmsReceiver smsReceiver ;
    private TelephonyManager telephonyManager;
    private PhoneListener listener ;
    public StopBlackNum() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dao = new BlacknumSQLDao(this);

        //动态注册短信广播接收者
        smsReceiver = new SmsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");//设置监听的内容为短信
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);//设置权限为最高
        registerReceiver(smsReceiver, filter);

        //电话监听器
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new PhoneListener();
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);//监听来电状态

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //取消短信拦截
        unregisterReceiver(smsReceiver);
        smsReceiver =null;

        //取消监听
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);//设置监听内容为空
        listener = null;
    }

    //监听短信广播
    private class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取发送过来的短信
            Object[] obj = (Object[]) intent.getExtras().get("pdus");

            for (Object j : obj) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) j);//注意此处写法
                //发送人
                String sender = message.getOriginatingAddress();
                String mode = dao.findMode(sender);
                if ("1".equals(mode)  ||  "3".equals(mode)){//拦截短信或者全部
                    abortBroadcast();
                }else{//拦截电话或者为空

                }
            }
        }
    }

    private class PhoneListener extends PhoneStateListener {
        //当通话状态改变的时候调用
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING: {//当响铃的时候
                    String mode = dao.findMode(incomingNumber);
                    if("2".equals(mode) || "3".equals(mode)){
                        //删除黑名单电话记录
                        Uri  uri = Uri.parse("content://call_log/calls");
                        getContentResolver().registerContentObserver(uri,true,new CallLogObsever(incomingNumber,new Handler()));
                        //拦截电话
                        endCall();
                        Log.d("test","拦截电话");
                    }else{

                    }
                }
                default:
                    break;
            }

        }
    }

    private class  CallLogObsever extends ContentObserver {

        private String incomingNumber;
        /**
         * Creates a content observer.
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public CallLogObsever(String incomingNumber,Handler handler) {
            super(handler);
            this.incomingNumber = incomingNumber;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("test", "通话记录数据库发生变化");
            //删除通话记录
            deleteCallLog(incomingNumber);
            //取消注册
            getContentResolver().unregisterContentObserver(this);
        }
    }

    /**
     * 删除通话记录
     * @param incomingNumber
     */
    private void deleteCallLog(String incomingNumber) {
        Uri uri = Uri.parse("content://call_log//calls");
        ContentResolver resolver  = getContentResolver();
        resolver.delete(uri,"number=?",new String[]{incomingNumber});
    }

    /**
     * 中断电话
     */
    private  void endCall() {

        try {
            //加载ServiceManager的字节码
            Class  clazz = StopBlackNum.class.getClassLoader().loadClass("android.os.ServiceManager");
            Method method = clazz.getDeclaredMethod("getService", String.class);
            IBinder  binder  = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
            ITelephony.Stub.asInterface(binder).endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}