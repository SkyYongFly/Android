package com.example.fragment;

import java.util.List;


import com.example.daomain.DataMessage;
import com.example.db.SQLiteUtil;
import com.example.qq.R;
import com.example.qq.ResponseAddFriend;

import android.R.interpolator;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract.PinnedPositions;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 管理主界面的第一个页卡
 * 
 * @author yzas
 *
 */
public class HomeFragmentBody1 extends ListFragment {

	protected static final String TAG = "test";

	private static  List<DataMessage> listMessage ;

	private View view;

	private ArrayAdapter<DataMessage> adapter;

	private LocalBroadcastManager manager;

	private MyBroadCastReceiver receiver;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				SQLiteUtil sqLiteUtil = new SQLiteUtil(getActivity());
				listMessage = sqLiteUtil.getUnReadMessage();
				
				manager = LocalBroadcastManager.getInstance(getActivity());
				IntentFilter intentFilter = new IntentFilter();
				intentFilter.addAction("com.example.qq.mybroadcast");
				receiver = new MyBroadCastReceiver();
				manager.registerReceiver(receiver,intentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home_body1, container, false);
		
		if(adapter == null){
			adapter = new MyAdapter(getActivity(),R.layout.home_body1 );
			setListAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		manager.unregisterReceiver(receiver);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		DataMessage message = listMessage.get(position);
		if(message.getBody().equals("add friend")){
			Intent intent = new Intent(getActivity(),ResponseAddFriend.class);
			intent.putExtra("account", message.getDesphone());
			startActivity(intent);
		}
	}
	
	private class MyAdapter  extends ArrayAdapter<DataMessage>{
		public MyAdapter(Context context, int resource) {
			super(context, resource);
		}
		
		

		@Override
		public int getCount() {
			return listMessage ==null?0:listMessage.size();
		}



		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.home_body1_item, null);
			}
			DataMessage message = listMessage.get(position);
			TextView tv_showPhone = (TextView) convertView.findViewById(R.id.tv_showPhone);
			TextView tv_showMessage = (TextView) convertView.findViewById(R.id.tv_showMessage);
			if("add friend".equals(message.getBody())){
				tv_showPhone.setText("系统消息:添加好友申请");
				tv_showMessage.setText(message.getDesphone()+":请求添加为好友");
			}else{
				tv_showPhone.setText(message.getDesphone());
				tv_showMessage.setText(message.getBody());
			}
			return convertView;
		}
		
		
	}


	private class MyBroadCastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String msg =  intent.getExtras().getString("message");
			Log.d(TAG,"解析到广播信息"+msg);
			DataMessage message = new DataMessage();
			message.setBody(msg);
			//将信息加入显示集合
			listMessage.add(message);
			//通知适配器更改视图
			adapter.notifyDataSetChanged();
		}
		
	}
}
