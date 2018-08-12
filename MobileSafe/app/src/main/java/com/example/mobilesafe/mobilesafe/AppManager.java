package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daomain.AppInfo;
import com.example.db.LockAppSQLDao;
import com.example.engine.AppInfoProvider;
import com.example.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class AppManager extends Activity implements View.OnClickListener {

    private static final String TAG = "test";
    private TextView tv_rom;//显示手机内存可用大小
    private TextView tv_sd;//显示手机SD卡可用大小
    private ListView lv_showApp;
    private List<AppInfo> list; //存储手机已经安装的所有应用信息
    private List<AppInfo> listUser = new ArrayList<>(); //存储手机已经安装的所有用户应用信息
    private List<AppInfo> listSystem = new ArrayList<>(); //存储手机已经安装的所有系统应用信息
    private LinearLayout ll_load;
    private MyAdapter adapter;
    private ViewHolder holder;
    private AppInfo appInfo;//应用程序对象，用于保存点击的listview的条目，方便后面打开或者卸载程序
    private PopupWindow popupWindow;//弹出窗口
    private LinearLayout ll_start_app;
    private LinearLayout ll_delete_app;
    private LinearLayout ll_share_app;
    private SharedPreferences sp ;
    private boolean  isLock; //是否打开锁定应用程序功能
    private LockAppSQLDao lockAppSQLDao;
    private List<String>  listLockApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);

        sp = getApplication().getSharedPreferences("isUpdateFile", MODE_PRIVATE);
       // isLock  = sp.getBoolean("isLockApp", false);
        lockAppSQLDao = new LockAppSQLDao(this);


        tv_rom = (TextView) findViewById(R.id.tv_rom);
        tv_sd = (TextView) findViewById(R.id.tv_sd);
        ll_load = (LinearLayout) findViewById(R.id.ll_load);
        //设置存储大小
        tv_rom.append(Formatter.formatFileSize(this, getAvailableStorage(Environment.getDataDirectory().getAbsolutePath())));
        tv_sd.append(Formatter.formatFileSize(this, getAvailableStorage(Environment.getExternalStorageDirectory().getAbsolutePath())));


        lv_showApp = (ListView) findViewById(R.id.lv_showApp);
        fillData();

        lv_showApp.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            //当滚动的时候调用
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                dismissPopwindow();
            }
        });

        lv_showApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissPopwindow();
                // 获取点击的是哪一个项目
                if (position == 0 || position == listUser.size() + 1) {
                    return;
                } else if (position <= listUser.size()) {
                    appInfo = listUser.get(position - 1);//集合位置从0开始
                } else {
                    appInfo = listSystem.get(position - 2 - listUser.size());
                }

                //加载弹出窗口
                View contentView = View.inflate(getApplicationContext(), R.layout.app_item_pop, null);

                ll_start_app = (LinearLayout) contentView.findViewById(R.id.ll_startApp);
                ll_delete_app = (LinearLayout) contentView.findViewById(R.id.ll_deleteApp);
                ll_share_app = (LinearLayout) contentView.findViewById(R.id.ll_shareApp);

                ll_start_app.setOnClickListener(AppManager.this);//打开应用
                ll_delete_app.setOnClickListener(AppManager.this);//删除应用
                ll_share_app.setOnClickListener(AppManager.this);//分享应用

                popupWindow = new PopupWindow(contentView, DensityUtil.dip2px(getApplicationContext(), 140), DensityUtil.dip2px(getApplicationContext(), 80));
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //int[] location = new int[2];
                // view.getLocationInWindow(location);//将视图距离左上角的距离存入数组
                //popupWindow.showAtLocation(parent, Gravity.TOP | Gravity.LEFT, 40, 40);
                //使弹出窗口在指定组件的周围
                popupWindow.showAsDropDown(view,                        //指定组件
                        DensityUtil.dip2px(getApplicationContext(), 40),  //x距离
                        -DensityUtil.dip2px(getApplicationContext(), 120));//y距离

                //加载动画效果
                ScaleAnimation animation = new ScaleAnimation(0.3f,//动画起始时 X坐标上的伸缩尺寸
                        1.0f,//到达的x位置
                        0.3f,//动画起始时Y坐标上的伸缩尺寸
                        1.0f,//到达的y位置
                        Animation.RELATIVE_TO_SELF,//动画在X轴相对于物件位置类型
                        0,                          //动画相对于物件的X坐标的开始位置
                        Animation.RELATIVE_TO_SELF,// 动画在Y轴相对于物件位置类型---相对于自身
                        0.5f                        // 动画相对于物件的Y坐标的开始位置
                );
                animation.setDuration(300);//动画过渡时长

                //加载透明度渐变效果
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);//从半透明到不透明
                alphaAnimation.setDuration(300);//加载时长

                AnimationSet set = new AnimationSet(false);//设置为false其中每个组件加载自己的效果
                set.addAnimation(animation);
                set.addAnimation(alphaAnimation);
                //加载动画
                contentView.setAnimation(set);

            }
        });

        //设置长时间点击选项，切换是否锁定应用程序
        lv_showApp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listLockApp = lockAppSQLDao.findAll();
                // 获取点击的是哪一个项目
                if (position == 0 || position == listUser.size() + 1) {
                    return  false;
                } else if (position <= listUser.size()) {
                    appInfo = listUser.get(position - 1);//集合位置从0开始
                } else {
                    appInfo = listSystem.get(position - 2 - listUser.size());
                }
                //ImageView iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
                holder = (ViewHolder) view.getTag();
                if(listLockApp.contains(appInfo.getPackageName())){//之前枷锁了
                    holder.iv_lock.setImageResource(R.drawable.unlock);//解锁
                    lockAppSQLDao.delete(appInfo.getPackageName());//从数据库删除
                }else{//之前没锁
                    holder.iv_lock.setImageResource(R.drawable.lock);//枷锁
                    lockAppSQLDao.add(appInfo.getPackageName());//保存到数据库
                }


                //通知后台服务锁定应用信息的数据库信息发生了变化
                Intent intent1 = new Intent();
                intent1.setAction("com.example.dataChanged");
                sendBroadcast(intent1);

                return true;//返回true代表长点击事件只到这里结束不会引发短点击事件
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPopwindow();
    }

    /**
     * listview填充数据
     */
    private void fillData() {
        //加载进度条
        ll_load.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = AppInfoProvider.getAppInfo(AppManager.this);
                for (AppInfo info : list) {
                    if (info.isUserApp()) {
                        listUser.add(info);
                    } else {
                        listSystem.add(info);
                    }
                }

                Log.d("test", "用户程序" + listUser.size());
                Log.d("test", "系统程序" + listSystem.size());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter == null) {
                            adapter = new MyAdapter();
                            lv_showApp.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                        //进度条不再显示
                        ll_load.setVisibility(View.INVISIBLE);
                    }
                });
            }

        }).start();


    }

    /**
     * 获取手机可用存储大小
     *
     * @param absolutePath 手机rom或者SD卡路径
     * @return
     */
    private long getAvailableStorage(String absolutePath) {

        StatFs statFs = new StatFs(absolutePath);
        long blockSize = statFs.getBlockSize();//获取分区块大小
        //long blockSize = statFs.getBlockSizeLong();//API18
        long availableBlocks = statFs.getAvailableBlocks();//获取可用的分区大小
        //long availableBlocks = statFs.getAvailableBlocksLong();//API18

        return blockSize * availableBlocks;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_startApp: {
                Log.d(TAG, "开启应用");
                startApplication();
            }
            break;

            case R.id.ll_deleteApp: {
                Log.d(TAG, "删除应用");
                deleteApplication();
            }
            break;

            case R.id.ll_shareApp: {
                Log.d(TAG, "分享应用");
                shareApplication();
            }
            break;
        }

    }

    /**
     * 打开应用
     */
    private void startApplication() {
        //查询这个应用程序的入口activity
        PackageManager pm = getPackageManager();

        // Intent intent = new Intent();
        // intent.setAction("android.intent.action.MAIN");
        // intent.addCategory("android.intent.category.LAUNCHER");
        // //查询出来了手机上所有的具有启动能力的activity。
        // List<ResolveInfo> infos = pm.queryIntentActivities(intent,
        // PackageManager.GET_INTENT_FILTERS);

       Intent intent =  pm.getLaunchIntentForPackage(appInfo.getPackageName());
        if(intent!=null){
            try {
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AppManager.this, "不能打开这个应用", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AppManager.this, "不能打开这个应用", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 删除应用
     */
    private void deleteApplication() {
        Intent intent  = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setAction("android.intent.action.DELETE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + appInfo.getPackageName()));
        startActivityForResult(intent,0);//为了删除应用后刷新应用界面

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //卸载应用后刷新页面
        fillData();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 分享应用
     */
    private void shareApplication() {
        Intent intent  = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件：" + appInfo.getName());
        startActivity(intent);
    }


    private class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size() + 2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppInfo info;

            if (position == 0) {
                Log.d("test", "当前位置" + position);
                TextView textView = new TextView(getApplicationContext());
                textView.setText("用户应用:" + listUser.size() + "个");
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(16);
                textView.setBackgroundColor(Color.GRAY);
                return textView;
            } else if (position == (listUser.size() + 1)) {
                Log.d("test", "当前位置" + position);
                TextView textView = new TextView(getApplicationContext());
                textView.setText("系统应用:" + listSystem.size() + "个");
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(16);
                textView.setBackgroundColor(Color.GRAY);
                return textView;
            } else if (position <= listUser.size()) {
                Log.d("test", "当前位置" + position);
                info = listUser.get(position - 1);
            } else {
                Log.d("test", "当前位置" + position);
                info = listSystem.get(position - 2 - listUser.size());
            }

            View view = null;
//            if(convertView == null){
//               // view = View.inflate(AppManager.this,R.layout.app_item_layout,null);
//                LayoutInflater inflater = AppManager.this.getLayoutInflater();
//                view  = inflater.inflate(R.layout.app_item_layout,null);
//                holder = new ViewHolder();
//                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
//                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
//                holder.tv_location = (TextView) view.findViewById(R.id.tv_location);
//                view.setTag(holder);
//            }else{
//                //防止加载复用的是TextView
//                if(convertView instanceof  LinearLayout){
//                    view = convertView;
//                    holder = (ViewHolder) view.getTag();
//                }
//
//            }

            //防止加载复用的是TextView
            if (convertView != null && convertView instanceof LinearLayout) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                // view = View.inflate(AppManager.this,R.layout.app_item_layout,null);
                LayoutInflater inflater = AppManager.this.getLayoutInflater();
                view = inflater.inflate(R.layout.app_item_layout, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_location = (TextView) view.findViewById(R.id.tv_location);
                holder.iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
                view.setTag(holder);
            }

            holder.iv_icon.setImageDrawable(info.getIcon());
            holder.tv_name.setText(info.getName());
            if (info.isInRom()) {
                holder.tv_location.setText("手机内存");
            } else {
                holder.tv_location.setText("SD卡");
            }

            isLock  = sp.getBoolean("isLockApp", false);
            listLockApp = lockAppSQLDao.findAll();
            if(isLock){
                if(listLockApp.contains(info.getPackageName())) {
                    holder.iv_lock.setImageResource(R.drawable.lock);
                }else{
                    holder.iv_lock.setImageResource(R.drawable.unlock);
                }
            }

            return view;
        }


    }

    /**
     * 使点击弹出的窗口关闭
     */
    private void dismissPopwindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_location;
        ImageView iv_lock;
    }

}
