package com.bjfu.mcs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjfu.mcs.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdditionActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_cause)
    TextView tv_cause;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_type_other)
    TextView tv_type_other;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_address_other)
    TextView tv_address_other;
    @BindView(R.id.tv_starttime)
    TextView tv_starttime;
    @BindView(R.id.tv_endtime)
    TextView tv_endtime;
    @BindView(R.id.tv_alltime)
    TextView tv_alltime;
    @BindView(R.id.tv_other)
    TextView tv_other;
    private AdditionActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        ButterKnife.bind(this);
        mContext = this;

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("活动添加");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @OnClick({R.id.tv_name,R.id.tv_cause,R.id.tv_type,R.id.tv_type_other,R.id.tv_address,
            R.id.tv_address_other,R.id.tv_starttime,R.id.tv_endtime,R.id.tv_alltime,R.id.tv_other,})
    public void onClick(View view){
        switch (view.getId()){

            default:
                break;
        }
    }
}
