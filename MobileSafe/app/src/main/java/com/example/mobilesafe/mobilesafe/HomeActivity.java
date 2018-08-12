package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {


    private static String[] nameString = {
            "手机防盗", "通讯卫士", "软件管理",
            "进程管理", "清理缓存", "流量统计",
            "手机杀毒", "高级工具", "系统设置"
    };
    private static int[] imageList = {
            R.drawable.safe, R.drawable.callmsgsafe, R.drawable.sysoptimize,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.app,
            R.drawable.trojan ,R.drawable.settings,R.drawable.atools};

    private SharedPreferences sp;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("configFile", MODE_PRIVATE);

        GridView gd_list = (GridView) findViewById(R.id.gd_list);
        //设置界面显示
        gd_list.setAdapter(new MyAdapter());
        //设置图标点击触发事件
        gd_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {//进入手机防盗页面

                        //判断是否设置过密码
                        hasPassword();
                    }
                    break;

                    case 1: {//进入黑名单管理
                        Intent intent = new Intent(HomeActivity.this, BlackNumber.class);
                        startActivity(intent);
                    }
                    break;

                    case 2:{//进入软件管理
                            Intent intent = new Intent(HomeActivity.this, AppManager.class);
                        startActivity(intent);

                    }break;
                    case 3:{//进入进程管理
                        Intent intent = new Intent(HomeActivity.this, TaskManager.class);
                        startActivity(intent);

                    }break;

                    case 4:{//情理手机缓存
                        Intent intent = new Intent(HomeActivity.this, CleanCache.class);
                        startActivity(intent);
                    }break;

                    case 5:{//进入手机流量统计页面
                        Intent intent = new Intent(HomeActivity.this, TrafficManager.class);
                        startActivity(intent);

                    }break;

                    case 6:{//进入手机杀毒
                        Intent intent = new Intent(HomeActivity.this, AntiVirus.class);
                        startActivity(intent);
                    }break;

                    case 7: {//进入系统优化
                        Intent intent = new Intent(HomeActivity.this, SystemOptimize.class);
                        startActivity(intent);
                    }
                    break;

                    case 8: {//打开设置中心
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);

                    }
                    break;

                    default:
                        break;

                }
            }
        });

    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return nameString.length;
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
            //获取九宫格布局文件
            View list_view = View.inflate(HomeActivity.this, R.layout.list_item, null);
            ImageView item_image = (ImageView) list_view.findViewById(R.id.iv_icon);
            TextView item_text = (TextView) list_view.findViewById(R.id.tv_funtion);
            //设置图标
            item_image.setImageResource(imageList[position]);
            //设置功能文字
            item_text.setText(nameString[position]);
            return list_view;
        }
    }


    /**
     * 进入手机防盗界面，首先判断是否设置过密码，如果没有则弹出对话框设置密码，如果设置过则输入密码
     */
    private void hasPassword() {
        String password = sp.getString("fdPwd", null);
        if (TextUtils.isEmpty(password)) {//没有设置过密码
            setPassword();
        } else {//检查密码
            checkPassword();
        }


    }

    /**
     * 设置进入防盗界面密码
     */
    private void setPassword() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        //加载布局文件
        View view = View.inflate(HomeActivity.this, R.layout.setpassword, null);
        //获取控件
        final EditText et_password1 = (EditText) view.findViewById(R.id.et_password1);
        final EditText et_password2 = (EditText) view.findViewById(R.id.et_password2);
        Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);


        //确认输入的密码
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取输入的两次密码
                final String pwd1 = et_password1.getText().toString().trim();
                final String pwd2 = et_password2.getText().toString().trim();

                if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!pwd1.equals(pwd2)) {
                        Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("fdPwd", pwd1);
                        editor.commit();
                        //进入手机防盗主界面
                        enterSetting();
                        dialog.dismiss();
                    }
                }
            }
        });

        //取消输入的密码
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

    }

    //设置过防盗密码，确认密码
    private void checkPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        View view = View.inflate(HomeActivity.this, R.layout.check_password, null);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        final EditText et_checkPwd = (EditText) view.findViewById(R.id.et_checkPwd);
        Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);


        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = et_checkPwd.getText().toString().trim();
                final String savedPwd = sp.getString("fdPwd", null);

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (savedPwd.equals(password)) {
                        enterFindLocation();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * 进入手机防盗主界面
     */
    private void enterFindLocation() {
        //判断之前是否设置过防盗功能，如果设置过直接显示主界面，否则进入设置防盗流程
        if (sp == null) {
            setPassword();
        } else {
            String settingStr = sp.getString("fdPwd", null);
            if (!TextUtils.isEmpty(settingStr)) {
                Intent intent = new Intent(HomeActivity.this, FindLocationActivity.class);
                startActivity(intent);
                finish();
            } else {
                setPassword();
            }
        }
//        Intent intent = new Intent(this,FindLocationActivity.class);
//        startActivity(intent);

    }

    /**
     * 进入手机防盗设置流程
     */
    private void enterSetting() {
        Intent intent = new Intent(this, FindSetting1.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
