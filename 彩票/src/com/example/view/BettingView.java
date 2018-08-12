package com.example.view;

import java.util.ArrayList;
import java.util.Random;

import com.example.R;
import com.example.util.PoolAdapter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class BettingView extends BaseView {
	private static final String TAG = "test";
	private Context context;
	// private View view;
	private GridView redGridView;
	private GridView blueGridView;

	// ���ڱ����Ѿ�����ĺ����λ��
	private static ArrayList<Integer> redList;
	// ���ڱ����Ѿ�����������λ��
	private static ArrayList<Integer> blueList;

	public BettingView(Context context) {
		this.context = context;
	}

	public View init() {
		redList = new ArrayList<Integer>();
		blueList = new ArrayList<Integer>();

		View bettingView = View.inflate(context, R.layout.betting_layout, null);

		Button redRandom = (Button) bettingView.findViewById(R.id.bt_redRandom);
		Button blueRandom = (Button) bettingView.findViewById(R.id.bt_blueRandom);

		

		blueGridView = (GridView) bettingView.findViewById(R.id.gv_blue);
		redGridView = (GridView) bettingView.findViewById(R.id.gv_red);

		PoolAdapter redAdapter = new PoolAdapter(33, context);
		redGridView.setAdapter(redAdapter);
		redGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!redList.contains(position)) { // ���֮ǰû�е����
					view.setBackgroundResource(R.drawable.id_redball);
					// ����ѡ��ʱ��Ķ���
					view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_ball));
					redList.add(Integer.valueOf(position));
				} else { // ���֮ǰ�����
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					redList.remove(Integer.valueOf(position));

				}
			}
		});

		PoolAdapter blueAdapter = new PoolAdapter(15, context);
		blueGridView.setAdapter(blueAdapter);
		blueGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!blueList.contains(position)) { // ���֮ǰû�е����
					view.setBackgroundResource(R.drawable.id_blueball);
					view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_ball));
					blueList.add(Integer.valueOf(position));
				} else { // ���֮ǰ�����
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					blueList.remove(Integer.valueOf(position));

				}
			}
		});

		redRandom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				redList.clear();
				while (redList.size() < 6) {
					int number = new Random().nextInt(33);
					if(redList.contains(number)){
						continue;
					}
					redList.add(number);
				}
				
				for(int i =0;i<33;i++){
					View ball = redGridView.getChildAt(i);
					if(redList.contains(i)){
						ball.setBackgroundResource(R.drawable.id_redball);
						ball.setAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_ball));
					}else {
						ball.setBackgroundResource(R.drawable.id_defalut_ball);
					}
					
				
				}

			}
		});
		
		blueRandom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				blueList.clear();
				while (blueList.size() < 1) {
					int number = new Random().nextInt(15);
					if(blueList.contains(number)){
						continue;
					}
					blueList.add(number);
				}
				
				for(int i =0;i<15;i++){
					View ball = blueGridView.getChildAt(i);
					if(blueList.contains(i)){
						ball.setBackgroundResource(R.drawable.id_blueball);
						ball.setAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_ball));
					}else {
						ball.setBackgroundResource(R.drawable.id_defalut_ball);
					}
					
				
				}
			}
		});

		//ҡ���ֻ���ѡ������
		//��ȡϵͳ����������
		SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		ShakeListener listener = new ShakeListener(context){
			@Override
			public void randomSelect() {
				redList.clear();
				while (redList.size() < 6) {
					int number = new Random().nextInt(33);
					if(redList.contains(number)){
						continue;
					}
					redList.add(number);
				}
				
				for(int i =0;i<33;i++){
					View ball = redGridView.getChildAt(i);
					if(redList.contains(i)){
						ball.setBackgroundResource(R.drawable.id_redball);
						ball.setAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_ball));
					}else {
						ball.setBackgroundResource(R.drawable.id_defalut_ball);
					}
					
				
				}
				blueList.clear();
				while (blueList.size() < 1) {
					int number = new Random().nextInt(15);
					if(blueList.contains(number)){
						continue;
					}
					blueList.add(number);
				}
				
				for(int i =0;i<15;i++){
					View ball = blueGridView.getChildAt(i);
					if(blueList.contains(i)){
						ball.setBackgroundResource(R.drawable.id_blueball);
						ball.setAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_ball));
					}else {
						ball.setBackgroundResource(R.drawable.id_defalut_ball);
					}
					
				
				}
			}
		};
		//Ϊ���ٶȴ�����ע������¼�
		manager.registerListener(listener,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager
				.SENSOR_DELAY_FASTEST);
		
		
		return bettingView;
	}

	@Override
	public View getView() {
		return init();
	}

	public static void clear() {
		redList.clear();
		blueList.clear();
	}

}
