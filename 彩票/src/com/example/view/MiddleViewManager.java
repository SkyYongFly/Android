package com.example.view;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import com.example.R;

import android.app.Activity;
import android.app.backup.BackupDataInput;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 中间布局容器的管理者
 * 
 * @author yzas
 *
 */
public class MiddleViewManager extends Observable {
	// private static MiddleViewManager middleViewManager;
	private static final String TAG = "test";

	private Context context;
	private RelativeLayout middle_layout;

	// 保存加载进来的View，这里要使其成为静态常量，否则每次新建MiddleViewManager该Map就会重新创建一次 ，从而导致其中一只没有缓存
	private static Map<String, View> viewMap;

	// 用户操作的历史记录，即加载了哪个页面，这个用于回退键的操作，否则因为只有一个activity会直接退出
	private static LinkedList<Class<? extends BaseView>> historyList;

	static {
		viewMap = new HashMap<String, View>();
		historyList = new LinkedList<Class<? extends BaseView>>();
	}

	public MiddleViewManager(Context context) {
		this.context = context;
		Activity activity = (Activity) context;
		middle_layout = (RelativeLayout) activity.findViewById(R.id.middle_layout);

		// 为中间布局的管理者添加顶部和底部两个观察者
		this.addObserver(new TitleManager(context));
		this.addObserver(new BottomManager(context));
	}

	/**
	 * 回退的界面切换
	 * 
	 * @param clazz
	 */
	public void backUI() {
		try {
			
			historyList.removeFirst();
			Class<? extends BaseView> clazz = historyList.getFirst();

			BaseView baseView;
			View view;
			String clazzName = clazz.getSimpleName();

			
			
			if (viewMap.containsKey(clazzName)) {
				view = viewMap.get(clazzName);
			} else {
				Constructor<? extends BaseView> constructor = clazz.getConstructor(Context.class);
				baseView = constructor.newInstance(context);
				view = baseView.getView();
			}

			middle_layout.removeAllViews();
			// 这里需要借助view的父布局清空视图，否则会出现退出应用再次复用历史布局的时候不现实view
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			// 将要显示的布局添加到中间布局容器中
			middle_layout.addView(view);

			// 通知观察者中间内容发生变化，顶部和底部的显示风格也需要进行变化
			setChanged();
			notifyObservers(clazzName);// 将具体显示的内容传递给内容观察者，让其判断显示对应的界面

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切换中间的布局
	 * 
	 * @param clazz
	 */
	public void changeUI(Class<? extends BaseView> clazz) {
		try {
			BaseView baseView;
			View view;
			String clazzName = clazz.getSimpleName();
			// 如果历史记录中的第一个就是要添加的视图则不进行任何操作
			if (historyList.size() > 0 && (historyList.getFirst() == clazz)) {
				return;
			}
			if (viewMap.containsKey(clazzName) && !clazzName.equals("BettingView")) {
				Log.d(TAG, "界面有历史缓存");
				Log.d(TAG, viewMap.toString());
				view = viewMap.get(clazzName);
			} else {
				Log.d(TAG, "界面没有历史缓存");
				Log.d(TAG, viewMap.toString());
				Constructor<? extends BaseView> constructor = clazz.getConstructor(Context.class);
				baseView = constructor.newInstance(context);
				view = baseView.getView();
				// 加新添加的布局保存到map中
				viewMap.put(clazzName, view);

			}

			// 将操作的记录添加到集合中的第一个
			historyList.addFirst(clazz);
			Log.d(TAG, "list大小" + historyList.size());

			middle_layout.removeAllViews();
			// 这里需要借助view的父布局清空视图，否则会出现退出应用再次复用历史布局的时候不现实view
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			middle_layout.addView(view);

			// 通知观察者中间内容发生变化，顶部和底部的显示风格也需要进行变化
			Log.d(TAG, "通知观察者发生变化:" + clazzName);
			setChanged();
			notifyObservers(clazzName);// 将具体显示的内容传递给内容观察者，让其判断显示对应的界面

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取历史操作记录
	 * 
	 * @return
	 */
	public LinkedList<Class<? extends BaseView>> getHistoryList() {
		return historyList;
	}

	public void clearHistoryList() {
		viewMap.clear();
		historyList.clear();

	}

	public void removeFirst() {
		historyList.removeFirst();
	}

}
