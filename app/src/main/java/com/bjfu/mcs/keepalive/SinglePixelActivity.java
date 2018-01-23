package com.bjfu.mcs.keepalive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bjfu.mcs.activity.MainActivity;
import com.bjfu.mcs.keepalive.receiver.KeepAliveReceiver;
import com.bjfu.mcs.keepalive.receiver.ScreenManager;
import com.bjfu.mcs.utils.Constants;


/**
 * 1像素Activity
 * Created by ly on 2017/11/7.
 */

public class SinglePixelActivity extends AppCompatActivity {
    private static final String TAG = SinglePixelActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.height = 300;
        layoutParams.width = 300;
        mWindow.setAttributes(layoutParams);
        //绑定1像素Activity到ScreeanManagerUtil
        ScreenManager.getScreenManagerInstance(this).setSingleActivity(this);
    }

    @Override
    protected void onDestroy() {

        if(!KeepAliveReceiver.isApplive(this, Constants.PACKAGE_NAME)){
            Intent intentAlive = new Intent(this, MainActivity.class);
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            //Log.i(TAG,"SinglePixelActivity--->App被干掉，我要重启它");
        }
        super.onDestroy();
    }
}
