package com.example.zy.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button on,off,visible,list;
    private ListView listView1;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        on = (Button) findViewById(R.id.button1);
        visible = (Button) findViewById(R.id.button2);
        list = (Button) findViewById(R.id.button3);
        off = (Button) findViewById(R.id.button4);

        listView1  = (ListView) findViewById(R.id.listView1);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //开启蓝牙
    public void on(View v){
        //如果没有开启蓝牙
        if(!bluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn,0);
            Toast.makeText(MainActivity.this, "蓝牙正在开启", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
        }
    }

    //设备列表
    public void list(View v){
        pairedDevices = bluetoothAdapter.getBondedDevices();

        ArrayList<String> list = new ArrayList<String>();
        for(BluetoothDevice  d : pairedDevices){
            list.add(d.getName());
        }

        Toast.makeText(MainActivity.this, "显示设备列表", Toast.LENGTH_SHORT).show();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView1.setAdapter(adapter);
    }

    //关闭蓝牙
    public void off(View v){
        bluetoothAdapter.disable();
        Toast.makeText(MainActivity.this, "关闭蓝牙", Toast.LENGTH_SHORT).show();
    }

    //蓝牙可见
    public void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible,0);
    }
}
