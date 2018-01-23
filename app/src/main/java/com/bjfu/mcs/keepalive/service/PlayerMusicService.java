package com.bjfu.mcs.keepalive.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bjfu.mcs.R;
import com.bjfu.mcs.application.MCSApplication;


/**
 * 循环播放一段无声音频，以便提升进程优先级
 * Created by ly on 2017/11/7.
 */

public class PlayerMusicService extends Service {
    private static final String TAG = PlayerMusicService.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayer = MediaPlayer.create(MCSApplication.getApplication(), R.raw.silent);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startPlayMusic();
            }
        }).start();
        return START_STICKY;
    }

    private void startPlayMusic() {
        if(mMediaPlayer != null){

            mMediaPlayer.start();
        }
    }
    private void stopPlayMusic(){
        if(mMediaPlayer != null){

            mMediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayMusic();

        //重启自己
        Intent intent = new Intent(MCSApplication.getApplication(),PlayerMusicService.class);
        startActivity(intent);
    }
}
