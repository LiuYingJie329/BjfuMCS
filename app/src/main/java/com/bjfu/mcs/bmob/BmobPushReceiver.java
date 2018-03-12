package com.bjfu.mcs.bmob;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bjfu.mcs.R;

import cn.bmob.push.PushConstants;


public class BmobPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Notify notify = new Notify(context);
            notify.setId(msg.hashCode());
            notify.setTitle(msg);
            notify.setAutoCancel(true);
            notify.setSmallIcon(R.mipmap.ic_launcher);
            notify.notification();
            notify.show();
        }
    }
}
