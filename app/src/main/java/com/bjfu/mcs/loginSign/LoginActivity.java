package com.bjfu.mcs.loginSign;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjfu.mcs.R;
import com.bjfu.mcs.activity.ExcelActivity;
import com.bjfu.mcs.activity.MainActivity;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.greendao.PersonPushSet;
import com.bjfu.mcs.splash.SplashActivity;
import com.bjfu.mcs.utils.ConfigKey;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.SerializableMap;
import com.bjfu.mcs.utils.security.SecuritySharedPreference;

import org.xclcharts.renderer.XEnum;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.bjfu.mcs.application.MCSApplication.appcache;

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
        Log.i("--->onCreate", "onCreate");
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("--->onStart", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("--->onPause", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("--->onStop", "onStop");
    }

    @OnClick({R.id.bt_go, R.id.fab, R.id.tirdway})
    public void setListener(View v) {
        switch (v.getId()) {
            case R.id.bt_go:
                String phone = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                if (RxDataTool.isNullString(phone)) {
                    RxToast.error("用户名不能为空");
                    return;
                }
                if (RxDataTool.isNullString(password)) {
                    RxToast.error("密码不能为空");
                    return;
                }

                if (!RxDataTool.isNullString(phone) && !RxDataTool.isNullString(password)) {
                    doLogin(phone, password);
                }

                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
                break;

            case R.id.tirdway:
                RxActivityTool.skipActivity(LoginActivity.this,SocialOauthActivity.class);
                this.overridePendingTransition(R.anim.jjdxm_social_snack_in, 0);
                break;
            default:

                break;
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
                    appcache.put("has_login", "yes");
                    appcache.put(ConfigKey.KEY_CURR_LOGIN_MOBILE, mobile);
                    PersonInfo pi = PersonInfo.getCurrentUser(PersonInfo.class);
                    if (null != pi) {
                        pi.setSqlmobilePhoneNumber(mobile);
                        pi.setSqlusername(mobile);
                        pi.setSqlpassword(pwd);
                        pi.setUserId(user.getObjectId());
                        Log.i("--------------->",user.getObjectId());
                        DataBaseHandler.insertPesonInfo(pi);
                    }
                    RxToast.success("登录成功");
                    saveloginfirst();
                    saveRegisterData(mobile, pwd);
                    //initpushdata();
                    RxActivityTool.skipActivityAndFinish(LoginActivity.this, MainActivity.class);
                } else {
                    RxToast.error("登录失败:" + e.getMessage());
                }
            }
        });


    }

    private void initpushdata() {
        PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);
        PersonPushSet pushSet = new PersonPushSet();
        pushSet.personInfo = user;
        pushSet.setPersonid(user.getUserId());
        pushSet.setOpenstarttime("23:00");
        pushSet.setOpenendtime("07:00");
        pushSet.setOpencool(1 * 60 + "");
        pushSet.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i("--------------->","个人推送情况添加成功------->");
                } else {
                    Log.i("--------------->","个人推送情况添加失败------->");
                }
            }
        });


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

    private void saveloginfirst() {
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(MCSApplication.getApplication(), "spf_loginfirst", Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = securitySharedPreference.edit();
        editor.putBoolean("loginfirst", false);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
        Log.i("--->onRestart", "onRestart");
        //getMediaData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
        Log.i("--->onResume", "onResume");
        getMediaData();
        try {
            SecuritySharedPreference security = new SecuritySharedPreference(MCSApplication.getApplication(), "spf_loginInfo", Context.MODE_PRIVATE);
            pwfromShare = security.getString("password", null);
            usernamefromShare = security.getString("username", null);
        } catch (Exception e) {
            pwfromShare = null;
            usernamefromShare = null;
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if("EXIT".equals(bundle.getString("tuichudenglu"))){
                try {
                    if (!RxDataTool.isNullString(usernamefromShare)
                            && !RxDataTool.isNullString(pwfromShare)) {
                        //注册界面返回至登录界面
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etUsername.setText(usernamefromShare);
                                etPassword.setText("");
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if("zhuceback".equals(bundle.getString("zhucechenggong"))){
                try {
                    if (!RxDataTool.isNullString(usernamefromShare)
                            && !RxDataTool.isNullString(pwfromShare)) {
                        //注册界面返回至登录界面
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etUsername.setText(usernamefromShare);
                                etPassword.setText(pwfromShare);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if("SWITCH".equals(bundle.getString("addaccount"))){
                etUsername.setText("");
                etPassword.setText("");
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("--->onSaveInstanceState", "onSaveInstanceState");
    }

    private void getMediaData() {
        etUsername.setText("");
        etPassword.setText("");
        try {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    SerializableMap serializableMap = (SerializableMap) bundle.get("mediamap");

                    StringBuilder sb = new StringBuilder();
                    for (String key : serializableMap.getMap().keySet()) {
                        sb.append(key).append(" : ").append(serializableMap.getMap().get(key)).append("\n");
                    }

                    Log.i("第三方获取授权数据-->", "data---->" + sb.toString());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
