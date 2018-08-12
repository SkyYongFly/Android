package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.example.service.GPSService;
import com.example.mobilesafe.mobilesafe.R;

import org.w3c.dom.Text;

/**
 * 短信内容广播接收着，当安全号码发过来相应的指令，应用将根据指令内容完成相应的功能
 */
public class SmsReceiver extends BroadcastReceiver {

    private SharedPreferences sp;
    private  String safePhone ;

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences(context.getResources().getString(R.string.applicationSaveFile), Context.MODE_PRIVATE);
        safePhone = sp.getString("safePhoneNum", null);
        if(TextUtils.isEmpty(safePhone)){

        }else {
            //获取发送过来的短信
            Object[] obj = (Object[]) intent.getExtras().get("pdus");

            for (Object j : obj) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) j);//注意此处写法

                //发送人
                String sender = message.getOriginatingAddress();
                //短信内容
                String messageBody = message.getMessageBody();
                Log.d("test", sender + "  " + messageBody + "  " + safePhone);
                if (sender.contains(safePhone)) {
                    if ("#*location*#".equals(messageBody)) {
                        //发送定位信息
                        sendGPSMessage(context);
                        //拦截短信
                        abortBroadcast();
                    } else if ("#*alarm*#".equals(messageBody)) {
                        //播放报警音乐
                        musicPlayer(context);
                        //拦截短信
                        abortBroadcast();

                    } else if ("#*wipedata*#".equals(messageBody)) {
                        //远程删除数据

                        //拦截短信
                        abortBroadcast();
                    } else if ("#*lockscreen*#".equals(messageBody)) {
                        //远程锁屏

                        //拦截短信
                        abortBroadcast();
                    }
                }

            }
        }
    }

    /**
     * 播放报警音乐
     * @param context
     */
    private void musicPlayer(Context context) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.ylzs);//获取音乐播放器
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//当前播放的是音乐
                mediaPlayer.start();   //开始播放
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    /**
     * 发送手机的当前GPS信息
     */
    private void sendGPSMessage(Context context) {
        //启动另一个服务来获取位置信息并且写入配置文件
        Intent intent = new Intent(context,GPSService.class);
        context.startService(intent);
        //获取位置信息
        String location = sp.getString("lcoation",null);
        if(TextUtils.isEmpty(location)){
            Log.d("test","位置为空");
            SmsManager.getDefault().sendTextMessage(safePhone, null, "location is null", null, null);

        }else{
            //发送位置短信到安全号码
            SmsManager.getDefault().sendTextMessage(safePhone,null,location,null,null);

        }
    }
}
