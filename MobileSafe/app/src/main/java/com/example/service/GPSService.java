package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.example.mobilesafe.mobilesafe.R;

import java.security.Provider;

public class GPSService extends Service {
    private LocationManager locationManager;
    private MyListener listener ;
    public GPSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //获取位置管理系统服务
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //给位置管理提供条件，选择精度好的
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //获取最佳精度条件
        String provider = locationManager.getBestProvider(criteria, true);

        listener = new MyListener();
        //当位置发生变化
        locationManager.requestLocationUpdates(provider,0,0,listener);

    }

    private  class MyListener  implements  LocationListener{

        //当位置发生改变的时候调用此方法
        @Override
        public void onLocationChanged(Location location) {
            //经度
            String  longitude  =  "j"+location.getLongitude();
            //纬度
            String latitude =  "w:"+location.getLatitude();
            //精度
            String accuracy =  "a"+location.getAccuracy();

            SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.applicationSaveFile),MODE_PRIVATE);
            SharedPreferences.Editor  editor = sp.edit();
            editor.putString("location", longitude + latitude + accuracy);
            editor.commit();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消位置监听服务
        locationManager.removeUpdates(listener);
        listener = null;
    }
}
