package com.bjfu.mcs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjfu.mcs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetPlaceActivity extends AppCompatActivity {

    @BindView(R.id.tv_latitude)
    TextView tv_latitude;
    @BindView(R.id.tv_longitude)
    TextView tv_longitude;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_starttime)
    TextView tv_starttime;
    @BindView(R.id.tv_lasttime)
    TextView tv_lasttime;
    @BindView(R.id.tv_alltime)
    TextView tv_alltime;
    @BindView(R.id.tv_setdis)
    TextView tv_setdis;

    private SetPlaceActivity mContext;

    private String latitude = null;
    private String longitude = null;
    private String name = null;
    private String address = null;
    private String time = null;
    private String starttime = null;
    private String lasttime = null;
    private String alltime = null;
    private String setdis = null;
    private String search = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_place);
        ButterKnife.bind(this);
        mContext = this;
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("当前位置详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            latitude = bundle.get("latitude").toString();
            longitude = bundle.get("longitude").toString();
            address = bundle.get("address").toString();
            name = bundle.get("name").toString();
            search = bundle.get("search").toString();
        }

        tv_latitude.setText(latitude);
        tv_longitude.setText(longitude);
        tv_address.setText(address);
        tv_name.setText(name);
    }
}
