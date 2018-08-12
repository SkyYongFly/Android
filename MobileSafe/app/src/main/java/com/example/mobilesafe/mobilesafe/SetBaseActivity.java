package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public  abstract  class SetBaseActivity extends Activity {

    protected SharedPreferences  sp  ;
    //定义一个手势识别器
    private GestureDetector dector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_base);

        sp  = getSharedPreferences(getResources().getString(R.string.applicationSaveFile),MODE_PRIVATE);

        //初始化手势识别器
        dector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
           //当我们的手指在屏幕上滑动调用此方法
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if((e1.getRawX()-e2.getRawX())>200){//从右到左滑动
                        showNext();
                }

                if((e2.getRawX()-e1.getRawX())>200){//从左到右滑动
                    showPre();
                }

                return super.onFling( e1, e2, velocityX, velocityY);
            }
        });

    }

    public abstract  void showNext();
    public abstract  void showPre();

    /**
     * 子类的按钮触发事件调用，显示上一个界面
     * @param v
     */
    public void pre(View v){
        showPre();
    }


    /**
     * 子类的按钮触发事件调用，显示下一个界面
     * @param v
     */
    public void next(View v){
        showNext();
    }

    //使用手势识别器
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
