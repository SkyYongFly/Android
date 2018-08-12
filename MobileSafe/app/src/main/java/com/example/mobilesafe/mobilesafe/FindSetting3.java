package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class FindSetting3 extends SetBaseActivity {
    private EditText  et_safePHone;
    private String  safePhone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_setting3);

        et_safePHone  = (EditText) findViewById(R.id.et_safePhone);

    }

    /**
     * 将安全号码保存起来
     * @param safePhone
     */
    private void savePhoneNumToFile(String safePhone) {
        SharedPreferences.Editor  editor = sp.edit();
        editor.putString("safePhoneNum",safePhone);
        editor.commit();
    }


    /**
     * 选择联系人
     * @param v
     */
    public void selectContacts(View v){
        Intent intent = new Intent(this,SelectContacts.class);
        startActivityForResult(intent,0);//注意此处的写法，带着目的去开启活动，否则数据返回这里的活动将接受不到数据
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 0){
            String  returnPhone =  data.getStringExtra("phone");
            Log.d("test",returnPhone);
            et_safePHone.setText(returnPhone);
        }else{
            return;
        }


    }

    @Override
    public void showNext() {
        safePhone = et_safePHone.getText().toString().trim();
        savePhoneNumToFile(safePhone);

        Intent intent = new Intent(this,FindSetting4.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,FindSetting2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,FindSetting2.class);
        startActivity(intent);
        finish();
    }
}
