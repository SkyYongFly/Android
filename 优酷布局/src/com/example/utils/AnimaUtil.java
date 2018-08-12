package com.example.utils;

import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * ����
 * @author yzas
 *
 */
public class AnimaUtil {
	
	
	/**
	 * ����Ҫ�ӳٵĶ�����ͬʱ������ô���ֱ�ӵ������ӳٲ����ķ���
	 * @param view
	 */
	public static void startAnimOut(RelativeLayout view){
		startAnimOut(view, 0);
	}
	
	/**
	 * ������ת��ȥ
	 * @param view
	 * @param offset  �����ӳٵ�ʱ��
	 */
	public  static void startAnimOut(RelativeLayout view,long offset){
		RotateAnimation animation = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
		animation.setDuration(500);
		animation.setStartOffset(offset);
		animation.setFillAfter(true);//����ִ����Ϻ󱣳�״̬����
		view.startAnimation(animation);
	}
	
	
	public static void startAnimIn(RelativeLayout view){
		startAnimIn(view, 0);
	}
	/**
	 * ������ת����
	 * @param view
	 */
	public  static void  startAnimIn(RelativeLayout view,long offset){
		RotateAnimation animation = new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
		animation.setDuration(500);
		animation.setStartOffset(offset);
		animation.setFillAfter(true);
		view.startAnimation(animation);
	}

}
