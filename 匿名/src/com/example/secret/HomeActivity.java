package com.example.secret;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.secret.daomain.Config;
import com.example.secret.daomain.Message;
import com.example.secret.net.MessageRequest;
import com.example.secret.net.MessageRequest.FailtureRequestMessage;
import com.example.secret.net.MessageRequest.SuccessRequestMessage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 应用主界面，显示消消息列表
 * @author yzas
 *
 */
public class HomeActivity extends Activity {
	protected static final String TAG = "test";
	private JSONArray jsArray;
	private List<Message> listMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		listMessage = new ArrayList<Message>();
		//请求服务器获取消息
		MessageRequest.getMessages(Config.getCachePhone(this),1,new SuccessRequestMessage() {
			
			@Override
			public void onSuccess(String reString) {
				Log.d(TAG,reString);
				try {
					jsArray = new JSONArray(reString);
					for(int i=0;i<jsArray.length();i++){
						JSONObject object = jsArray.getJSONObject(i);
						listMessage.add(new Message(object.getString("id"),object.getString("phone"),object.getString("body")));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},new FailtureRequestMessage() {
			
			@Override
			public void onFail() {
				
			}
		});
		
		ListView lv_message = (ListView) findViewById(R.id.lv_message);
		lv_message.setAdapter(new MyAdapter());
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return listMessage.size();
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
			 
			if(convertView == null){
				view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.message_item,null);
				holder = new ViewHolder();
				holder.tv_message_item = (TextView) view.findViewById(R.id.tv_message_item);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			holder.tv_message_item.setText(listMessage.get(position).getMsg_body());
			return view;		}
	}
	
	private class ViewHolder{
		TextView  tv_message_item;
	}
}
