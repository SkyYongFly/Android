package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daomain.ProgressInfo;
import com.example.engine.TaskInfoProvider;
import com.example.utils.TaskUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TaskManager extends Activity {
    private TextView tv_all_tasks;
    private TextView tv_taskMemory;
    private ListView lv_showTasks;
    private LinearLayout ll_load;
    private List<ProgressInfo> listUser = new ArrayList<>();
    private List<ProgressInfo> listSystem = new ArrayList<>();
    private List<ProgressInfo> listAll = new ArrayList<>();
    private MyAdapter myAdapter;
    private long availableMemory;
    private long allMemory;
    private SharedPreferences sp ;

    public TaskManager() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        sp = getSharedPreferences(getResources().getString(R.string.applicationSaveFile),MODE_PRIVATE);

        tv_all_tasks = (TextView) findViewById(R.id.tv_all_tasks);
        tv_taskMemory = (TextView) findViewById(R.id.tv_taskMemory);
        ll_load = (LinearLayout) findViewById(R.id.ll_load_tasks);

        tv_all_tasks.append(TaskUtils.getRunningTasks(this) + "");

        availableMemory = TaskUtils.getTaskAvailMemory(this);
        allMemory = TaskUtils.getTaskAllMemory(this);
        tv_taskMemory.append(Formatter.formatFileSize(getApplicationContext(),availableMemory) + "//" +
                Formatter.formatFileSize(getApplicationContext(),allMemory));

        lv_showTasks = (ListView) findViewById(R.id.lv_showTasks);
        fillData();

        lv_showTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProgressInfo info;
               // CheckBox  cb_select = (CheckBox) view.findViewById(R.id.cb_select);
                if(position == 0|| position==listUser.size()+1){
                    return;
                }else if(position<=listUser.size()){
                    info = listUser.get(position-1);
                }else{
                    info = listSystem.get(position-2-listUser.size());
                }

                if(info.isChecked == false){
                    info.isChecked =true;
                }else{
                    info.isChecked=false;
                }

                myAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 将数据填充进布局
     */
    private void fillData() {
        ll_load.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                listAll = TaskInfoProvider.getTasksInfo(TaskManager.this);
                for (ProgressInfo info : listAll) {
                    if (info.isUserProgress()) {
                        listUser.add(info);
                    } else {
                        listSystem.add(info);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(myAdapter == null){
                            myAdapter = new MyAdapter();
                            lv_showTasks.setAdapter(myAdapter);
                        }else{
                            myAdapter.notifyDataSetChanged();
                        }

                        ll_load.setVisibility(View.INVISIBLE);

                    }
                });

            }
        }).start();


    }

    private ViewHolder holder;

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            boolean  showSysTask = sp.getBoolean("showSystemTasks",false);
            if(showSysTask){
                return listAll.size() + 2;
            }else{
                return  listUser.size()+1;
            }

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
            View view;
            ProgressInfo progressInfo;
            if (position == 0) {
                TextView tv_showUserTask = new TextView(getApplicationContext());
                tv_showUserTask.setText("用户进程数：" + listUser.size() + "个");
                tv_showUserTask.setTextSize(20);
                tv_showUserTask.setTextColor(Color.WHITE);
                tv_showUserTask.setBackgroundColor(Color.GRAY);
                return tv_showUserTask;
            } else if (position == listUser.size() + 1) {
                TextView tv_showUserTask = new TextView(getApplicationContext());
                tv_showUserTask.setText("系统进程数：" + listSystem.size() + "个");
                tv_showUserTask.setTextSize(20);
                tv_showUserTask.setTextColor(Color.WHITE);
                tv_showUserTask.setBackgroundColor(Color.GRAY);
                return tv_showUserTask;
            } else if (position <= listUser.size()) {
                progressInfo = listUser.get(position - 1);
            } else {
                progressInfo = listSystem.get(position - 2 - listUser.size());
            }


            if (convertView != null && convertView instanceof LinearLayout) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                LayoutInflater inflater = TaskManager.this.getLayoutInflater();
                view = inflater.inflate(R.layout.task_item_layout, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_memSize = (TextView) view.findViewById(R.id.tv_memSize);
                holder.cb_select = (CheckBox)view.findViewById(R.id.cb_select);
                view.setTag(holder);
            }

            holder.iv_icon.setImageDrawable(progressInfo.getIcon());
            holder.tv_name.setText(progressInfo.getName());
            holder.tv_memSize.setText(Formatter.formatFileSize(getApplicationContext(), progressInfo.getSize()));
            if(progressInfo.isChecked){
                holder.cb_select.setChecked(true);
            }else{
                holder.cb_select.setChecked(false);
            }

            return view;
        }
    }

    private class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_memSize;
        CheckBox  cb_select;
    }

    /**
     * 全选
     * @param v
     */
    public void selectAll(View v){
        for(ProgressInfo p:listAll){
            p.isChecked = true;
        }
        myAdapter.notifyDataSetChanged();
    }


    /**
     * 反选
     * @param v
     */
    public void diSelectAll(View v){
        for(ProgressInfo p:listAll){
            p.isChecked = false;
        }
        myAdapter.notifyDataSetChanged();
    }

    /**
     * 清理
     * 注意：这里清理掉的进程需要把集合中相应的数据移出，不仅整个集合需要，用户进程集合和系统进程集合也要
     * 因为杀掉进程后通知刷新页面，并没有重新读取进程数据，还是用的原来的数据，所以需要从各自的集合移出
     * 另外不能使用fillData() 因为这是又一次加载数据，造成进程数据重复
     * @param v
     */
    public void cleanAll(View v){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ProgressInfo>  listKill  = new ArrayList<>();//保存杀掉的进程
        for(ProgressInfo  p :listAll){

            if(p.isChecked){
                //不能杀掉自身
                if(p.getPackageName().equals(getApplicationContext().getPackageName())){
                    Toast.makeText(TaskManager.this, "若清理掉萌萌哒小管家会使手机不安全哦!", Toast.LENGTH_SHORT).show();
                    continue;
                }

                listKill.add(p);

                if(p.isUserProgress()){
                    listUser.remove(p);
                }else{
                    listSystem.remove(p);
                }
                manager.killBackgroundProcesses(p.getPackageName());
            }
        }
        //将已经杀掉的进程从集合中移出
        listAll.removeAll(listKill);

        //刷新显示的总进程数
        int runningProgress = listUser.size()+listSystem.size();
        tv_all_tasks.setText("当前进程数："+runningProgress+"个");

        //计算释放掉的内存
        long releaseSize = 0;
        for(ProgressInfo info :listKill){
            releaseSize +=info.getSize();
        }
        //设置显示
        availableMemory = TaskUtils.getTaskAvailMemory(this)+releaseSize;
        tv_taskMemory.setText("可用/总内存：" + Formatter.formatFileSize(getApplicationContext(), availableMemory) + "//" +
                Formatter.formatFileSize(getApplicationContext(), allMemory));

        //通知重新刷新加载页面
        myAdapter.notifyDataSetChanged();

        if(listKill.size()>0)
        Toast.makeText(this,"恭喜您，成功清理进程数"+listKill.size()+"个,释放内存"+Formatter.formatFileSize(getApplicationContext(),releaseSize),Toast.LENGTH_SHORT).show();

    }

    /**
     * 设置
     * @param v
     */
    public void setting(View v){
        Intent intent = new Intent(this,TaskSetting.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        myAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
