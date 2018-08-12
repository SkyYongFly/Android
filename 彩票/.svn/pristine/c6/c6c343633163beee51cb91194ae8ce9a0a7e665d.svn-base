package com.example.view;

import java.util.LinkedList;

import android.content.Context;

/**
 * 管理视图的显示状态（在不同的操作下显示不同的样式）
 * 
 * @author yzas
 *
 */
public class ViewControl {
	private Context context;
	
	private MiddleViewManager middleViewManager;

//	private TitleManager titleManager;
//
//	private BottomManager bottomManager;

	public ViewControl(Context context) {
		this.context = context;
		
		initView();
	}

	/**
	 * 初始化索引布局文件
	 */
	private void initView() {
//		titleManager = new TitleManager(context);
//		bottomManager = new BottomManager(context);
		
		middleViewManager = new MiddleViewManager(context);
		//为中间布局的管理者添加顶部和底部两个观察者
//		middleViewManager.addObserver(titleManager);
//		middleViewManager.addObserver(bottomManager);
	
	}

	/**
	 * 显示开始界面的布局
	 */
	public void showStart() {
		//titleManager.titleNoLogin();
		
		middleViewManager.changeUI(HallView.class);
//		middleViewManager.changeUI(SecondView.class);
		
		//bottomManager.bottomNormal();
	}
	
	
	public void changeUI(Class<? extends BaseView>  clazz){
			middleViewManager.changeUI(clazz);
	}

	/**
	 * 获取历史操作记录
	 * @return
	 */
	public LinkedList<Class<? extends BaseView> >  getHistoryList(){
		return middleViewManager.getHistoryList();
	}

	public void clearHistoryList() {
		middleViewManager.clearHistoryList();
	}

	public void removeFirst() {
		middleViewManager.removeFirst();
	}

	public void backUI() {
		middleViewManager.backUI();
		
	}


}
