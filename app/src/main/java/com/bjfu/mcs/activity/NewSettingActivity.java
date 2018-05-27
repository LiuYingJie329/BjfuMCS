package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.loginSign.LoginActivity;
import com.bjfu.mcs.map.OfflineDemo;
import com.bjfu.mcs.utils.DataCleanManager;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.other.ConvertUtils;
import com.bjfu.mcs.utils.picker.OptionPicker;
import com.bjfu.mcs.utils.picker.TimePicker;
import com.bjfu.mcs.utils.picker.WheelView;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.bjfu.mcs.application.MCSApplication.appcache;
import static com.bjfu.mcs.utils.Rx.RxToast.showToast;

public class NewSettingActivity extends AppCompatActivity {

    @BindView(R.id.tv_home)
    TextView mTvhome;
    @BindView(R.id.tv_school)
    TextView mTvschool;
    @BindView(R.id.tv_company)
    TextView mTvcompany;
    @BindView(R.id.tv_map)
    TextView mTvmap;
    @BindView(R.id.tv_clean)
    TextView mTvclean;
    @BindView(R.id.tv_notification)
    TextView mTvnotification;
    @BindView(R.id.tv_pushstart)
    TextView mTvpushstart;
    @BindView(R.id.tv_pushstop)
    TextView mTvpushstop;
    @BindView(R.id.tv_pushcool)
    TextView mTvpushcool;
    @BindView(R.id.tv_appversion)
    TextView mTvappversion;
    @BindView(R.id.ll_switch)
    LinearLayout mLLswitch;
    @BindView(R.id.ll_exit)
    LinearLayout mLLexit;
    private NewSettingActivity mContext;

    private static final int updatehome =1;
    private static final int updateschool =2;
    private static final int updatecompany =3;
    private static final int updatemap =4;
    private static final int updateclean =5;
    private static final int updatenotification =6;
    private static final int updatepushstart =7;
    private static final int updatepushstop =8;
    private static final int updatepushcool =9;
    private static final int updateappversion =10;
    private static final int updateswitch =11;
    private static final int updateexit =12;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case updatehome:
                    String home = (String) msg.obj;
                    PersonInfo personInfohome = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfohome.setUserHome(home);
                    personInfohome.update(personInfohome.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserHome(home);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvhome.setText(home);
                                    }
                                });
                                RxToast.success("信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(NewSettingActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateschool:
                    String school = (String) msg.obj;
                    PersonInfo personInfoschool = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoschool.setUserSchool(school);
                    personInfoschool.update(personInfoschool.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserSchool(school);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvschool.setText(school);
                                    }
                                });
                                RxToast.success("信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(NewSettingActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updatecompany:
                    String company = (String) msg.obj;
                    PersonInfo personInfocompany = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfocompany.setUserCompany(company);
                    personInfocompany.update(personInfocompany.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserCompany(company);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvcompany.setText(company);
                                    }
                                });
                                RxToast.success("信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(NewSettingActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updatemap:
                    String content = (String) msg.obj;
                    if(content.equals("POSITIVE")){
                        RxToast.info("开始下载");
                        RxActivityTool.skipActivityAndFinish(NewSettingActivity.this, OfflineDemo.class);
                    }
                    if(content.equals("NEGATIVE")){
                        RxToast.info("取消下载");
                    }
                    if(content.equals("NEUTRAL")){

                    }
                    break;
                case updateclean:
                    String contentclean = (String) msg.obj;
                    if(contentclean.equals("POSITIVE")){
                        RxToast.info("开始清除");
                        //DataCleanManager.cleanInternalCache(getApplicationContext());
                        //appcache.clear();
                        RxToast.info("清除完成");
                        mTvclean.setText("0KB");
                        //initcache();
                    }
                    if(contentclean.equals("NEGATIVE")){
                        RxToast.info("取消");
                    }
                    if(contentclean.equals("NEUTRAL")){

                    }
                    break;
                case updatenotification:
                    break;
                case updatepushstart:
                    break;
                case updatepushstop:
                    break;
                case updatepushcool:
                    break;
                case updateappversion:
                    break;
                case updateswitch:
                    break;
                case updateexit:
                    break;
                default:
                    break;
            }
        }
    };
    private void updatePersonInfoMsg(int type,String content){
        Message msg = new Message();
        msg.what = type;
        msg.obj =  content;
        mHandler.sendMessage(msg);
    }

    @OnClick({R.id.tv_home,R.id.tv_school,R.id.tv_company,R.id.tv_map,R.id.tv_clean,R.id.tv_notification,
    R.id.tv_pushstart,R.id.tv_pushstop,R.id.tv_pushcool,R.id.tv_appversion,R.id.ll_switch,R.id.ll_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                new MaterialDialog.Builder(this)
                        .title("家用地址")
                        .content("修改家用地址")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 30)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "北京林业大学",
                                "北京林业大学",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updatehome,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_school:
                new MaterialDialog.Builder(this)
                        .title("学校地址")
                        .content("修改学校地址")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 30)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "北京林业大学",
                                "北京林业大学",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updateschool,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_company:
                new MaterialDialog.Builder(this)
                        .title("公司地址")
                        .content("修改公司地址")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 30)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "北京林业大学",
                                "北京林业大学",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updatecompany,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_map:
                new MaterialDialog.Builder(this)
                        .title("离线地图")
                        .content(R.string.downloadmap, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .neutralText(R.string.ignore)
                        .onAny((dialog, which) -> updatePersonInfoMsg(updatemap,which.name()))
                        .show();
                break;
            case R.id.tv_clean:
                new MaterialDialog.Builder(this)
                        .title("清除缓存")
                        .content(R.string.cleancache, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .neutralText(R.string.ignore)
                        .onAny((dialog, which) -> updatePersonInfoMsg(updateclean,which.name()))
                        .show();
                break;
            case R.id.tv_notification:
                OptionPicker sexpicker = new OptionPicker(this, new String[]{
                        "打开", "关闭"
                });
                sexpicker.setCanceledOnTouchOutside(false);
                sexpicker.setDividerRatio(WheelView.DividerConfig.FILL);
                sexpicker.setShadowColor(Color.WHITE, 40);
                sexpicker.setTextColor(Color.BLACK);
                sexpicker.setSelectedIndex(1);
                sexpicker.setSubmitTextSize(16);
                sexpicker.setCancelTextSize(16);
                sexpicker.setCycleDisable(true);
                sexpicker.setCancelTextColor(getResources().getColor(R.color.pickercancel));
                sexpicker.setSubmitTextColor(getResources().getColor(R.color.pickersure));
                sexpicker.setDividerColor(getResources().getColor(R.color.pickerdivid));
                sexpicker.setTopLineColor(getResources().getColor(R.color.pickerdivid));
                sexpicker.setTextSize(18);
                sexpicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        //showToast("index=" + index + ", item=" + item);
                        updatePersonInfoMsg(updatenotification,item);
                    }
                });
                sexpicker.show();
                break;
            case R.id.tv_pushstart:
                TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
                picker.setUseWeight(false);
                picker.setCycleDisable(false);
                picker.setRangeStart(0, 0);//00:00
                picker.setRangeEnd(23, 59);//23:59
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                picker.setSelectedItem(currentHour, currentMinute);
                picker.setTopLineVisible(false);
                picker.setTextPadding(ConvertUtils.toPx(this, 15));
                picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        updatePersonInfoMsg(updatepushstart,hour + ":" + minute);
                    }
                });
                picker.show();
                break;
            case R.id.tv_pushstop:
                TimePicker picker2 = new TimePicker(this, TimePicker.HOUR_24);
                picker2.setUseWeight(false);
                picker2.setCycleDisable(false);
                picker2.setRangeStart(0, 0);//00:00
                picker2.setRangeEnd(23, 59);//23:59
                int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int curMinute = Calendar.getInstance().get(Calendar.MINUTE);
                picker2.setSelectedItem(curHour, curMinute);
                picker2.setTopLineVisible(false);
                picker2.setTextPadding(ConvertUtils.toPx(this, 15));
                picker2.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        updatePersonInfoMsg(updatepushstop,hour + ":" + minute);
                    }
                });
                picker2.show();
                break;
            case R.id.tv_pushcool:
                OptionPicker picker3 = new OptionPicker(this, new String[]{
                        "1分钟", "2分钟", "3分钟","4分钟","5分钟"
                });
                picker3.setCanceledOnTouchOutside(false);
                picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                picker3.setShadowColor(Color.WHITE, 40);
                picker3.setTextColor(Color.BLACK);
                picker3.setSelectedIndex(1);
                picker3.setSubmitTextSize(16);
                picker3.setCancelTextSize(16);
                picker3.setCycleDisable(true);
                picker3.setCancelTextColor(getResources().getColor(R.color.pickercancel));
                picker3.setSubmitTextColor(getResources().getColor(R.color.pickersure));
                picker3.setDividerColor(getResources().getColor(R.color.pickerdivid));
                picker3.setTopLineColor(getResources().getColor(R.color.pickerdivid));
                picker3.setTextSize(18);
                picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        updatePersonInfoMsg(updatepushcool,item);
                    }
                });
                picker3.show();
                break;
            case R.id.tv_appversion:
                break;
            case R.id.ll_switch:
                break;
            case R.id.ll_exit:
                OptionPicker picker4 = new OptionPicker(this, new String[]{
                        "退出登录", "关闭应用"
                });
                picker4.setCanceledOnTouchOutside(false);
                picker4.setDividerRatio(WheelView.DividerConfig.FILL);
                picker4.setShadowColor(Color.WHITE, 40);
                picker4.setTextColor(Color.BLACK);
                picker4.setSelectedIndex(1);
                picker4.setSubmitTextSize(16);
                picker4.setCancelTextSize(16);
                picker4.setCycleDisable(true);
                picker4.setCancelTextColor(getResources().getColor(R.color.pickercancel));
                picker4.setSubmitTextColor(getResources().getColor(R.color.pickersure));
                picker4.setDividerColor(getResources().getColor(R.color.pickerdivid));
                picker4.setTopLineColor(getResources().getColor(R.color.pickerdivid));
                picker4.setTextSize(18);
                picker4.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        updatePersonInfoMsg(updateexit,item);
                    }
                });
                picker4.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_setting);
        ButterKnife.bind(this);
        mContext = this;
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
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

        initdata();
    }

    private void initdata() {
        PersonInfo personInfo = DataBaseHandler.getCurrPesonInfo();
        if (null != personInfo) {
            if(!RxDataTool.isNullString(personInfo.getUserHome())){
                mTvhome.setText(personInfo.getUserHome());
            }
            if(!RxDataTool.isNullString(personInfo.getUserSchool())){
                mTvschool.setText(personInfo.getUserSchool());
            }
            if(!RxDataTool.isNullString(personInfo.getUserCompany())){
                mTvcompany.setText(personInfo.getUserCompany());
            }
        }




        initcache();

    }

    private void initcache() {
        File file =new File(mContext.getCacheDir().getPath());
        try {
            mTvclean.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
