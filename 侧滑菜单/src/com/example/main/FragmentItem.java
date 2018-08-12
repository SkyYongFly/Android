package com.example.main;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ��ʾ������������ʾ������
 * @author yzas
 *
 */
public class FragmentItem  extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		//��ȡ��䲼��
		View view  = inflater.inflate(R.layout.fragment_layout,container,false);
		//��ȡ��ʾ�ؼ�
		TextView tv_show = (TextView) view.findViewById(R.id.tv_show);
		//��ȡ���ݹ�����ֵ
		String content = getArguments().getString("name");
		tv_show.setText(content);
		return view;
	}
}
