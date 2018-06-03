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
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.AdditionInfo;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.other.ConvertUtils;
import com.bjfu.mcs.utils.picker.DateTimePicker;
import com.bjfu.mcs.utils.picker.TimePicker;
import com.bjfu.mcs.utils.widget.BaseProgressDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.bjfu.mcs.utils.Rx.RxToast.showToast;

public class AdditionActivity extends AppCompatActivity {

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
    @BindView(R.id.submit)
    Button submit;

    private AdditionActivity mContext;

    private static final int cause = 1;
    private static final int type = 2;
    private static final int type_other = 3;
    private static final int address = 4;
    private static final int address_other = 5;
    private static final int starttime = 6;
    private static final int endtime = 7;
    private static final int alltime = 8;
    private static final int other = 9;

    private BaseProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        ButterKnife.bind(this);
        mContext = this;

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    @OnClick({R.id.tv_cause, R.id.tv_type, R.id.tv_type_other, R.id.tv_address,
            R.id.tv_address_other, R.id.tv_starttime, R.id.tv_endtime, R.id.tv_alltime, R.id.tv_other,
            R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submitcontent();
                break;
            case R.id.tv_cause:
                new MaterialDialog.Builder(this)
                        .title("原因")
                        .content("请填写原因")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "暂无",
                                "暂无",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(cause, input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_type:
                new MaterialDialog.Builder(this)
                        .title("活动类型")
                        .content("请填写活动类型")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "暂无",
                                "暂无",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(type, input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
            case R.id.tv_type_other:
                new MaterialDialog.Builder(this)
                        .title("活动类型补充")
                        .content("请填写活动类型补充")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "暂无",
                                "暂无",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(type_other, input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_address:
                new MaterialDialog.Builder(this)
                        .title("活动地点")
                        .content("请填写活动地点")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "暂无",
                                "暂无",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(address, input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_address_other:
                new MaterialDialog.Builder(this)
                        .title("活动地点补充")
                        .content("请填写活动地点补充")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "暂无",
                                "暂无",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(address_other, input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
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
            case R.id.tv_endtime:
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
                        updateAdditionInfoMsg(endtime, year + "-" + month + "-" + day + " " + hour + ":" + minute);
                    }
                });
                endpicker.show();
                break;
            case R.id.tv_alltime:
                TimePicker alltimepicker = new TimePicker(this, TimePicker.HOUR_24);
                alltimepicker.setUseWeight(false);
                alltimepicker.setCycleDisable(false);
                alltimepicker.setRangeStart(0, 0);//00:00
                alltimepicker.setRangeEnd(23, 59);//23:59
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                alltimepicker.setSelectedItem(currentHour, currentMinute);
                alltimepicker.setTopLineVisible(false);
                alltimepicker.setLabel("小时","分钟");
                alltimepicker.setTextPadding(ConvertUtils.toPx(this, 15));
                alltimepicker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        //showToast(hour + ":" + minute);
                        updateAdditionInfoMsg(alltime,hour+"小时"+minute+"分钟");
                    }
                });
                alltimepicker.show();
                break;
            case R.id.tv_other:
                new MaterialDialog.Builder(this)
                        .title("补充说明")
                        .content("请填写活动补充说明")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "暂无",
                                "暂无",
                                false,
                                (dialog, input) -> updateAdditionInfoMsg(other, input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case cause:
                    String con_cause = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_cause.setText(con_cause+"");
                        }
                    });
                    break;
                case type:
                    String con_type = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_type.setText(con_type+"");
                        }
                    });
                    break;
                case type_other:
                    String con_type_other = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_type_other.setText(con_type_other+"");
                        }
                    });
                    break;
                case address:
                    String con_address = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_address.setText(con_address+"");
                        }
                    });
                    break;
                case address_other:
                    String con_address_other = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_address_other.setText(con_address_other+"");
                        }
                    });
                    break;
                case  other:
                    String con_other = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_other.setText(con_other+"");
                        }
                    });
                    break;
                case starttime:
                    String con_starttime = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_starttime.setText(con_starttime+"");
                        }
                    });
                    break;
                case endtime:
                    String con_endtime = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_endtime.setText(con_endtime+"");
                        }
                    });
                    break;
                case alltime:
                    String con_alltime = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_alltime.setText(con_alltime+"");
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private void updateAdditionInfoMsg(int type, String content) {
        Message msg = new Message();
        msg.what = type;
        msg.obj = content;
        mHandler.sendMessage(msg);
    }

    private void submitcontent() {
        String submitcause = tv_cause.getText().toString() + "";
        String submittype = tv_type.getText().toString() + "";
        String submittypeother = tv_type_other.getText().toString() + "";
        String submitaddress = tv_address.getText().toString() + "";
        String submitaddressother = tv_address_other.getText().toString() + "";
        String submitstarttime = tv_starttime.getText().toString() + "";
        String submitendtime = tv_endtime.getText().toString() + "";
        String submitalltime = tv_alltime.getText().toString() + "";
        String submitother = tv_other.getText().toString() + "";

        if (RxDataTool.isNullString(submitcause) || submitcause.equals("暂无")) {
            RxToast.error("请填写原因");
            return;
        }
        if (RxDataTool.isNullString(submittype) || submittype.equals("暂无")) {
            RxToast.error("请填写活动类型");
            return;
        }

        if (RxDataTool.isNullString(submitaddress) || submitaddress.equals("暂无")) {
            RxToast.error("请填写活动地点");
            return;
        }

        if (RxDataTool.isNullString(submitstarttime) || submitstarttime.equals("暂无")) {
            RxToast.error("请填写活动开始时间");
            return;
        }
        if (RxDataTool.isNullString(submitendtime) || submitendtime.equals("暂无")) {
            RxToast.error("请填写活动结束时间");
            return;
        }
        if (RxDataTool.isNullString(submitalltime) || submitalltime.equals("暂无")) {
            RxToast.error("请填写活动总时间");
            return;
        }

        PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);
        AdditionInfo additionInfo = new AdditionInfo();
        additionInfo.personInfo = user;
        additionInfo.setPersonid(user.getUserId());
        additionInfo.personInfo = user;
        additionInfo.setPersonid(user.getUserId());
        additionInfo.setAddcause(submitcause);
        additionInfo.setAddtype(submittype);
        additionInfo.setAddtypeother(submittypeother);
        additionInfo.setAddaddress(submitaddress);
        additionInfo.setAddaddressother(submitaddressother);
        additionInfo.setAddstarttime(submitstarttime);
        additionInfo.setAddendtime(submitendtime);
        additionInfo.setAddalltime(submitalltime);
        additionInfo.setAddother(submitother);
        additionInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    RxToast.success("添加个人补充活动信息成功");
                    Log.i("--------------->", "添加个人补充活动信息成功");
                    finish();
                } else {
                    RxToast.error("添加个人补充活动信息失败");
                    Log.i("--------------->", "添加个人补充活动信息失败" + e.getMessage());
                }
            }
        });
    }

    public void showProgressDialog(BaseProgressDialog.OnCancelListener cancelListener, boolean cancelable, String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new BaseProgressDialog(this);
        if (cancelListener != null) {
            mProgressDialog.setOnCancelListener(cancelListener);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void showProgressDialog(boolean cancelable, String msg) {
        showProgressDialog(null, cancelable, msg);
    }

    public void showProgressDialog(boolean cancelable) {
        showProgressDialog(cancelable, "");
    }

    public void stopProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.stop();
        }
        mProgressDialog = null;
    }

    protected void cancelProgressDialog() {
        if (mProgressDialog.cancel()) {
            mProgressDialog = null;
        }
    }

}
