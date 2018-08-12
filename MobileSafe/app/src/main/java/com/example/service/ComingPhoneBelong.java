package com.example.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.db.SearchPhoneBelongDB;
import com.example.mobilesafe.mobilesafe.R;

/**
 * 来电显示归属地功能
 */
public class ComingPhoneBelong extends Service {
    private TelephonyManager tm;
    private MyListener listener;
    private OutCallReceiver receiver;
    private WindowManager wm;
    private View view;
    private SharedPreferences sp;
    private String TAG = "test";

    public ComingPhoneBelong() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        sp = getApplication().getSharedPreferences("isUpdateFile", MODE_PRIVATE);

        //监听来电
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);//监听来电状态


        //动态注册监听拨打电话的广播
        receiver = new OutCallReceiver();
        //增加过滤内容，即监听的内容
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消监听
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);//设置监听内容为空
        listener = null;
        //取消注册广播
        unregisterReceiver(receiver);
        receiver = null;

    }

    private class MyListener extends PhoneStateListener {
        //当通话状态改变的时候调用
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING: {//当响铃的时候
                    String address = SearchPhoneBelongDB.search(ComingPhoneBelong.this, incomingNumber);
                    //Toast.makeText(ComingPhoneBelong.this, address, Toast.LENGTH_SHORT).show();
                    Log.d("test", address);

                    myToast(address);
                }
                break;

                case TelephonyManager.CALL_STATE_IDLE: {//挂断电话
                    if (view != null) {
                        wm.removeView(view);
                        view = null;
                    }
                }
                break;
                default:
                    break;
            }

        }
    }

    /**
     * 定义一个内部的广播，用于监听拨打电话，当拨打电话显示号码归属地
     * 定义在服务的内部主要是使其生命周期和服务的生命周期一样，如果单独新建广播，静态注册
     * 当用户将显示号码归属地的功能关闭后，静态注册的广播依然工作，这是不合理的，所以定义一个内部
     * 的广播，并动态注册
     */
    class OutCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取拨打出去的号码
            String phone = getResultData();
            String address = SearchPhoneBelongDB.search(context, phone);
            // Toast.makeText(context, address, Toast.LENGTH_SHORT).show();
            Log.d("test", address);

            myToast(address);
        }
    }


    WindowManager.LayoutParams params;

    /**
     * 自定义土司
     */
    public void myToast(String address) {
        params = new WindowManager.LayoutParams();

        view = View.inflate(this, R.layout.toastlayout, null);
        TextView tv_address = (TextView) view.findViewById(R.id.address);


        //给view添加触摸事件，用来移动自定义的土司
        view.setOnTouchListener(new View.OnTouchListener() {

            int startX ;//开始的位置 X坐标
            int startY ;//Y坐标
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://手指按下
                        Log.d(TAG, "手指按下了");
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        Log.d(TAG, "开始位置X：" + startX);
                        Log.d(TAG, "开始位置Y：" + startY);

                        break;
                    case MotionEvent.ACTION_MOVE://手指在屏幕上移动
                        Log.d(TAG, "手指移动了");
                        int newX = (int) event.getRawX();
                        int newY = (int) event.getRawY();

                        int moveX = newX - startX;//在X方向上移动的距离
                        int moveY = newY - startY;//在Y方向上移动的距离

                        params.x += moveX;
                        params.y += moveY;
                        //判断移出屏幕情况
//                        if(params.x<0){
//                            params.x=0;
//                        }
//                        if(params.x> (wm.getDefaultDisplay().getWidth()-view.getWidth())){
//                            params.x = wm.getDefaultDisplay().getWidth()-view.getWidth();
//                        }
//                        if(params.y<0){
//                            params.y = 0;
//                        }
//                        if(params.y>(wm.getDefaultDisplay().getHeight()-view.getHeight())){
//                            params.y=wm.getDefaultDisplay().getHeight()-view.getHeight();
//                        }


                        //更新土司显示位置
                        wm.updateViewLayout(view, params);
                        //初始化开始位置为新的位置
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP://手指离开屏幕
                        Log.d(TAG, "手指离开了");
                        //将位置信息保存起来
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("startX", params.x);
                        editor.putInt("startY", params.y);
                        editor.commit();

                        break;

                }

                return false;//返回false，即触摸过后不要让父布局相应触摸事件
            }

        });

        final  long[] mHits = new long[2];
        //双击居中
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                    // 双击居中了。。。
                    params.x = wm.getDefaultDisplay().getWidth()/2-view.getWidth()/2;
                    wm.updateViewLayout(view, params);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("lastx", params.x);
                    editor.commit();
                }
            }
        });

        //"半透明","活力橙","卫士蓝","金属灰","苹果绿"
        int[] items = {
                R.drawable.call_locate_white, R.drawable.call_locate_orange,
                R.drawable.call_locate_blue, R.drawable.call_locate_gray,
                R.drawable.call_locate_green,};
        view.setBackgroundResource(items[sp.getInt("background", 0)]);
        tv_address.setText(address);


        //指定显示的位置
       // params.gravity = Gravity.TOP + Gravity.LEFT;//左上角
       // params.x  =100;//距离左上角水平距离100
       // params.y = 100;
//        params.x = sp.getInt("startX", 0);//使用之前保存的位置
//        params.y = sp.getInt("startY", 0);

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //  params.type = WindowManager.LayoutParams.TYPE_TOAST;//设置为土司类型将不具有点击效果
        // android系统里面具有电话优先级的一种窗体类型，记得添加权限
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;


        //将自定义布局添加到界面
        wm.addView(view, params);


    }
}
