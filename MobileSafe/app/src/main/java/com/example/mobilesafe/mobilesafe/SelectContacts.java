package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daomain.Phone;

import java.util.ArrayList;
import java.util.List;

public class SelectContacts extends Activity {

    private List<Phone> listPhone;
    private ListView lv_phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);

        lv_phoneNum = (ListView) findViewById(R.id.lv_phoneNum);
        //获取手机联系人
        listPhone = getContacts();

        lv_phoneNum.setAdapter(new MyAdapter());
        lv_phoneNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phone phone = listPhone.get(position);
                Intent data = new Intent();
                data.putExtra("phone", phone.getPhoneNum());
                setResult(0, data);
                finish();
            }
        });

    }

    public List<Phone> getContacts() {
        List<Phone> list = new ArrayList<Phone>();
        //查询raw_contacts表的URI
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        //查询data表的URI
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        ContentResolver resolver = getContentResolver();
        //取出raw_contacts表中的所有id，这些id作为data表中的识别不同用户的标志
        Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);//因为查询的数据只有 _id 一项，所以只有索引  0

                //根据id去Data表中查询数据
                Cursor dataCursor = resolver.query(dataUri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{id + ""}, null);
                if (dataCursor != null && dataCursor.getCount() > 0) {
                    Phone phone = new Phone();

                    while (dataCursor.moveToNext()) {
                        //获取数据内容，此内容包括手机号码、姓名和邮箱，可以通过 mimetype的类型判断到底为那种值
                        String data1 = dataCursor.getString(0);
                        //获取mime类型值
                        String mimetype = dataCursor.getString(1);


                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            Log.d("contacts", "手机号码是 : " + data1);
                            phone.setPhoneNum(data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            Log.d("contacts", "姓名 ： " + data1);
                            phone.setContact(data1);
                        }
                    }
                    list.add(phone);
                }
                dataCursor.close();
            }
        }
        cursor.close();

        return list;
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listPhone.size();
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
            View view = null;

            if (convertView != null) {
                view = convertView;
            } else {
                LayoutInflater inflater = SelectContacts.this.getLayoutInflater();
                view = inflater.inflate(R.layout.phonenumitem, null);
            }

            TextView tv_showPhone = (TextView) view.findViewById(R.id.tv_showPhone);
            TextView tv_showContact = (TextView) view.findViewById(R.id.tv_showContact);

            Phone phone = listPhone.get(position);
            tv_showContact.setText(phone.getContact());
            tv_showPhone.setText(phone.getPhoneNum());

            return view;
        }
    }

}
