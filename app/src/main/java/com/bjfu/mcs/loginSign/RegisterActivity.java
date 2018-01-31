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
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.bjfu.mcs.R;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxConstTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxRegTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.security.SecuritySharedPreference;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bjfu.mcs.R2.id.et_password;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.bt_go)
    Button registergo;
    @BindView(R.id.et_username)
    EditText mUsername;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_repeatpassword)
    EditText mRepeatPassword;

    private String uername = null;
    private String repassword = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showEnterAnimation();

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.fab, R.id.bt_go})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                animateRevealClose();
                break;
            case R.id.bt_go:
                uername = mUsername.getText().toString();
                password = mPassword.getText().toString();
                repassword = mRepeatPassword.getText().toString();
                if (RxDataTool.isNullString(uername)) {
                    RxToast.error("手机号不能为空");
                    return;
                }
                if (uername.length() != 11) {
                    RxToast.error("请输入11位手机号");
                    return;
                }
                if (!RxRegTool.isMobile(uername)) {
                    RxToast.error(getString(R.string.toast_phoe_error));
                    return;
                }
                if (RxDataTool.isNullString(password)) {
                    RxToast.error(getString(R.string.toast_password_null));
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
                if (RxRegTool.isMobile(uername)
                        && !RxDataTool.isNullString(password)
                        && !RxDataTool.isNullString(repassword)
                        && password.equals(repassword)) {
                    saveRegisterData(uername, password);
                    RxToast.normal(getString(R.string.toast_register_success));
                    RxActivityTool.skipActivityAndFinish(RegisterActivity.this, LoginActivity.class);
                }

                break;
            default:
                break;
        }
    }

    /**
     * 将用户名和密码保存在本地的SharedPreference数据库分钟
     */
    private void saveRegisterData(String username, String password) {
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(MCSApplication.getApplication(), uername, Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = securitySharedPreference.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
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
