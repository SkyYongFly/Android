package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 * 手机流量管理
 */
public class TrafficManager extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_manager);

        //获取包管理器
        PackageManager pm = getPackageManager();
        //获取所有已经安装的应用
        List<ApplicationInfo>  listApp = pm.getInstalledApplications(0);
        for(ApplicationInfo info:listApp){
            //获取每个应用的uid
            int uid = info.uid;
            long  tx = TrafficStats.getUidTxBytes(uid);//获取应用的上传流量 bytes
            long rx = TrafficStats.getUidRxBytes(uid);//获取应用下载的流量  bytes
        }

        long mobileTx  = TrafficStats.getMobileTxBytes();//获取手机移动网络上传的流量
        long mobileRx = TrafficStats.getMobileRxBytes();//手机移动网络下载的流量
        long totalTx = TrafficStats.getTotalTxBytes();//获取手机上传的所有流量（wifi,移动流量）
        long totalRx = TrafficStats.getTotalRxBytes();//获取手机下载的所有流量（wifi,移动流量）
        //-1代表没有流量或者系统不支持获取流量

        TextView   tv_showTraffic = (TextView) findViewById(R.id.tv_showTraffic);
        tv_showTraffic.setText("手机移动网络上传的流量:"+ Formatter.formatFileSize(getApplicationContext(),mobileTx)+
        "\n手机移动网络下载的流量:"+Formatter.formatFileSize(getApplicationContext(),mobileRx)+
        "\n手机上传的所有流量:"+Formatter.formatFileSize(getApplicationContext(),totalTx)+
        "\n手机下载的所有流量:"+Formatter.formatFileSize(getApplicationContext(),totalRx));

    }
}
