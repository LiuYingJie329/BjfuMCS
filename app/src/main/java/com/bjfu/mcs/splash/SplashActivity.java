package com.bjfu.mcs.splash;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjfu.mcs.R;
import com.bjfu.mcs.activity.MainActivity;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.loginSign.LoginActivity;
import com.bjfu.mcs.utils.ConfigKey;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.security.SecuritySharedPreference;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.bjfu.mcs.application.MCSApplication.appcache;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.btn_launch_activity)
    AppCompatButton button;

    private Boolean loginfirst = true;
    private String pwfromShare = null;
    private String usernamefromShare = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //判断用户是否是第一次登录
        try {
            SecuritySharedPreference security = new SecuritySharedPreference(MCSApplication.getApplication(), "spf_loginfirst", Context.MODE_PRIVATE);
            loginfirst = security.getBoolean("loginfirst", true);
        } catch (Exception e) {
            loginfirst = true;
        }
        //不是第一次登录
        if (!loginfirst) {
            button.setVisibility(View.INVISIBLE);
        }else{
            button.setVisibility(View.VISIBLE);
        }

        try {
            SecuritySharedPreference security = new SecuritySharedPreference(MCSApplication.getApplication(), "spf_loginInfo", Context.MODE_PRIVATE);
            pwfromShare = security.getString("password", null);
            usernamefromShare = security.getString("username", null);
        } catch (Exception e) {
            pwfromShare = null;
            usernamefromShare = null;
        }

        if (!RxDataTool.isNullString(usernamefromShare)
                && !RxDataTool.isNullString(pwfromShare)) {
            //注册界面返回至登录界面
            doLogin(usernamefromShare, pwfromShare);
        }

    }

    private void doLogin(final String mobile, String pwd) {
        showProgressDialog(false);
        PersonInfo user = new PersonInfo();
        user.setUsername(mobile);
        user.setPassword(pwd);
        user.login(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    appcache.put("has_login","yes");
                    appcache.put(ConfigKey.KEY_CURR_LOGIN_MOBILE,mobile);
                    PersonInfo pi = PersonInfo.getCurrentUser(PersonInfo.class);
                    if(null != pi){
                        pi.setSqlmobilePhoneNumber(mobile);
                        pi.setSqlusername(mobile);
                        pi.setSqlpassword(pwd);
                        DataBaseHandler.insertPesonInfo(pi);
                    }
                    RxToast.success("登录成功");

                    RxActivityTool.skipActivityAndFinish(SplashActivity.this, MainActivity.class);
                } else {
                    RxToast.error("登录失败");
                    RxActivityTool.skipActivityAndFinish(SplashActivity.this, LoginActivity.class);
                }
            }
        });

    }


    @OnClick({R.id.btn_launch_activity})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_launch_activity:
                //第一次登录
                RxActivityTool.skipActivity(SplashActivity.this, IntroActivity.class);
                break;
            default:
                break;
        }
    }
}
