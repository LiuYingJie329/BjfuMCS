package com.bjfu.mcs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bjfu.mcs.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.PushListener;

public class SetPushActivity extends AppCompatActivity {

    @BindView(R.id.btn_broadcast)
    Button btn_broadcast;
    @BindView(R.id.btn_multi_cast_channel)
    Button btn_multi_cast_channel;
    @BindView(R.id.btn_multi_cast_platform)
    Button btn_multi_cast_platform;
    @BindView(R.id.btn_multi_cast_location)
    Button btn_multi_cast_location;
    @BindView(R.id.btn_multi_cast_active)
    Button btn_multi_cast_active;
    @BindView(R.id.btn_multi_cast_query)
    Button btn_multi_cast_query;
    @BindView(R.id.btn_uni_cast_android)
    Button btn_uni_cast_android;
    @BindView(R.id.btn_uni_cast_ios)
    Button btn_uni_cast_ios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_push);
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
    }

    @OnClick({R.id.btn_broadcast, R.id.btn_multi_cast_channel, R.id.btn_multi_cast_platform,
            R.id.btn_multi_cast_location, R.id.btn_multi_cast_active, R.id.btn_multi_cast_query,
            R.id.btn_uni_cast_android, R.id.btn_uni_cast_ios})
    public void onViewClicked(View view) {
        BmobPushManager bmobPushManager = new BmobPushManager();
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        switch (view.getId()) {
            case R.id.btn_broadcast://OK
                bmobPushManager.pushMessageAll("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_channel:
                List<String> channels = new ArrayList<>();
                //TODO 替换成你需要推送的所有频道，推送前请确认已有设备订阅了该频道，也就是channels属性存在该值
                channels.add("NBA");
                bmobPushManager.setQuery(query);
                query.addWhereContainedIn("channels", channels);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_platform://OK
                //TODO 属性值为android或者ios
                query.addWhereEqualTo("deviceType", "android");
                //query.addWhereEqualTo("deviceType", "ios");
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_location://OK
                //TODO 替换你需要推送的地理位置的经纬度和范围，发送前请确认installation表中已有location的BmobGeoPoint类型属性
                query.addWhereWithinRadians("location", new BmobGeoPoint(113.385610000, 23.0561000000), 1.0);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage()+"发送前请确认installation表中已有location的BmobGeoPoint类型属性",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_active://OK
                //TODO 替换你需要的判断为不活跃的时间点
                query.addWhereLessThan("updatedAt", new BmobDate(new Date()));
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_query://OK
                //TODO 替换成你作为判断需要推送的属性名和属性值，推送前请确认installation表已有该属性
                query.addWhereEqualTo("替换成你作为判断需要推送的属性名", "替换成你作为判断需要推送的属性值");
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_uni_cast_android://OK
                //TODO 替换成所需要推送的Android客户端installationId
                String installationId = "替换成所需要推送的Android客户端installationId";
                query.addWhereEqualTo("installationId", installationId);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.btn_uni_cast_ios://OK
                //TODO 替换成所需要推送的iOS客户端deviceToken
                String deviceToken = "";
                query.addWhereEqualTo("deviceToken", deviceToken);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SetPushActivity.this,"推送成功",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SetPushActivity.this,"推送异常"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }
}
