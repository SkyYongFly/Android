package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daomain.BlackNumInfo;
import com.example.db.BlacknumSQLDao;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BlackNumber extends Activity {
    private ListView lv_showBlackNumber;
    private List<BlackNumInfo> list ;
    private BlacknumSQLDao dao;
    private MyAdapter  adapter;
    int offset =0;
    int maxnum = 20;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);

         //获取数据库，查询出所有数据
       dao = new BlacknumSQLDao(this);
      // list= (dao.queryAll() == null?new ArrayList<BlackNumInfo>():dao.queryAll());

        // adapter  = new MyAdapter();
        lv_showBlackNumber = (ListView) findViewById(R.id.lt_showNumber);
        //lv_showBlackNumber.setAdapter(adapter);
         fillData();

         lv_showBlackNumber.setOnScrollListener(new AbsListView.OnScrollListener() {
             // 当滚动的状态发生变化的时候
             @Override
             public void onScrollStateChanged(AbsListView view, int scrollState) {
                 switch (scrollState){
                     case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: {//空闲状态
                         int lastposition = lv_showBlackNumber.getLastVisiblePosition();
                         if (lastposition == (list.size() - 1)) {
                             offset += maxnum;
                             fillData();
                         }
                     }
                         break;
                     case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 手指触摸滚动
                         break;
                     case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 惯性滑行状态
                         break;

                 }
             }
             // 滚动的时候调用的方法。
             @Override
             public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

             }
         });



    }

    public void fillData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(list==null){
                    list = (dao.queryPart(offset,maxnum)== null?new ArrayList<BlackNumInfo>():dao.queryPart(offset,maxnum));
                }else{
                    if(dao.queryPart(offset,maxnum)!=null)
                    list.addAll(dao.queryPart(offset,maxnum));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(adapter == null){
                            adapter = new MyAdapter();
                            lv_showBlackNumber.setAdapter(adapter);
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();

    }

    /**
     * 添加号码
     * @param v
     */
    public void addNumber(View v){
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        View view  = View.inflate(this,R.layout.dialog_add_black_number,null);
        builder.setView(view);
        final Dialog dialog = builder.create();

        final EditText  et_blackNum  = (EditText) view.findViewById(R.id.et_blackNum);
        final CheckBox cb_mode1 = (CheckBox) view.findViewById(R.id.cb_mode1);
        final CheckBox cb_mode2 = (CheckBox) view.findViewById(R.id.cb_mode2);
        Button  bt_yes = (Button) view.findViewById(R.id.bt_yes);
        Button  bt_no = (Button) view.findViewById(R.id.bt_no);
        //确定将输入的号码加入黑名单
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blackNum = et_blackNum.getText().toString().trim();
                if(TextUtils.isEmpty(blackNum)){
                    Toast.makeText(BlackNumber.this,"号码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String mode ;
                if(cb_mode1.isChecked()&&!cb_mode2.isChecked()){
                    mode= "1";
                }else if(!cb_mode1.isChecked()&&cb_mode2.isChecked()){
                    mode= "2";
                }else if(cb_mode1.isChecked() && cb_mode2.isChecked()){
                    mode = "3";
                }else{
                    Toast.makeText(BlackNumber.this,"请选择拦截的类型",Toast.LENGTH_SHORT).show();
                    return;
                }

                //修改数据库
                BlackNumInfo  num = new BlackNumInfo(blackNum,mode);
                dao.add(num);
                //需要更新list，界面刷新显示新添加的内容
                list.add(0,num);
                adapter.notifyDataSetChanged();

                dialog.dismiss();

            }
        });

        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }


    private class MyAdapter  extends BaseAdapter{
        @Override
        public int getCount() {
            if(list == null){
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        ViewHolder  holder ;
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null){
                LayoutInflater inflater =  BlackNumber.this.getLayoutInflater();
                view =  inflater.inflate(R.layout.show_black_number_item,null);

                //新建view的时候将子孩子的内存地址记录起来
                holder = new ViewHolder();
                holder.tv_showNum = (TextView) view.findViewById(R.id.tv_showNumber);
                holder.tv_mode = (TextView) view.findViewById(R.id.tv_mode);
                holder.iv_delete  = (ImageView) view.findViewById(R.id.iv_delete);
                //做一个记录标签，下次使用尅直接拿来用，不用再新查询子孩子地址
                view.setTag(holder);
            }else{
                view = convertView;
                //有历史记录的使用历史记录
                holder = (ViewHolder) view.getTag();
            }
           final  BlackNumInfo number=  list.get(position);
            holder.tv_showNum.setText("手机号码 "+number.getBlackNumber());
            String mode = number.getMode();
            if("1".equals(mode)){
                holder.tv_mode.setText("短信拦截");
            }else if("2".equals(mode)){
                holder.tv_mode.setText("手机拦截");
            }else if("3".equals(mode)){
                holder.tv_mode.setText("全部拦截");
            }


            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //询问用户是否真的要将号码从黑名单中移出
                    AlertDialog.Builder  builder  = new AlertDialog.Builder(BlackNumber.this);
                    builder.setMessage("您确定要将该号码从黑名单中移出吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //删除记录
                            dao.delete(number.getBlackNumber());
                            //将记录从黑名单集合中移出
                            list.remove(position);
                            //通知listview更新视图
                            adapter.notifyDataSetChanged();

                        }
                    });

                    builder.setNegativeButton("取消", null);

                    builder.show();

                }
            });

            return view;
        }
    }

    /**
     * View 对象的容器
     * 相当于一个记事本，记录着View中子对象的内存地址
     */
    static class  ViewHolder{
        TextView tv_showNum;
        TextView tv_mode;
        ImageView iv_delete;

    }


}
