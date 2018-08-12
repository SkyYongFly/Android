package com.example.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {
	//号码输入框
	private EditText et_select;
	//弹出窗体
	private PopupWindow popupWindow;
	//ListView
	private ListView lv_down;
	//数据集合
	private List<String>  list ;
	//适配器
	private MyAdapter myAdapter;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化数据，这里直接给出假设数据
		list = new ArrayList<String>();
		for(int i=0;i<20;i++){
			list.add("132412312"+i);
		}
		
		et_select = (EditText) findViewById(R.id.et_phone);
		lv_down = new ListView(this);
		myAdapter = new MyAdapter();
		lv_down.setAdapter(myAdapter);
		
		//lv_down.setBackgroundResource(R.drawable.listview_background);
		//这里通过设置ListView的监听器获取文本内容不行，原因不详
		//lv_down.setOnItemSelectedListener();
	}
	
	/**
	 * 点击下拉菜单选择按钮弹出窗口
	 * @param v
	 */
	public void down(View v){
		popupWindow = new PopupWindow(getApplicationContext());
		
		//设置弹出窗体的填充内容
		popupWindow.setContentView(lv_down);
		//宽度
		popupWindow.setWidth(et_select.getWidth());
		//高度
		popupWindow.setHeight(200);
		//在弹出窗体的外部点击屏幕弹出窗体隐藏
		popupWindow.setOutsideTouchable(true);
		//弹出位置和方式，这里在文本输入框的下面以下拉菜单的方式弹出
		popupWindow.showAsDropDown(et_select,0,0);
		
		
		
	}
	
	/**
	 * 适配器
	 * @author yzas
	 *
	 */
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list.size();
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
			View view ;
			ViewHolder holder ;
			
			if(convertView != null && convertView instanceof LinearLayout){
				view = convertView;
				holder = (ViewHolder) view.getTag();
				
			}else{
				holder = new ViewHolder();
				view = View.inflate(getApplicationContext(),R.layout.down_select,null);
				holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
				holder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
				view.setTag(holder);
				
			}
			
			//因为内部类中是数据要final类型
			final int i = position;
			//点击 x号按钮删除对应的数据，同时要将视图刷新
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					list.remove(i);
					myAdapter.notifyDataSetInvalidated();
					
				}
			});
			
			//当选项点击的时候获取文本显示
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					et_select.setText(list.get(i));
					popupWindow.dismiss();
				}
			});
			
			String  phone = list.get(position);
			holder.tv_phone.setText(phone);
			return view;
		}
		
	}
	
	
	private class  ViewHolder{
		TextView tv_phone;
		ImageView iv_delete;
		
		
	}
}
