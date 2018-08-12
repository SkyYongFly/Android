package com.example.mobilesafe.mobilesafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.StreamUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

public class SplashActivity extends Activity {

    private static final String TAG = "test";


    private String downloadURL;
    protected static final int ENTER_HOME = 0;
    protected static final int UPDATE_APK = 1;
    private static final int CONNECTION_FALTURE = 2;
    private static final int INSTALL_FAILTURE = 3;
    private String versionInfo;
    private TextView tv_version;
    private SharedPreferences sp =null;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case ENTER_HOME: {//进入主页
                    enterHome();
                }
                break;

                case UPDATE_APK: {//更新软件
                    updateApk();
                }
                break;

                case CONNECTION_FALTURE: { //错误信息
                    Toast.makeText(getApplicationContext(), "检查更新失败", Toast.LENGTH_SHORT).show();
                    enterHome();
                }
                break;
                case INSTALL_FAILTURE: { //安装信息
                    Toast.makeText(getApplicationContext(), "安装失败", Toast.LENGTH_SHORT).show();
                    enterHome();
                }
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences("isUpdateFile", MODE_PRIVATE);

        //创建桌面图标
        createShortcut();
        //将应用需要的数据库拷贝到本地
        copyDB("address.db");
        //将病毒库数据库写入到本地
        copyDB("antivirus.db");

        tv_version = (TextView) findViewById(R.id.tv_splash_version);
        //设置版本号
        setVersion();

        if (sp != null) {
            String isUpdate = sp.getString("isUpdate", null);
            if ("true".equals(isUpdate)) {
                //检查更新
                checkoutUpdate();
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //进入主页面
                        enterHome();
                    }
                }, 2000);

            }
        } else {
            checkoutUpdate();
        }


    }

    /**
    *创建桌面图标
     * */
    private void createShortcut(){
        //判断之前是否创建过桌面图标
            boolean hasCreateIcon = sp.getBoolean("hasCreateIcon", false);
            if (hasCreateIcon) {
                return;
            }

            //创建意图，发送广播告诉桌面要创建图标
            Intent intent = new Intent();
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            //桌面图标即跨界方式要做的三件事 ：1、名称   2、图标 3、点击图标要做什么事
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "贴心小管家");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

            //桌面图标对应的意图
            Intent shortcutIntent = new Intent();
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            shortcutIntent.setClassName(getPackageName(), "com.example.mobilesafe.mobilesafe.SplashActivity");//点击快捷图标打开第一个页面

            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            sendBroadcast(intent);//如果系统中存在其他的桌面应用也能相应该广播

            //将信息存储起来防止程序打开时重复创建快捷键
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("hasCreateIcon", true);
            editor.commit();

    }

    /**
     * 拷贝数据库
     * "antivirus.db"
     */
    private void copyDB(String dbName){
        File  file = new File(getFilesDir(),dbName);
        if(file.exists() && file.length()>0){
            return;
        }else{
            try {
                //数据库文件保存在assets目录中
                AssetManager am = getAssets();
                InputStream in = am.open(dbName);
                byte[] bt =new  byte[1024];
                int  length = 0;
                FileOutputStream out = new FileOutputStream(file);
                while((length=in.read(bt))!=-1){
                    out.write(bt,0,length);
                }
                in.close();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 设置版本号
     */
    private void setVersion() {
        if ((versionInfo = getVersionInfo()) != null) {
            tv_version.setText("版本号:" + versionInfo);
        } else {
            //doNothing
            Log.d(TAG, "设置版本号失败");
        }

    }


    /**
     * 获取版本号
     *
     * @return
     */
    private String getVersionInfo() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            Log.d(TAG, "获取版本号失败");
            return null;
        }
    }

    /**
     * 进入主界面
     */
    private void enterHome() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        //结束活动，防止用户进入主页面按返回键还回到开始页面
        finish();
    }


    /**
     * 检查更新
     */
    private void checkoutUpdate() {
        new Thread(new Runnable() {

            private HttpURLConnection connection;
            Message message = new Message();

            @Override
            public void run() {

                try {
                    URL url = new URL(getResources().getString(R.string.ServerUrl));
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(2000);

                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String updateInfo = StreamUtil.readFromStream(inputStream);
                        Log.d(TAG, updateInfo);
                        JSONObject json = new JSONObject(updateInfo);
                        String version = (String) json.get("version");
                        String comment = (String) json.get("comment");
                        downloadURL = (String) json.get("downloadURL");

                        //服务器版本号和当前版本号一致
                        if (version.equals(versionInfo)) {
                            message.what = ENTER_HOME;
                        } else {
                            message.what = UPDATE_APK;
                        }

                    } else {
                        message.what = CONNECTION_FALTURE;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d(TAG, "URL异常");
                    message.what = CONNECTION_FALTURE;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                    Log.d(TAG, "异常");
                    message.what = CONNECTION_FALTURE;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON异常");
                    message.what = CONNECTION_FALTURE;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "IO异常");
                    message.what = CONNECTION_FALTURE;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                    handler.sendMessage(message);
                }
            }
        }).start();
    }


    /**
     * 更新apk
     */
    protected void updateApk() {
        final Message message = new Message();
        //弹出对话框
        AlertDialog.Builder dialog = new Builder(this);
        dialog.setTitle("更新软件");
        dialog.setMessage("检测到新版本，是否立即更新软件？");
        //升级
        dialog.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    FinalHttp http = new FinalHttp();
                    http.download(downloadURL, Environment.getExternalStorageDirectory().getAbsolutePath() + "/MobileSafe2.apk",
                            new AjaxCallBack<File>() {
                                @Override
                                public void onLoading(long count, long current) {
                                    super.onLoading(count, current);
                                }

                                @Override
                                public void onSuccess(File file) {
                                    super.onSuccess(file);
                                    //安装软件
                                    installApk(file);
                                }

                                @Override
                                public void onFailure(Throwable t, int errorNo, String strMsg) {
                                    super.onFailure(t, errorNo, strMsg);
                                    t.printStackTrace();
                                    Toast.makeText(SplashActivity.this, "安装更新包失败", Toast.LENGTH_SHORT).show();
                                    message.what = INSTALL_FAILTURE;
                                }

                                //安装更新包
                                private void installApk(File file) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                    startActivity(intent);

                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = INSTALL_FAILTURE;
                } finally {
                    handler.sendMessage(message);
                }

            }
        });

        //不升级
        dialog.setNegativeButton("取消升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Message message = new Message();
                message.what = ENTER_HOME;
                handler.sendMessage(message);

                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
