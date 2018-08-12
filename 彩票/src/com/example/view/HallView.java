package com.example.view;

import java.util.Currency;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.example.R;
import com.example.daomain.Config;
import com.example.engine.BaseEngine;
import com.example.engine.BeanFactory;
import com.example.engine.CurrentLotteryInfo;
import com.example.util.Message;
import com.example.util.XmlElement;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 购彩大厅的布局(中间显示部分，应用一加载的时候显示的内容)
 * @author yzas
 *
 */
public class HallView extends BaseView {
	private Context context ;
	private View view;
	private View hallView;
	private MyMiddleItemView mmv_ssq;
	private MyMiddleItemView mmv_3d;
	private MyMiddleItemView mmv_7lc;
	
	private ViewControl viewControl;
	
	public HallView(Context context) {
		this.context = context;
		view = initView();
		getCurrentLotteryInfo();
		
		viewControl = new ViewControl(context);
	}
	
	public  View initView(){
		hallView = View.inflate(context, R.layout.hall_layout,null);
		
		mmv_ssq = (MyMiddleItemView) hallView.findViewById(R.id.miv_ssq);
		mmv_3d = (MyMiddleItemView) hallView.findViewById(R.id.miv_3d);
		mmv_7lc = (MyMiddleItemView) hallView.findViewById(R.id.miv_7lc);
		
		betSSQ();
		
		return hallView;
	}
	
	/**
	 * 双色球立即投注
	 */
	private void betSSQ(){
		ImageView betting = mmv_ssq.getSettingImageView();
		betting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context,"进入购彩大厅",Toast.LENGTH_SHORT).show();
				viewControl.changeUI(BettingView.class);
				
			}
		});
	}
	

	/**
	 * 3D立即投注
	 */
	private void bet3D(){
		ImageView betting = mmv_3d.getSettingImageView();
		betting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	

	/**
	 * 7乐彩立即投注
	 */
	private void bet7lc(){
		ImageView betting = mmv_7lc.getSettingImageView();
		betting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	
	
	
	@Override
	public View getView() {
		return view;
	}
	
	/**
	 * 获取彩种信息
	 */
	public   void getCurrentLotteryInfo(){
		//new MyAsyncTask().execute(Config.VIEW_SSQ);
	}
	
	private class MyAsyncTask extends AsyncTask<Integer,Void,Message>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected Message doInBackground(Integer... params) {
			CurrentLotteryInfo engine = BeanFactory.getClassImpl(CurrentLotteryInfo.class);
			
			return engine.getLottryInfoAtId(params[0]);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Message result) {
			XmlElement element = result.getBody().getElements().get(0);
			showMessage(element);
			super.onPostExecute(result);
		}
	}
	
	
	private void showMessage(XmlElement element) {
		String issue = element.getIssuesTag().getTagValue();
		String lastTime = element.getLastTimeTag().getTagValue();
		lastTime = getLasttime(lastTime);
		mmv_ssq.setMessage("第"+issue+"期还有 "+lastTime+"停售");
	}
	
	
	/**
	 * 将秒时间转换成日时分格式
	 * 
	 * @param lasttime
	 * @return
	 */
	public String getLasttime(String lasttime) {
		StringBuffer result = new StringBuffer();
		if (StringUtils.isNumericSpace(lasttime)) {
			int time = Integer.parseInt(lasttime);
			int day = time / (24 * 60 * 60);
			result.append(day).append("天");
			if (day > 0) {
				time = time - day * 24 * 60 * 60;
			}
			int hour = time / 3600;
			result.append(hour).append("时");
			if (hour > 0) {
				time = time - hour * 60 * 60;
			}
			int minute = time / 60;
			result.append(minute).append("分");
		}
		return result.toString();
	}

}
