package com.bjfu.mcs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjfu.mcs.R;
import com.bjfu.mcs.base.BaseActivity;
import com.tencent.bugly.crashreport.CrashReport;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView tvtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("基本设置");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         *地图坐标
         * 116.346639 , 40.006374
         * 116.346639 , 40.006374
         * 116.346553 , 40.009707
         * 116.348121 , 40.013282
         * 116.349679 , 40.013241
         * 116.349908 , 40.009013
         * 116.352743 , 40.00912
         * 116.352594 , 40.013389
         * 116.355487 , 40.01341
         * 116.355711 , 40.009431
         * 116.355658 , 40.008122
         * 116.355658 , 40.006595
         */


    }

    @OnClick({R.id.tv_text})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_text:
                break;
            default:
                break;
        }
    }

}
