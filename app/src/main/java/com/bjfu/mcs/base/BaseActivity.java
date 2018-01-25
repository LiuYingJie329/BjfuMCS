package com.bjfu.mcs.base;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bjfu.mcs.R;

import butterknife.ButterKnife;

/**
 * Created by ly on 2018/1/17.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutId());
        setTransluentStatusBar();
        ButterKnife.bind(this);
    }

    void setTransluentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            getWindow().setAttributes(localLayoutParams);
        }
    }

    protected abstract int layoutId();
}
