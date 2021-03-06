package com.example.view;

import java.util.Observable;
import java.util.Observer;

import com.example.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 标题样式和相关事件的管理者
 * 
 * @author yzas
 *
 */
public class TitleManager  implements OnClickListener ,Observer {

	private static final String TAG = "test";
	private Context context;
	private RelativeLayout ll_title;

	private LinearLayout ll_title_normal;
	private LinearLayout ll_title_no_login;
	private LinearLayout ll_title_has_login;
	
	private View view;

	public TitleManager(Context context) {
		this.context = context;
		view = init();
	}

	public View init() {
		// 获取标题布局
		//ll_title = (RelativeLayout) View.inflate(context, R.id.title_layout, null);
		Activity activity = (Activity) context;
		ll_title = (RelativeLayout) activity.findViewById(R.id.title_layout);

		// 获取不同状态下的标题布局：普通模、未登录状态、已经登陆状态
		ll_title_normal = (LinearLayout) ll_title.findViewById(R.id.ll_title_normal);
		ll_title_no_login = (LinearLayout) ll_title.findViewById(R.id.ll_title_no_login);
		ll_title_has_login = (LinearLayout) ll_title.findViewById(R.id.ll_title_has_login);

		return ll_title;
	}

	/**
	 * 未登陆状态
	 */
	public void titleNoLogin() {
		ll_title_no_login.setVisibility(View.VISIBLE);
		ll_title_normal.setVisibility(View.INVISIBLE);
		ll_title_has_login.setVisibility(View.INVISIBLE);
		
		// 获取未登陆状态下登陆和注册的按钮对象
		ImageView iv_title_login = (ImageView) ll_title_no_login.findViewById(R.id.iv_title_login);
		ImageView iv_title_rigist = (ImageView) ll_title_no_login.findViewById(R.id.iv_title_rigist);

		iv_title_login.setOnClickListener(this);
		iv_title_rigist.setOnClickListener(this);
	}
	
	/**
	 * 登陆状态
	 */
	public void titleHasLogin(){
		
	}
	
	/**
	 * 普通状态
	 */
	public void titleNormal(String showTip){
		ll_title_no_login.setVisibility(View.INVISIBLE);
		ll_title_normal.setVisibility(View.VISIBLE);
		ll_title_has_login.setVisibility(View.INVISIBLE);
		
		ImageView  iv_title_left = (ImageView) ll_title_normal.findViewById(R.id.iv_title_left);
		ImageView  iv_title_right = (ImageView) ll_title_normal.findViewById(R.id.iv_title_right);
		TextView tv_title_show_tip = (TextView) ll_title_normal.findViewById(R.id.tv_title_show_tip);
		
		tv_title_show_tip.setText(showTip);
		iv_title_left.setOnClickListener(this);
		iv_title_right.setOnClickListener(this);
		
	}
	

	@Override
	public void onClick(View v) {
		ViewControl viewControl = new ViewControl(context);
		
		switch (v.getId()) {
		case R.id.iv_title_login://登陆
			
			viewControl.changeUI(SecondView.class);
			
			break;

		case R.id.iv_title_rigist://注册
			break;
			
		case R.id.iv_title_left://返回
			viewControl.backUI();
			break;
			
		case R.id.iv_title_right://帮助
			break;

		default:
			break;
		}
	}
	
	/**
	 * 获取view实例
	 * @return
	 */
	public  View getView(){
		return view;
	}

	
	/**
	 * 当被观察的内容发生变化
	 */
	@Override
	public void update(Observable observable, Object data) {
		Log.d(TAG,"加载的页面是"+data.toString());
		
		if("HallView".equals(data)){
			titleNoLogin();
		}else if("SecondView".equals(data)){
			titleNormal("登陆");
		}else if("BettingView".equals(data)){
			titleNormal("投注大厅");
		}
	}


}
