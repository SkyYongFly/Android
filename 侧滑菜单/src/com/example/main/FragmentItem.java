package com.example.main;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 显示点击抽屉具体显示的内容
 * @author yzas
 *
 */
public class FragmentItem  extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		//获取填充布局
		View view  = inflater.inflate(R.layout.fragment_layout,container,false);
		//获取显示控件
		TextView tv_show = (TextView) view.findViewById(R.id.tv_show);
		//获取传递过来的值
		String content = getArguments().getString("name");
		tv_show.setText(content);
		return view;
	}
}
