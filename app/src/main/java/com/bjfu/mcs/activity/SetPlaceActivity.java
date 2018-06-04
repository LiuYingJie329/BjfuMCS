package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.LocationInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    private int counts = 0;

    private static final int getlocationtimes = 1;

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

        getdata();

    }

    //获取在此地的信息
    private void getdata() {
        PersonInfo piInfo = BmobUser.getCurrentUser(PersonInfo.class);
        BmobQuery<LocationInfo> query = new BmobQuery<LocationInfo>();
        query.addWhereEqualTo("userId", piInfo.getUserId());    // 查询当前用户的所有位置信息
        query.findObjects(new FindListener<LocationInfo>() {
            @Override
            public void done(List<LocationInfo> locationlist, BmobException e) {
                if (e == null && locationlist != null && locationlist.size() > 0) {
                    Message msg = new Message();
                    msg.what = getlocationtimes;
                    msg.obj = locationlist;
                    mHandler.sendMessage(msg);
                } else {
                    Log.i("查询LocationInfo失败", e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case getlocationtimes:
                    List<LocationInfo> dataList = (List<LocationInfo>) msg.obj;
                    SortClass sort = new SortClass();
                    Collections.sort(dataList,sort);
                    for(int i = 0; i < dataList.size(); i++){
                        LocationInfo info = dataList.get(i);
                        String address= info.getLocationDescribe();
                        if(name.contains("北京林业大学")){
                            name.replace("北京林业大学","");
                        }
                        if(name.contains("北林")){
                            name.replace("北林","");
                        }
                        if(address.contains(name)){
                            counts++;
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_starttime.setText(dataList.get(0).getUpdatedAt()+"");
                            tv_lasttime.setText(dataList.get(dataList.size()-1).getUpdatedAt()+"");
                            tv_time.setText(counts+"");
                        }
                    });
                    break;
            }
        }
    };

    public static class SortClass implements Comparator {
        public int compare(Object arg0,Object arg1){
            LocationInfo location0 = (LocationInfo)arg0;
            LocationInfo location1 = (LocationInfo)arg1;
            int flag = location0.getUpdatedAt().compareTo(location1.getUpdatedAt());
            return flag;
        }
    }

}
