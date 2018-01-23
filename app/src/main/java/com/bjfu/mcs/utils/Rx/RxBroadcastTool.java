package com.bjfu.mcs.utils.Rx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;


/**
 * Created by Vondear on 2017/3/15.
 */

public class RxBroadcastTool {

    private static final String TAG = RxBroadcastTool.class.getSimpleName();
    private static boolean disnet = false;
    private long firsttime = 0;

    /**
     * 注册监听网络状态的广播
     *
     * @param context
     * @return
     */
    public static BroadcastReceiverNetWork initRegisterReceiverNetWork(Context context) {
        // 注册监听网络状态的服务
        BroadcastReceiverNetWork mReceiverNetWork = new BroadcastReceiverNetWork();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mReceiverNetWork, mFilter);
        return mReceiverNetWork;
    }

    /**
     * 网络状态改变广播
     */
    public static class BroadcastReceiverNetWork extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int isnet = RxNetTool.getNetWorkType(context);
//            boolean isdetails = RxActivityTool.currentActivity().getClass().getName().toString().contains("StoreDetailsActivity");
//            if ((isnet == -1) && isdetails) {
//                startPlayService(PlayService.EXIT_ACTION);
//                new android.os.Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //LogUtils.file(LogUtils.I,"StoreDetailsActivity------->网络断开,返回微信登录界面,重新登录");
//                        CurrentStatus.getInstance().setCurrentStatus(CurrentStatus.STATUS_LOGIN);
//                        Intent intent2 = new Intent(context, WXEntryActivity.class);
//                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent2);
//                        RxActivityTool.finishAllActivity();
//                    }
//                }, 3000);
//
//            }
        }
    }

//    public static void startPlayService(String action) {
//        Intent serviceIntent = new Intent();
//        serviceIntent.setClass(WinApplication.getWinApplication(), PlayService.class);
//        serviceIntent.putExtra(Constants.STORE_CODE, "");
//        serviceIntent.putExtra(Constants.SQL_USERID, 0);
//        serviceIntent.setAction(action);
//        WinApplication.getWinApplication().startService(serviceIntent);
//    }
}
