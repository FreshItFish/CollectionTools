package com.zxtc.collectiontools.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 *  定时更新之--pull的推送实现
 */

public class PushSmsService extends Service {

    private boolean flag = true;
    private AlarmManager mAlarmMgr;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mAlarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);

        new MyThread().start();

        super.onCreate();
    }

    private class MyThread extends Thread{
        @Override
        public void run() {
            String url = "";
            while (flag){
                try {
                    //每 10 秒发送一次请求
                    Thread.sleep(10000);
                    //TODO 发送get请求

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            super.run();
        }
    }

    public void startRequestAlarm(){

        cancelRequestAlarm();

        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()+ 1000,5*1000,getOperationIntent());
    }

    private void cancelRequestAlarm() {
        mAlarmMgr.cancel(getOperationIntent());
    }

    public PendingIntent getOperationIntent() {
        Intent intent = new Intent(this,PushSmsService.class);
        intent.setAction("aaa");
        PendingIntent operation = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return operation;
    }
}
