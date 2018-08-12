package com.example.util;

import com.example.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PoolAdapter extends BaseAdapter {
	//GridView显示的项目总数
	private int number;
	private Context context;
	
	
	public PoolAdapter(int number,Context context) {
		this.number = number;
		this.context = context;
	}

	@Override
	public int getCount() {
		return number;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv_gridView_item = new TextView(context);
		int result = position+1;
		tv_gridView_item.setText(result+"");
		tv_gridView_item.setTextSize(15);
		tv_gridView_item.setGravity(Gravity.CENTER);
		tv_gridView_item.setBackgroundResource(R.drawable.id_defalut_ball);
		return tv_gridView_item;
	}

}
