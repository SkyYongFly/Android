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
		Log.d(TAG, "��Ҫ�ͻ��������");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "����Fragment����������");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "����Fragment����������ͼ");
		// ��ȡ�����ļ�����������ͼ
		View view = inflater.inflate(R.layout.fragment_test, container, false);
		return view;

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "����Fragment����ʼ������");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "����Fragment��ʧȥ������");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "����Fragment����ý�����");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "����Fragment���û�����������");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "����Fragment����ͼ��������");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "����Fragment����������");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "����Fragment���ͻ���������");
	}

}
