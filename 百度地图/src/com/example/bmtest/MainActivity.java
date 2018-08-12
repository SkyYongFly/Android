package com.example.bmtest;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import android.app.Activity;
import android.os.Bundle;

/**
 * �ٶȵ�ͼӦ��
 * @author yzas
 *
 */
public class MainActivity extends Activity {
	//��ͼ�Ĺ�����
//	private BMapManager  mapManager = new BMapManager();
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ӧ����Ҫ�ڼ��ص�ͼ֮ǰ�������ͻ�ȡ���������ݵ�״̬��Ϣ������ᷢ���쳣
		SDKInitializer.initialize(getApplicationContext());
		
		setContentView(R.layout.activity_main);
		
		mapView = (MapView) findViewById(R.id.id_mapview);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

}
