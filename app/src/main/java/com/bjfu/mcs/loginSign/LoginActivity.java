package com.bjfu.mcs.loginSign;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjfu.mcs.R;
import com.bjfu.mcs.activity.MainActivity;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.splash.SplashActivity;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.security.SecuritySharedPreference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tirdway)
    TextView tirdway;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private String pwfromShare = null;
    private String usernamefromShare = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.bt_go, R.id.fab,R.id.tirdway})
    public void setListener(View v) {
        switch (v.getId()) {
            case R.id.bt_go:
                String phone = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                try {
                    SecuritySharedPreference security = new SecuritySharedPreference(MCSApplication.getApplication(), phone, Context.MODE_PRIVATE);
                    pwfromShare = security.getString("password", null);
                    usernamefromShare = security.getString("username", null);
                } catch (Exception e) {
                    pwfromShare = null;
                    usernamefromShare = null;
                }
                if(RxDataTool.isNullString(phone)){
                    RxToast.error("用户名不能为空");
                    return;
                }
                if(RxDataTool.isNullString(password)){
                    RxToast.error("密码不能为空");
                    return;
                }

//                if (!RxDataTool.isNullString(username) && !RxDataTool.isNullString(password)
//                        && !RxDataTool.isNullString(pwfromShare) && !RxDataTool.isNullString(usernamefromShare)
//                        && username.equals(usernamefromShare) && password.equals(pwfromShare)) {
//                    RxToast.normal("登录成功");
//                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
//                    Intent i2 = new Intent(LoginActivity.this, SplashActivity.class);
//                    startActivity(i2, oc2.toBundle());
//                }

                if(!RxDataTool.isNullString(phone) && !RxDataTool.isNullString(password)){
                    doLogin(phone,password);
                }

                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
                break;
            default:
                break;
        }
    }

    private void doLogin(final String mobile, String pwd) {
        PersonInfo user = new PersonInfo();
        user.setUsername(mobile);
        user.setPassword(pwd);
        user.login(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {

                if(e == null){

//                    PersonInfo pi = PersonInfo.getCurrentUser(PersonInfo.class);
//                    if(null != pi){
//
//                    }
                    RxToast.success("登录成功");
                    RxActivityTool.skipActivityAndFinish(LoginActivity.this,MainActivity.class);
                }else{
                    RxToast.error("登录失败:" + e.getMessage());
                }
            }
        });

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }
}
