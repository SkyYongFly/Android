package com.example.zy.bluetoothcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket  bluetoothSocket = null;
    private OutputStream     outputStream = null;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "98:D3:31:20:67:7F"; // 蓝牙设备MAC地址

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "蓝牙设备不可用，请打开蓝牙！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "请打开蓝牙并重新运行程序！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            DisplayToast("正在尝试连接蓝牙设备，请稍后····");
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothAdapter.cancelDiscovery();
            bluetoothSocket.connect();
            DisplayToast("成功连接蓝牙设备，可以开始操控");
        } catch (IOException e) {
            e.printStackTrace();
            DisplayToast("连接失败");

            try {
                bluetoothSocket.close();
            } catch (IOException e2) {
                DisplayToast("连接没有建立，无法关闭套接字！");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            if (outputStream != null) {
                    outputStream.flush();
            }
            bluetoothSocket.close();
        } catch (IOException e) {
            DisplayToast("套接字关闭失败！");
        }
    }

    public void go(View v){
        sendCmd("A");
        Log.d("action", "前进");
    }

    public void back(View v){
        sendCmd("B");
        Log.d("action","后退");
    }

    public void left(View v){
        sendCmd("C");
        Log.d("action", "左转");
    }

    public void right(View v){
        sendCmd("D");
        Log.d("action", "右转");
    }

    public void stop(View v){
        sendCmd("E");
        Log.d("action", "停止");
    }

    //显示提示信息
    public void DisplayToast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 220);
        toast.show();

    }

    //发送数据
    public void sendCmd(String message){
        try {
            outputStream = bluetoothSocket.getOutputStream();
            byte[] msgBuffer = message.getBytes();
            outputStream.write(msgBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
