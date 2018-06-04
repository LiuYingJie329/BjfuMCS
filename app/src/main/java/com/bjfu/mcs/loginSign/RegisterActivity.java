package com.bjfu.mcs.loginSign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bjfu.mcs.R;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxConstTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxRegTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.security.SecuritySharedPreference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.bjfu.mcs.R2.id.et_password;
import static com.bjfu.mcs.R2.id.pin;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.bt_go)
    Button registergo;
    @BindView(R.id.et_username)
    EditText mUsername;
    @BindView(R.id.sms)
    EditText mSms;
    @BindView(R.id.getsms)
    Button mGetsms;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_repeatpassword)
    EditText mRepeatPassword;

    private String phone = null;
    private String repassword = null;
    private String password = null;
    private String sms = null;

    private int toast1 = 0;
    private int toast2 = 0;
    private int toast3 = 0;
    private int toast4 = 0;
    private int toast5 = 0;
    private int toast6 = 0;
    private int toast7 = 0;
    private int toast8 = 0;
    private int toast9 = 0;
    private int toast10 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showEnterAnimation();

        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (RxDataTool.isNullString(mUsername.getText().toString())) {
                    if (toast1 <= 1) {
                        RxToast.error("手机号不能为空");
                        toast1++;
                        return;
                    }
                }
                if (mUsername.getText().toString().length() != 11) {
                    if (toast2 <= 1) {
                        RxToast.error("请输入11位手机号");
                        toast2++;
                        return;
                    }
                }
                if (mUsername.getText().length() == 11 && !RxRegTool.isMobile(mUsername.getText().toString())) {
                    RxToast.error("手机号错误");
                }
                if (mUsername.getText().toString().length() == 11) {
                    RxToast.success("手机号正确");
                    return;
                }
            }
        });
        mSms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (RxDataTool.isNullString(mSms.getText().toString())) {
                    if (toast3 <= 1) {
                        RxToast.error("验证码不能为空");
                        toast3++;
                        return;
                    }
                }
                if (mSms.getText().toString().length() == 6) {
                    phone = mUsername.getText().toString();
                    sms = mSms.getText().toString();
                    BmobSMS.verifySmsCode(phone, sms, new UpdateListener() {
                        @Override
                        public void done(BmobException ex) {
                            if (ex == null) {//短信验证码已验证成功
                                RxToast.success("短信验证通过");
                            } else {
                                RxToast.error("短信验证失败");
                                //toast("验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                            }
                        }
                    });
                    return;
                }
            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (RxDataTool.isNullString(mPassword.getText().toString())) {
                    if (toast4 <= 1) {
                        RxToast.error("密码不能为空");
                        toast4++;
                        return;
                    }
//                    if(mPassword.getText().toString().length()>=4 &&
//                            mPassword.getText().toString().length()<=6 ){
//                        if(toast5 == 0){
//                            RxToast.error("密码最低6位，请重新输入");
//                            toast5++;
//                            return;
//                        }
//                    }
//                    if(mPassword.getText().toString().length()>=16){
//                        if(toast6 == 0){
//                            RxToast.error("密码最高16位，请重新输入");
//                            toast6++;
//                            return;
//                        }
//                    }
                    if (mPassword.getText().toString().length() >= 5 &&
                            !RxDataTool.isPassword(mPassword.getText().toString())) {
                        if (toast5 <= 0) {
                            RxToast.error("密码由6~16位数字和英文字母组成");
                            toast5++;
                            return;
                        }
                    }
                    if (mPassword.getText().toString().length() >= 6
                            && mPassword.getText().toString().length() <= 16
                            && RxDataTool.isPassword(mPassword.getText().toString())) {
                        if (toast6 <= 0) {
                            RxToast.error("当前密码格式正确");
                            toast6++;
                            return;
                        }
                    }
                }


            }
        });

        mRepeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass = mPassword.getText().toString();
                if (RxDataTool.isNullString(mRepeatPassword.getText().toString())) {
                    if (toast7 <= 1) {
                        RxToast.error("重复密码不能为空");
                        toast7++;
                        return;
                    }
//                    if(mPassword.getText().toString().length()>=4 &&
//                            mPassword.getText().toString().length()<=6 ){
//                        if(toast5 == 0){
//                            RxToast.error("密码最低6位，请重新输入");
//                            toast5++;
//                            return;
//                        }
//                    }
//                    if(mPassword.getText().toString().length()>=16){
//                        if(toast6 == 0){
//                            RxToast.error("密码最高16位，请重新输入");
//                            toast6++;
//                            return;
//                        }
//                    }
                    if (mRepeatPassword.getText().toString().length() >= 5 &&
                            !RxDataTool.isPassword(mRepeatPassword.getText().toString())) {
                        if (toast8 <= 0) {
                            RxToast.error("重复密码由6~16位数字和英文字母组成");
                            toast8++;
                            return;
                        }
                    }
                    if (mRepeatPassword.getText().toString().length() == phone.length()
                            && mRepeatPassword.getText().toString().equals(phone)
                            && RxDataTool.isPassword(mRepeatPassword.getText().toString())) {
                        if (toast9 <= 0) {
                            RxToast.error("当前重复密码正确");
                            toast9++;
                            return;
                        }
                    }
                    if(mRepeatPassword.getText().toString().length() == phone.length()
                            && !mRepeatPassword.getText().toString().equals(phone)){
                        if (toast10 <= 0) {
                            RxToast.error("两次密码不一致");
                            toast10++;
                            return;
                        }
                    }
                }


            }
        });

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.fab, R.id.bt_go, R.id.getsms, R.id.et_password})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                animateRevealClose();
                break;

            case R.id.getsms:
                phone = mUsername.getText().toString();
                if (RxDataTool.isNullString(phone)) {
                    RxToast.error("手机号不能为空");
                    return;
                }
                if (phone.length() != 11) {
                    RxToast.error("请输入11位手机号");
                    return;
                }
                if (!RxRegTool.isMobile(phone)) {
                    RxToast.error(getString(R.string.toast_phoe_error));
                    return;
                }
                if (!RxDataTool.isNullString(phone)
                        && phone.length() == 11
                        && RxRegTool.isMobile(phone)) {
                    BmobSMS.requestSMSCode(phone, "MCS实验系统", new QueryListener<Integer>() {

                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            if (ex == null) {//验证码发送成功
                                RxToast.success("验证码发送成功，短信id：" + smsId);
                            } else {
                                RxToast.error("验证码发送失败");
                            }
                        }
                    });
                }
                break;

            case R.id.bt_go:
                phone = mUsername.getText().toString();
                password = mPassword.getText().toString();
                repassword = mRepeatPassword.getText().toString();
                if (RxDataTool.isNullString(phone)) {
                    RxToast.error("手机号不能为空");
                    return;
                }
                if (phone.length() != 11) {
                    RxToast.error("请输入11位手机号");
                    return;
                }
                if (!RxRegTool.isMobile(phone)) {
                    RxToast.error(getString(R.string.toast_phoe_error));
                    return;
                }

                if (RxDataTool.isNullString(password)) {
                    RxToast.error(getString(R.string.toast_password_null));
                    return;
                }

                if (!RxDataTool.isPassword(password)) {
                    RxToast.error("密码由6~16位数字和英文字母组成");
                    return;
                }

                if (RxDataTool.isNullString(repassword)) {
                    RxToast.error("重复密码不能为空");
                    return;
                }


                if (!password.equals(repassword)) {
                    RxToast.error(getString(R.string.toast_password_noequal));
                    return;
                }

                if (RxRegTool.isMobile(phone)
                        && !RxDataTool.isNullString(password)
                        && !RxDataTool.isNullString(repassword)
                        && password.equals(repassword)) {

                    doRegist(phone, phone, password);

                }

                break;
            default:
                break;
        }
    }

    /**
     * 将用户名和密码保存在本地的SharedPreference数据库分钟
     */
    private void saveRegisterData(String mobile, String password) {
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(MCSApplication.getApplication(), "spf_loginInfo", Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = securitySharedPreference.edit();
        editor.putString("username", mobile);
        editor.putString("password", password);
        editor.apply();
    }

    /**
     * Bmob注册用户
     */
    private void doRegist(String phone, String mobile, String pwd) {
        showProgressDialog(false);
        PersonInfo piInfo = new PersonInfo();
        piInfo.setMobilePhoneNumber(mobile);
        piInfo.setUsername(mobile);
        piInfo.setPassword(pwd);
        piInfo.setMobilePhoneNumberVerified(true);
        piInfo.signUp(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    RxToast.normal(getString(R.string.toast_register_success));
                    saveRegisterData(mobile, pwd);
                    Bundle bundle = new Bundle();
                    bundle.putString("zhucechenggong", "zhuceback");
                    RxActivityTool.skipActivity(RegisterActivity.this, LoginActivity.class, bundle);
                } else {
                    RxToast.normal("注册失败");
                    Log.e("注册失败", e.getMessage());
                }
            }
        });

    }

    private void showEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

}
