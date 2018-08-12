package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.List;

public class AntiVirus extends Activity {

    private static final int SCNNING = 0;
    private static final int FINISH = 1;
    private ImageView iv_scan;//扫描动态变化的图片
    private ProgressBar progressBar;//显示扫描的进度
    private LinearLayout ll_showText;//显示扫描文件的状态
    private TextView tv_showScan;//显示当前正在进行的操作
    private PackageManager pm;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppInfo info = (AppInfo) msg.obj;
            switch (msg.what){
                case 0:{//扫描中
                    tv_showScan.setText("正在扫描："+info.name);
                    if(info.isVirus){//是病毒
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText("病毒程序："+info.name);
                        tv.setTextColor(Color.RED);
                        ll_showText.addView(tv,0);
                    }else{
                        TextView tv = new TextView(getApplicationContext());
                        tv.setText("扫描正常:"+info.name);
                        tv.setTextColor(Color.BLACK);
                        ll_showText.addView(tv,0);
                    }

                }break;

                case 1:{//扫描结束
                    tv_showScan.setText("扫描结束");
                    iv_scan.clearAnimation();


                }break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_virus);

        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        progressBar = (ProgressBar) findViewById(R.id.pb_showProgress);
        ll_showText = (LinearLayout) findViewById(R.id.ll_showText);
        tv_showScan = (TextView) findViewById(R.id.tv_showScan);

        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(500);//设置扫描一次的时间
        ra.setRepeatCount(Animation.INFINITE);//设置循环扫描
        iv_scan.setAnimation(ra);


        scanVirus();
    }

    /**
     * 扫描杀毒
     * 将软件的特征码和病毒库进行比对
     */
    private void scanVirus() {
        pm = getPackageManager();
        tv_showScan.setText("正在初始化16核杀毒引擎......");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int progress = 0;
                //获取所有已经安装的应用
                List<ApplicationInfo> listApp = pm.getInstalledApplications(0);
                progressBar.setMax(listApp.size());
                for (ApplicationInfo info : listApp) {
                    //获取应用的路径
                    String path = info.dataDir;
                    //获取文件的MD5信息
                    String fileMd5 = getFileMd5(path);
                    AppInfo  appInfo = new AppInfo();
                    appInfo.name = info.loadLabel(pm).toString();
                    appInfo.packageName = info.packageName;

                    if(VirusFind(fileMd5)){
                        //是病毒
                        appInfo.isVirus = true;
                    }else{
                        //不是病毒
                        appInfo.isVirus =false;
                    }

                    Message message = new Message();
                    message.obj = appInfo;
                    message.what = SCNNING;
                    handler.sendMessage(message);

                    progress++;
                    progressBar.setProgress(progress);

                    //使扫描进程看起来慢一些
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message =  new Message();
                message.what = FINISH;
                handler.sendMessage(message);
            }
        }).start();


    }

    /**
     * app信息
     */
    class  AppInfo{
        String name;
        String packageName;
        boolean isVirus;//是否是病毒
    }

    /**
     * 获取文件的MD5信息
     *
     * @param path
     */
    private String getFileMd5(String path) {

        try {

            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            MessageDigest  digest = MessageDigest.getInstance("MD5");//进行MD5加密
            byte[]  bt = new byte[1024];
            int length = -1;
            while((length=inputStream.read(bt))!=-1){
                digest.update(bt,0,length);
            }

            byte[] dig = digest.digest();
            StringBuffer buffer = new StringBuffer();
            for(byte d:dig){
                int num = d & 0xff;
                String str = Integer.toHexString(num);
                if(str.length()==1){
                    buffer.append("0");//确保每个字节两位，不足补零
                }
                buffer.append(str);
            }

            return buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 查找病毒库中是否存在指定查找的特征码
     * @param fileMD5
     * @return
     */
    private boolean VirusFind(String fileMD5){
        try {
            File file = new File(getFilesDir(), "antivirus.db");
            String path = file.getAbsolutePath();
            SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);//只读打开
            Cursor cursor = database.rawQuery("select * from  datable where md5=?", new String[]{fileMD5});
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
            if(cursor!=null){
                cursor.close();
            }
            if(database !=null){
                database.close();
            }
            return  false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
}
