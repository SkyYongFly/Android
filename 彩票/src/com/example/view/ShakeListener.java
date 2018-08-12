package com.example.view;

import java.util.Random;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
/**
 * 手机摇晃的处理，摇晃手机机选红蓝球
 * @author yzas
 *
 */
public abstract class ShakeListener implements SensorEventListener {
	private Context context;
	private float lastX;
	private float lastY;
	private float lastZ;
	private long  lastTime;
	private float duration = 100;
	private float total;
	private float switchValue = 200;
	private Vibrator vibrator;
	
	public ShakeListener(Context context) {
		this.context = context;
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//第一次测得的点
		if(lastTime == 0){
			lastX = event.values[SensorManager.DATA_X];
			lastY = event.values[SensorManager.DATA_Y];
			lastZ = event.values[SensorManager.DATA_Z];
			
			lastTime = System.currentTimeMillis();
		}else {
			long currentTime = System.currentTimeMillis();
			//如果两次数据采集的时间间隔大于设定的时间间隔，则取样数据
			if(currentTime-lastTime>=duration){
				float  x = event.values[SensorManager.DATA_X];
				float  y = event.values[SensorManager.DATA_Y];
				float  z = event.values[SensorManager.DATA_Z];
				
				//取两次数值之间的差值
				float dx = Math.abs(x-lastX);
				float dy = Math.abs(y-lastY);
				float dz = Math.abs(z-lastZ);
				
				if(dx<1)dx= 0 ;
				if(dy<1)dy= 0 ;
				if(dz<1)dz= 0 ;
				
				float sum = dx+dy+dz;
				total+=sum;
				
				if(sum>=switchValue){
					//自动机选
					randomSelect();
					//响铃震动
					vibrator.vibrate(100);
					init();
					
					
				}else{//开始选取下一轮数据
					lastX = event.values[SensorManager.DATA_X];
					lastY = event.values[SensorManager.DATA_Y];
					lastZ = event.values[SensorManager.DATA_Z];
					
					lastTime = System.currentTimeMillis();
				}
				
				
			}
		}
	}

	/**
	 * 自动机选
	 */
	public abstract void randomSelect() ;
		

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
	
	/**
	 * 初始化
	 */
	private void init(){
		lastX = 0;
		lastY = 0;
		lastZ = 0;
		
		lastTime = 0;
	}

}
