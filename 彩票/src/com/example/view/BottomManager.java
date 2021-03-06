package com.example.view;

import java.util.Observable;
import java.util.Observer;

import com.example.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 底部样式和相关事件的管理者
 * 
 * @author yzas
 *
 */
public class BottomManager  implements OnClickListener,Observer {

	private Context context;
	private RelativeLayout ll_bottom;
	
	private LinearLayout ll_bottom_normal;
	private LinearLayout ll_bottom_select;
	
	private View view;
	private TextView tv_show;

	public BottomManager(Context context) {
		this.context = context;
		view = init();
	}

	public View init() {
		// 获取底部布局
		//ll_bottom = (RelativeLayout) View.inflate(context, R.id.bottom_layout, null);
		Activity activity = (Activity) context;
		ll_bottom = (RelativeLayout) activity.findViewById(R.id.bottom_layout);

		// 获取不同状态下的底部布局：普通模式、选择双色球状态
		ll_bottom_normal = (LinearLayout) ll_bottom.findViewById(R.id.ll_bottom_normal);
		ll_bottom_select = (LinearLayout) ll_bottom.findViewById(R.id.ll_bottom_select);

		return ll_bottom;
	}
	
	/**
	 * 普通模式下的底部布局
	 */
	public void bottomNormal(){
		ll_bottom_normal.setVisibility(View.VISIBLE);
		ll_bottom_select.setVisibility(View.INVISIBLE);
		
	}

	/**
	 * 选号大厅模式下的底部布局
	 */
	private void bottomSelect() {
		ll_bottom_normal.setVisibility(View.INVISIBLE);
		ll_bottom_select.setVisibility(View.VISIBLE);
		tv_show = (TextView) ll_bottom_select.findViewById(R.id.tv_button_show);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_login:

			break;

		case R.id.iv_title_rigist:
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
		if("HallView".equals(data)){
			bottomNormal();
		}else if("SecondView".equals(data)){
			bottomNormal();
		}else  if("BettingView".equals(data)){
			bottomSelect();
			tv_show.setText("双色球加奖了，2亿元大派送");
		}
	}

	
}
