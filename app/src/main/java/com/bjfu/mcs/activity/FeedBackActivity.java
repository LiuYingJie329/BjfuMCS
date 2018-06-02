package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjfu.mcs.R;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.FeedBackInfo;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxDeviceTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.picker.OptionPicker;
import com.bjfu.mcs.utils.picker.WheelView;
import com.bjfu.mcs.utils.widget.BaseProgressDialog;
import com.umeng.message.common.UmengMessageDeviceConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedBackActivity extends AppCompatActivity {

    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.tv_feedback_type)
    TextView tv_feedback_type;
    @BindView(R.id.tv_feedback_ques)
    TextView tv_feedback_ques;
    @BindView(R.id.tv_feedback_act)
    TextView tv_feedback_act;
    @BindView(R.id.tv_feedback_fre)
    TextView tv_feedback_fre;
    @BindView(R.id.tv_feedback_content)
    TextView tv_feedback_content;
    @BindView(R.id.tv_feedback_other)
    TextView tv_feedback_other;

    private FeedBackActivity mContext;
    private BaseProgressDialog mProgressDialog = null;

    private static final int feedback_type = 1;
    private static final int feedback_ques = 2;
    private static final int feedback_act = 3;
    private static final int feedback_fre = 4;
    private static final int feedback_content = 5;
    private static final int feedback_other = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        mContext = this;
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("反馈模块");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private void updatePersonInfoMsg(int type,String content){
        Message msg = new Message();
        msg.what = type;
        msg.obj =  content;
        mHandler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case feedback_type:
                    String type = (String)msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_feedback_type.setText(type);
                        }
                    });
                    break;
                case feedback_ques:
                    String question = (String)msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_feedback_ques.setText(question);
                        }
                    });
                    break;
                case feedback_act:
                    String activity = (String)msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_feedback_act.setText(activity);
                        }
                    });
                    break;
                case feedback_fre:
                    String frequence = (String)msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_feedback_fre.setText(frequence);
                        }
                    });
                    break;
                case feedback_content:
                    String content = (String)msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_feedback_content.setText(content);
                        }
                    });
                    break;
                case feedback_other:
                    String other = (String)msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_feedback_other.setText(other);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };


    @OnClick({R.id.submit,
            R.id.tv_feedback_type,R.id.tv_feedback_ques,
            R.id.tv_feedback_act,R.id.tv_feedback_fre,
            R.id.tv_feedback_content,R.id.tv_feedback_other})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.submit:
                submitFeedback();
                break;
            case R.id.tv_feedback_type:
                OptionPicker picker3 = new OptionPicker(this, new String[]{
                        "BUG", "闪退", "待优化"
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
                        updatePersonInfoMsg(feedback_type,item);
                    }
                });
                picker3.show();
                break;
            case R.id.tv_feedback_ques:
                new MaterialDialog.Builder(this)
                        .title("问题描述")
                        .content("填写问题描述")
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
                                (dialog, input) -> updatePersonInfoMsg(feedback_ques,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_feedback_act:
                new MaterialDialog.Builder(this)
                        .title("问题出现界面")
                        .content("填写问题出现界面")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "主界面",
                                "主界面",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(feedback_act,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_feedback_fre:
                OptionPicker picker4 = new OptionPicker(this, new String[]{
                        "偶发", "常出现", "频繁"
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
                        updatePersonInfoMsg(feedback_fre,item);
                    }
                });
                picker4.show();
                break;
            case R.id.tv_feedback_content:
                new MaterialDialog.Builder(this)
                        .title("具体描述")
                        .content("填写具体描述")
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
                                (dialog, input) -> updatePersonInfoMsg(feedback_content,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_feedback_other:
                new MaterialDialog.Builder(this)
                        .title("其他意见或建议")
                        .content("填写其他意见或建议")
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
                                (dialog, input) -> updatePersonInfoMsg(feedback_other,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            default:
                break;
        }
    }
    public void submitFeedback() {

        String type = tv_feedback_type.getText().toString()+"";
        String question = tv_feedback_ques.getText().toString()+"";
        String activity =   tv_feedback_act.getText().toString()+"";
        String frequence = tv_feedback_fre.getText().toString()+"";
        String content = tv_feedback_content.getText().toString()+"";
        String othercontent = tv_feedback_other.getText().toString()+"";

        if(RxDataTool.isNullString(type) || type.equals("BUG")){
            RxToast.error("反馈类型不能为空");
            return;
        }
        if(RxDataTool.isNullString(question) || question.equals("头像设置失败")){
            RxToast.error("问题描述不能为空");
            return;
        }
        if(RxDataTool.isNullString(activity) || activity.equals("个人设置界面")){
            RxToast.error("问题界面不能为空");
            return;
        }
        if(RxDataTool.isNullString(frequence) || frequence.equals("偶发")){
            RxToast.error("出现频率不能为空");
            return;
        }
        if(RxDataTool.isNullString(content) ){
            RxToast.error("具体描述不能为空");
            return;
        }
        if(RxDataTool.isNullString(othercontent)){
            RxToast.error("其他意见不能为空");
            return;
        }
        showProgressDialog(false);
        PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);

        FeedBackInfo feedBackInfo = new FeedBackInfo();
        feedBackInfo.personInfo  = user;
        feedBackInfo.setContent(content);
        feedBackInfo.setType(type);
        feedBackInfo.setQuestion(question);
        feedBackInfo.setActivity(activity);
        feedBackInfo.setFrequence(frequence);
        feedBackInfo.setOthercontent(othercontent);
        feedBackInfo.setMobile(user.getSqlmobilePhoneNumber());
        feedBackInfo.setDeviceIMEI(RxDeviceTool.getUniqueSerialNumber() + "");
        feedBackInfo.setAppversion(RxDeviceTool.getAppVersionNo(mContext)+"");
        feedBackInfo.setDeviceandroid(android.os.Build.VERSION.RELEASE+"");
        feedBackInfo.setDeviceCPU(UmengMessageDeviceConfig.getCPU()+"");
        feedBackInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                stopProgressDialog();
                if(e == null){
                    RxToast.success("反馈成功");
                }else{
                    RxToast.success("反馈失败");
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
