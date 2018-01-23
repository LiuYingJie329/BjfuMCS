package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bjfu.mcs.R;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.utils.Constants;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.other.DateUtil;

public class MainActivity extends BaseActivity {

    public static Handler mHandler = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.upLoad:
                        RxToast.success("定位成功");
                        break;
                    default:
                        break;
                }

            }
        };
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
}
