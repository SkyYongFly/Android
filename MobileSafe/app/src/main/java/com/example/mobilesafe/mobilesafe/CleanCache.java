package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import  android.content.pm.IPackageStatsObserver;
import  android.content.pm.IPackageDataObserver;
import  android.content.pm.PackageStats;
/**
 * 清理手机缓存
 */
public class CleanCache extends Activity {
    private TextView tv_scan;
    private ProgressBar pb;
    private LinearLayout  ll_showText;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_cache);

        tv_scan = (TextView) findViewById(R.id.tv_scan);
        pb = (ProgressBar) findViewById(R.id.pb);
        ll_showText = (LinearLayout) findViewById(R.id.ll_showText);

        scanCache();
    }

    /**
     * 扫描缓存
     */
    private void scanCache(){
        pm = getPackageManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Method getPackageSizeInfoMethod = null;
                Method[]  methods = PackageManager.class.getMethods();
                for(Method m :methods){
                    if("getPackageSizeInfo".equals(m.getName())){
                        getPackageSizeInfoMethod = m;
                    }
                }
                List<PackageInfo> listPackage =  pm.getInstalledPackages(0);
                pb.setMax(listPackage.size());
                int progres = 0;
                for(PackageInfo info:listPackage){
                    try {
                        //权限.GET_PACKAGE_SIZE.
                        getPackageSizeInfoMethod.invoke(pm, info.packageName, new MyDataObserver());
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progres++;
                    pb.setProgress(progres);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_scan.setText("扫描完毕");
                    }
                });

            }
        }).start();
    }


    private class MyDataObserver extends  IPackageStatsObserver.Stub{
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded){
            //缓存大小
            final long cache = pStats.cacheSize;
            final String packlageName = pStats.packageName;

            try {
                final ApplicationInfo appInfo = pm.getApplicationInfo(packlageName,0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_scan.setText("正在扫描：" + appInfo.loadLabel(pm));
                        //if (cache > 0) {
                            View view = View.inflate(getApplicationContext(), R.layout.cache_list_item, null);
                            TextView tv_showCacheText = (TextView) view.findViewById(R.id.tv_showCacheText);
                            TextView tv_showCacheSize = (TextView) view.findViewById(R.id.tv_showCacheS);
                            tv_showCacheText.setText(appInfo.loadLabel(pm));
                            tv_showCacheSize.setText("缓存大小：" + Formatter.formatFileSize(getApplicationContext(), cache));

                            //单独清理缓存
                            ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_deleteCache);
                            iv_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Method method = PackageManager.class.getMethod("deleteApplicationCacheFiles", String.class, IPackageDataObserver.class);
                                        method.invoke(pm, packlageName, new MypackDataObserver());
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            //加载显示内容
                            ll_showText.addView(view, 0);

                        }
                   // }
                });

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }



        }

    }

    private class  MypackDataObserver extends IPackageDataObserver.Stub{

        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }


    /**
     * 清理所有缓存
     * @param v
     */
    public void cleanAll(View v){
        Method[] methods = PackageInfo.class.getMethods();
        for(Method  m:methods){
            if("freeStorageAndNotify".equals(m.getName())){
                try {
                    m.invoke(pm,Integer.MAX_VALUE,new MypackDataObserver());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            return;
        }


    }


}
