package com.example.fragment;

import java.util.List;

import com.example.daomain.Friend;
import com.example.qq.ChatActivity;
import com.example.qq.R;
import com.example.service.SaveFriends;
import com.example.util.FriendUtil;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 管理主界面的第二个页卡
 * @author yzas
 *
 */
public class HomeFragmentBody2 extends Fragment {
	private ListView lv_showFriends;
	private List<Friend> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = FriendUtil.getFriends(getActivity());
 	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_body2, container,false);
		lv_showFriends = (ListView) view.findViewById(R.id.lv_home2_show_friends);
		lv_showFriends.setAdapter(new MyAdapter());
		lv_showFriends.setOnItemClickListener(new MyListener());
		return view;
	}


	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list == null?0:list.size();
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
			View view = null;
			ViewHolder holder ;
			if(convertView==null){
				view =getActivity().getLayoutInflater().inflate( R.layout.show_friend_item, null);
				holder = new ViewHolder();
				holder.iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
				holder.tv_nickName = (TextView) view.findViewById(R.id.tv_showNickname);
				holder.tv_sign = (TextView) view.findViewById(R.id.tv_showSign);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			holder.tv_nickName.setText(list.get(position).getNickName());
			holder.tv_sign.setText(list.get(position).getSign());
			
			return view;
		}
		
	}
	
	private static class  ViewHolder {
		ImageView iv_photo;
		TextView tv_nickName;
		TextView tv_sign;
	}
	
	
	
	private class MyListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(),ChatActivity.class);
			intent.putExtra("desphone",list.get(position).getPhoneNum());
			startActivity(intent);
		}
		
		
	}
}
