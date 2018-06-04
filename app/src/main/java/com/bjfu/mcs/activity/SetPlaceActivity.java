package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.LocationInfo;
import com.bjfu.mcs.greendao.UserPlaceTimeInfo;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.picker.DateTimePicker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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
    private static final int time = 1;
    private static final int starttime = 2;
    private static final int lasttime = 3;
    private static final int alltime = 4;
    private static final int setdis = 5;
    private String search = null;
    private int counts = 0;

    private static final int getlocationtimes = 6;
    private String curr_obiectid = null;
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
                    initSetPlace();
                    break;
                case time:
                    String sub_time = (String)msg.obj;
                    break;
                case starttime:
                    String sub_starttime = (String)msg.obj;
                    break;
                case lasttime:
                    String sub_lasttime = (String)msg.obj;
                    break;
                case alltime:
                    String sub_alltime = (String)msg.obj;
                    break;
                case setdis:
                    String sub_setdis = (String)msg.obj;
                    break;
            }
        }
    };

    @OnClick({R.id.tv_time,R.id.tv_starttime,R.id.tv_lasttime,R.id.tv_alltime,R.id.tv_setdis})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_time:
                new MaterialDialog.Builder(this)
                    .title("次数")
                    .content("请填写在此地的次数")
                    .inputType(
                            InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                    | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                    .inputRange(0, 3)
                    .positiveText("提交")
                    .negativeText("取消")
                    .input(
                            "0",
                            "0",
                            false,
                            (dialog, input) -> updateAdditionInfoMsg(time, input.toString()))
                    .checkBoxPromptRes(R.string.extra3, true, null)
                    .show();
                break;
            case R.id.tv_starttime:
                DateTimePicker startpicker = new DateTimePicker(this, DateTimePicker.HOUR_24);
                startpicker.setDateRangeStart(2017, 1, 1);
                startpicker.setDateRangeEnd(2018, 11, 11);
                startpicker.setTimeRangeStart(9, 0);
                startpicker.setTimeRangeEnd(20, 30);
                startpicker.setTopLineColor(0x99FF0000);
                startpicker.setLabelTextColor(0xFFFF0000);
                startpicker.setDividerColor(0xFFFF0000);
                startpicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        updateAdditionInfoMsg(starttime, year + "-" + month + "-" + day + " " + hour + ":" + minute);
                    }
                });
                startpicker.show();
                break;
            case R.id.tv_lasttime:
                DateTimePicker endpicker = new DateTimePicker(this, DateTimePicker.HOUR_24);
                endpicker.setDateRangeStart(2017, 1, 1);
                endpicker.setDateRangeEnd(2018, 11, 11);
                endpicker.setTimeRangeStart(9, 0);
                endpicker.setTimeRangeEnd(20, 30);
                endpicker.setTopLineColor(0x99FF0000);
                endpicker.setLabelTextColor(0xFFFF0000);
                endpicker.setDividerColor(0xFFFF0000);
                endpicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                        updateAdditionInfoMsg(lasttime, year + "-" + month + "-" + day + " " + hour + ":" + minute);
                    }
                });
                endpicker.show();
                break;
            case R.id.tv_alltime:
                new MaterialDialog.Builder(this)
                        .title("时间(单位:分钟)")
                        .content("请填写在此地的总时间")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(0, 5)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "0",
                                "0",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(alltime, input.toString()))
                        .checkBoxPromptRes(R.string.extra5, true, null)
                        .show();
                break;
            case R.id.tv_setdis:
                new MaterialDialog.Builder(this)
                        .title("常去地")
                        .content(R.string.oftenaddress, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .neutralText(R.string.ignore)
                        .onAny((dialog, which) -> updateAdditionInfoMsg(setdis, which.name()))
                        .show();
                break;
            default:
                break;
        }
    }
    private void initSetPlace(){
        PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);
        UserPlaceTimeInfo userPlaceTimeInfo = new UserPlaceTimeInfo();
        userPlaceTimeInfo.personInfo = user;
        userPlaceTimeInfo.setAddress(address);
        userPlaceTimeInfo.setName(name);
        userPlaceTimeInfo.setLatitude(latitude);
        userPlaceTimeInfo.setLongitude(longitude);
        userPlaceTimeInfo.setSearch(search);
        userPlaceTimeInfo.setSearch(time+"");
        userPlaceTimeInfo.setPersonid(user.getUserId());
        userPlaceTimeInfo.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e == null){
                    RxToast.success("添加个人地点设置信息成功");
                    curr_obiectid = objectId;
                    Log.i("--------------->","个人推送情况成功");
                }else{
                    RxToast.success("添加个人地点设置信息失败");
                    Log.i("--------------->","个人推送情况失败");
                }
            }
        });
    }
    private void updateAdditionInfoMsg(int type, String content) {
        Message msg = new Message();
        msg.what = type;
        msg.obj = content;
        mHandler.sendMessage(msg);
    }

    public static class SortClass implements Comparator {
        public int compare(Object arg0,Object arg1){
            LocationInfo location0 = (LocationInfo)arg0;
            LocationInfo location1 = (LocationInfo)arg1;
            int flag = location0.getUpdatedAt().compareTo(location1.getUpdatedAt());
            return flag;
        }
    }

}
