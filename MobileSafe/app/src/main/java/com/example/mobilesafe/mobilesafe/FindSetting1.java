package com.example.mobilesafe.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FindSetting1 extends SetBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_setting1);

    }

    @Override
    public void showNext() {
        Intent intent = new Intent(FindSetting1.this,FindSetting2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPre() {

    }

}
