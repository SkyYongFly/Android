package com.example.bmtest;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import android.app.Activity;
import android.os.Bundle;

/**
 * 百度地图应用
 * @author yzas
 *
 */
public class MainActivity extends Activity {
	//地图的管理类
//	private BMapManager  mapManager = new BMapManager();
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//应用需要在加载地图之前检查网络和获取服务器数据等状态信息，否则会发生异常
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
