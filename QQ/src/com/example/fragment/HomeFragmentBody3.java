package com.example.fragment;

import com.example.qq.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 管理主界面的第三个页卡
 * @author yzas
 *
 */
public class HomeFragmentBody3 extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_body3, container,false);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
