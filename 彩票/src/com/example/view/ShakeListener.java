package com.example.view;

import java.util.Random;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
/**
 * �ֻ�ҡ�εĴ���ҡ���ֻ���ѡ������
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
		//��һ�β�õĵ�
		if(lastTime == 0){
			lastX = event.values[SensorManager.DATA_X];
			lastY = event.values[SensorManager.DATA_Y];
			lastZ = event.values[SensorManager.DATA_Z];
			
			lastTime = System.currentTimeMillis();
		}else {
			long currentTime = System.currentTimeMillis();
			//����������ݲɼ���ʱ���������趨��ʱ��������ȡ������
			if(currentTime-lastTime>=duration){
				float  x = event.values[SensorManager.DATA_X];
				float  y = event.values[SensorManager.DATA_Y];
				float  z = event.values[SensorManager.DATA_Z];
				
				//ȡ������ֵ֮��Ĳ�ֵ
				float dx = Math.abs(x-lastX);
				float dy = Math.abs(y-lastY);
				float dz = Math.abs(z-lastZ);
				
				if(dx<1)dx= 0 ;
				if(dy<1)dy= 0 ;
				if(dz<1)dz= 0 ;
				
				float sum = dx+dy+dz;
				total+=sum;
				
				if(sum>=switchValue){
					//�Զ���ѡ
					randomSelect();
					//������
					vibrator.vibrate(100);
					init();
					
					
				}else{//��ʼѡȡ��һ������
					lastX = event.values[SensorManager.DATA_X];
					lastY = event.values[SensorManager.DATA_Y];
					lastZ = event.values[SensorManager.DATA_Z];
					
					lastTime = System.currentTimeMillis();
				}
				
				
			}
		}
	}

	/**
	 * �Զ���ѡ
	 */
	public abstract void randomSelect() ;
		

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
	
	/**
	 * ��ʼ��
	 */
	private void init(){
		lastX = 0;
		lastY = 0;
		lastZ = 0;
		
		lastTime = 0;
	}

}
