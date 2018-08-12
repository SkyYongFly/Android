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
 * �м䲼�������Ĺ�����
 * 
 * @author yzas
 *
 */
public class MiddleViewManager extends Observable {
	// private static MiddleViewManager middleViewManager;
	private static final String TAG = "test";

	private Context context;
	private RelativeLayout middle_layout;

	// ������ؽ�����View������Ҫʹ���Ϊ��̬����������ÿ���½�MiddleViewManager��Map�ͻ����´���һ�� ���Ӷ���������һֻû�л���
	private static Map<String, View> viewMap;

	// �û���������ʷ��¼�����������ĸ�ҳ�棬������ڻ��˼��Ĳ�����������Ϊֻ��һ��activity��ֱ���˳�
	private static LinkedList<Class<? extends BaseView>> historyList;

	static {
		viewMap = new HashMap<String, View>();
		historyList = new LinkedList<Class<? extends BaseView>>();
	}

	public MiddleViewManager(Context context) {
		this.context = context;
		Activity activity = (Activity) context;
		middle_layout = (RelativeLayout) activity.findViewById(R.id.middle_layout);

		// Ϊ�м䲼�ֵĹ�������Ӷ����͵ײ������۲���
		this.addObserver(new TitleManager(context));
		this.addObserver(new BottomManager(context));
	}

	/**
	 * ���˵Ľ����л�
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
			// ������Ҫ����view�ĸ����������ͼ�����������˳�Ӧ���ٴθ�����ʷ���ֵ�ʱ����ʵview
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			// ��Ҫ��ʾ�Ĳ�����ӵ��м䲼��������
			middle_layout.addView(view);

			// ֪ͨ�۲����м����ݷ����仯�������͵ײ�����ʾ���Ҳ��Ҫ���б仯
			setChanged();
			notifyObservers(clazzName);// ��������ʾ�����ݴ��ݸ����ݹ۲��ߣ������ж���ʾ��Ӧ�Ľ���

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �л��м�Ĳ���
	 * 
	 * @param clazz
	 */
	public void changeUI(Class<? extends BaseView> clazz) {
		try {
			BaseView baseView;
			View view;
			String clazzName = clazz.getSimpleName();
			// �����ʷ��¼�еĵ�һ������Ҫ��ӵ���ͼ�򲻽����κβ���
			if (historyList.size() > 0 && (historyList.getFirst() == clazz)) {
				return;
			}
			if (viewMap.containsKey(clazzName) && !clazzName.equals("BettingView")) {
				Log.d(TAG, "��������ʷ����");
				Log.d(TAG, viewMap.toString());
				view = viewMap.get(clazzName);
			} else {
				Log.d(TAG, "����û����ʷ����");
				Log.d(TAG, viewMap.toString());
				Constructor<? extends BaseView> constructor = clazz.getConstructor(Context.class);
				baseView = constructor.newInstance(context);
				view = baseView.getView();
				// ������ӵĲ��ֱ��浽map��
				viewMap.put(clazzName, view);

			}

			// �������ļ�¼��ӵ������еĵ�һ��
			historyList.addFirst(clazz);
			Log.d(TAG, "list��С" + historyList.size());

			middle_layout.removeAllViews();
			// ������Ҫ����view�ĸ����������ͼ�����������˳�Ӧ���ٴθ�����ʷ���ֵ�ʱ����ʵview
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			middle_layout.addView(view);

			// ֪ͨ�۲����м����ݷ����仯�������͵ײ�����ʾ���Ҳ��Ҫ���б仯
			Log.d(TAG, "֪ͨ�۲��߷����仯:" + clazzName);
			setChanged();
			notifyObservers(clazzName);// ��������ʾ�����ݴ��ݸ����ݹ۲��ߣ������ж���ʾ��Ӧ�Ľ���

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡ��ʷ������¼
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
