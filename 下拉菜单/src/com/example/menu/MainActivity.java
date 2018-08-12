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
	//���������
	private EditText et_select;
	//��������
	private PopupWindow popupWindow;
	//ListView
	private ListView lv_down;
	//���ݼ���
	private List<String>  list ;
	//������
	private MyAdapter myAdapter;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ʼ�����ݣ�����ֱ�Ӹ�����������
		list = new ArrayList<String>();
		for(int i=0;i<20;i++){
			list.add("132412312"+i);
		}
		
		et_select = (EditText) findViewById(R.id.et_phone);
		lv_down = new ListView(this);
		myAdapter = new MyAdapter();
		lv_down.setAdapter(myAdapter);
		
		//lv_down.setBackgroundResource(R.drawable.listview_background);
		//����ͨ������ListView�ļ�������ȡ�ı����ݲ��У�ԭ����
		//lv_down.setOnItemSelectedListener();
	}
	
	/**
	 * ��������˵�ѡ��ť��������
	 * @param v
	 */
	public void down(View v){
		popupWindow = new PopupWindow(getApplicationContext());
		
		//���õ���������������
		popupWindow.setContentView(lv_down);
		//���
		popupWindow.setWidth(et_select.getWidth());
		//�߶�
		popupWindow.setHeight(200);
		//�ڵ���������ⲿ�����Ļ������������
		popupWindow.setOutsideTouchable(true);
		//����λ�úͷ�ʽ���������ı������������������˵��ķ�ʽ����
		popupWindow.showAsDropDown(et_select,0,0);
		
		
		
	}
	
	/**
	 * ������
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
			
			//��Ϊ�ڲ�����������Ҫfinal����
			final int i = position;
			//��� x�Ű�ťɾ����Ӧ�����ݣ�ͬʱҪ����ͼˢ��
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					list.remove(i);
					myAdapter.notifyDataSetInvalidated();
					
				}
			});
			
			//��ѡ������ʱ���ȡ�ı���ʾ
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
