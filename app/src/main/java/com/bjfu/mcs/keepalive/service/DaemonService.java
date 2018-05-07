package com.bjfu.mcs.keepalive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bjfu.mcs.R;
import com.bjfu.mcs.application.MCSApplication;


/**
 * Created by ly on 2017/11/7.
 */

public class DaemonService extends Service{
    private static final String TAG = DaemonService.class.getSimpleName();
    public static final int NOTIFICATION_ID = 100;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("MCS实验系统");
            builder.setContentText("MCS实验系统");
            startForeground(NOTIFICATION_ID,builder.build());
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            Intent intent = new Intent(this,CancelNoticeService.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(intent);
        }else{
            startForeground(NOTIFICATION_ID,new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //如果Service被终止，当资源允许情况下，重启Service
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //如果service被杀死，干掉通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager notificationManager  =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(NOTIFICATION_ID);
        }

        //重启自己
        Intent intent =  new Intent(MCSApplication.getApplication(),DaemonService.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
