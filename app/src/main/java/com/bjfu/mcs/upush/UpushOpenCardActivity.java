package com.bjfu.mcs.upush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bjfu.mcs.R;
import com.bjfu.mcs.utils.SerializableMap;
import com.umeng.message.entity.UMessage;

import java.io.Serializable;
import java.util.Set;

import butterknife.ButterKnife;

public class UpushOpenCardActivity extends AppCompatActivity {

    private UpushOpenCardActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upush_open);
        ButterKnife.bind(this);
        mContext = this;
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("系统设置");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        getDataFromUpush();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bun = getIntent().getExtras();

        if (bun != null) {

            Set<String> keySet = bun.keySet();
            for (String key : keySet) {
                String value = bun.getString(key);
                Log.i("接收参数",value);
            }

        }
    }

    @Override

    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);

    }

    //{"sessions":[{"id":"E7B09837584CE93D8E4E1F00ABFAD73E","start_time":"1528282863932","end_time":"1528282866151","duration":2219,
    // "pages":[{"page_name":"com.bjfu.mcs.upush.UpushActivity","duration":2222}]}],
    // "sdk_version":"7.5.0","device_id":"865372020318579","device_model":"MI 4LTE","version":1,"appkey":"5b0faa348f4a9d2fa2000036","channel":"Umeng"}

    //{"success":"ok","data":{"t":1528282864302,"check_num":"","sduration":1,"pduration":1000,
    // "card":{"msg_info":
    // {"plain_text":{"activity":"com.bjfu.mcs.activity.MainActivity",
    // "action_type":"go_activity",
    // "button_text":"确定",
    // "title":"qweqweqwe",
    // "content":"qeqweqweqweqw"},
    // "display_button":false},
    // "msg_id":"uat0a0c152825044859700",
    // "msg_type":5,
    // "policy":{"show_type":0,"show_times":0,"start_time":"2018-06-06 01:24:46","expire_time":"2018-06-07 01:24:46"}}}}
    private void getDataFromUpush() {
        try {

            String msgtitle = getIntent().getStringExtra("msgtitle");
            String msgtext = getIntent().getStringExtra("msgtext");
            String msgraw = getIntent().getStringExtra("msgraw");
            String msgmapextra = getIntent().getStringExtra("msgmapextra");
            String msgcustom = getIntent().getStringExtra("msgcustom");
            String msgticker = getIntent().getStringExtra("msgticker");

            //Log.i("接收参数",msgtitle+"-->"+msgtext+"-->"+msgraw+"-->"+msgmapextra+"-->"+msgcustom+"-->"+msgticker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
