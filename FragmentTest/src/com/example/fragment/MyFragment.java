package com.example.fragment;

import com.example.fragmenttest.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment {

	private static final String TAG = "test";

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.d(TAG, "我要和活动产生关联");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "我是Fragment，被创建了");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "我是Fragment，创建了视图");
		// 获取布局文件，创建该视图
		View view = inflater.inflate(R.layout.fragment_test, container, false);
		return view;

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "我是Fragment，开始运行了");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "我是Fragment，失去焦点了");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "我是Fragment，获得焦点了");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "我是Fragment，用户看不到我了");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "我是Fragment，视图被销毁了");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "我是Fragment，被销毁了");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "我是Fragment，和活动解除关联了");
	}

}
