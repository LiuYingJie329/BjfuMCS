package com.bjfu.mcs.keepalive.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bjfu.mcs.activity.MainActivity;
import com.bjfu.mcs.application.MCSApplication;


/**
 * Created by ly on 2017/11/7.
 */
@TargetApi(21)
public class AliveJobService extends JobService{

    private static final String TAG = AliveJobService.class.getSimpleName();

    private volatile static Service mKeepAliveService = null;

    public static boolean isJobServiceAlive(){
        return mKeepAliveService!=null;
    }

    private static final int Meaage_id_Task = 0x01;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

                Intent intent  = new Intent(MCSApplication.getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //Log.i(TAG,"APP被杀死，重启中.......");

            //通知系统任务执行结束
            jobFinished((JobParameters)msg.obj,false);
            return true;
        }
    });
    @Override
    public boolean onStartJob(JobParameters params) {

        mKeepAliveService = this;
        // 返回false，系统假设这个方法返回时任务已经执行完毕；
        // 返回true，系统假定这个任务正要被执行
        Message msg = Message.obtain(mHandler,Meaage_id_Task,params);
        mHandler.sendMessage(msg);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mHandler.removeMessages(Meaage_id_Task);

        return false;
    }
}
