package com.example.utils;

import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * 动画
 * @author yzas
 *
 */
public class AnimaUtil {
	
	
	/**
	 * 不需要延迟的动画，同时这里服用代码直接调用有延迟参数的方法
	 * @param view
	 */
	public static void startAnimOut(RelativeLayout view){
		startAnimOut(view, 0);
	}
	
	/**
	 * 布局旋转出去
	 * @param view
	 * @param offset  动画延迟的时间
	 */
	public  static void startAnimOut(RelativeLayout view,long offset){
		RotateAnimation animation = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
		animation.setDuration(500);
		animation.setStartOffset(offset);
		animation.setFillAfter(true);//动画执行完毕后保持状态不变
		view.startAnimation(animation);
	}
	
	
	public static void startAnimIn(RelativeLayout view){
		startAnimIn(view, 0);
	}
	/**
	 * 布局旋转进来
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
