package com.bjfu.mcs.loginSign;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bjfu.mcs.R;
import com.bjfu.mcs.activity.MainActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.loginSign.view.ShareButton;
import com.bjfu.mcs.utils.ConfigKey;
import com.bjfu.mcs.utils.Constants;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.widget.BaseProgressDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.bjfu.mcs.application.MCSApplication.appcache;


public class SocialOauthActivity extends Activity {

    private static final String TAG = "SocialOauthActivity";

    private ShareButton llWeibo;
    private ShareButton llWeChat;
    private ShareButton llQQ;
    private BaseProgressDialog mProgressDialog = null;

    /**
     * type=0, 用户选择QQ或者微博登录
     * type=1，用户选择微信登录
     */
    private int type = 0;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.jjdxm_social_activity_social_oauth);


        llWeibo = (ShareButton) findViewById(R.id.social_oauth_sb_weibo);
        llWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RxToast.info("登录微博");
                skipAuth();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRegistWEIBO();
                    }
                },5000);

            }
        });
        llWeChat = (ShareButton) findViewById(R.id.social_oauth_sb_wechat);
        llWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RxToast.info("登录微信");
                skipAuth();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRegistWEIXIN();
                    }
                },5000);

                type = 1;
            }
        });
        llQQ = (ShareButton) findViewById(R.id.social_oauth_sb_qq);
        llQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RxToast.info("登录QQ");
                skipAuth();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRegistQQ();
                    }
                },5000);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (type == 0) {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (type == 1) {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.jjdxm_social_snack_out);
    }

    private void doRegistWEIXIN() {
        showProgressDialog(false);
        PersonInfo piInfo = new PersonInfo();
        piInfo.setUsername(Constants.LOGIN_WEIXIN_USER_NAME);
        piInfo.setPassword(Constants.LOGIN_WEIXIN_USER_UID);
        piInfo.setUserAvatar(Constants.LOGIN_WEIXIN_USER_HEARDURL);
        piInfo.setLoginType("WEIXIN");
        piInfo.signUp(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    RxToast.normal(getString(R.string.toast_register_success));
                } else {
                    //RxToast.normal("注册失败");
                    Log.e("注册失败", e.getMessage()+"注册失败码-->"+e.getErrorCode());
                    if(e.getErrorCode() == 202){
                        //已经有账号进行登录
                        doLoginWEIXIN();
                    }
                }
            }
        });
    }

    private void doLoginWEIXIN() {
        showProgressDialog(false);
        PersonInfo user = new PersonInfo();
        user.setUsername(Constants.LOGIN_WEIXIN_USER_NAME);
        user.setPassword(Constants.LOGIN_WEIXIN_USER_UID);
        user.login(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    appcache.put("has_login", "yes");
                    appcache.put(ConfigKey.KEY_CURR_LOGIN_MOBILE, Constants.LOGIN_WEIXIN_USER_NAME);
                    PersonInfo pi = PersonInfo.getCurrentUser(PersonInfo.class);
                    if (null != pi) {
                        pi.setSqlusername(Constants.LOGIN_WEIXIN_USER_NAME);
                        pi.setSqlpassword(Constants.LOGIN_WEIXIN_USER_UID);
                        pi.setUserId(user.getObjectId());
                        Log.i("--------------->",user.getObjectId());
                        DataBaseHandler.insertPesonInfo(pi);
                    }
                    RxToast.success("登录成功");
                    RxActivityTool.skipActivityAndFinishAll(SocialOauthActivity.this, MainActivity.class);
                    //RxActivityTool.finishActivity(LoginActivity.class);
                } else {
                    RxToast.error("登录失败:" + e.getMessage());
                }
            }
        });

    }

    private void doRegistQQ() {
        showProgressDialog(false);
        PersonInfo piInfo = new PersonInfo();
        piInfo.setUsername(Constants.LOGIN_QQ_USER_NAME);
        piInfo.setPassword(Constants.LOGIN_QQ_USER_UID);
        piInfo.setUserAvatar(Constants.LOGIN_QQ_USER_HEARDURL);
        piInfo.setLoginType("QQ");
        piInfo.signUp(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    RxToast.normal(getString(R.string.toast_register_success));
                } else {
                    //RxToast.normal("注册失败");
                    Log.e("注册失败", e.getMessage()+"注册失败码-->"+e.getErrorCode());
                    if(e.getErrorCode() == 202){
                        //已经有账号进行登录
                        doLoginQQ();
                    }
                }
            }
        });
    }

    private void doLoginQQ() {
        showProgressDialog(false);
        PersonInfo user = new PersonInfo();
        user.setUsername(Constants.LOGIN_QQ_USER_NAME);
        user.setPassword(Constants.LOGIN_QQ_USER_UID);
        user.login(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    appcache.put("has_login", "yes");
                    appcache.put(ConfigKey.KEY_CURR_LOGIN_MOBILE, Constants.LOGIN_QQ_USER_NAME);
                    PersonInfo pi = PersonInfo.getCurrentUser(PersonInfo.class);
                    if (null != pi) {
                        pi.setSqlusername(Constants.LOGIN_QQ_USER_NAME);
                        pi.setSqlpassword(Constants.LOGIN_QQ_USER_UID);
                        pi.setUserId(user.getObjectId());
                        Log.i("--------------->",user.getObjectId());
                        DataBaseHandler.insertPesonInfo(pi);
                    }
                    RxToast.success("登录成功");
                    RxActivityTool.skipActivityAndFinish(SocialOauthActivity.this, MainActivity.class);
                } else {
                    RxToast.error("登录失败:" + e.getMessage());
                }
            }
        });

    }

    private void doRegistWEIBO() {
        showProgressDialog(false);
        PersonInfo piInfo = new PersonInfo();
        piInfo.setUsername(Constants.LOGIN_WEIBO_USER_NAME);
        piInfo.setPassword(Constants.LOGIN_WEIBO_USER_UID);
        piInfo.setUserAvatar(Constants.LOGIN__WEIBO_HEARDURL);
        piInfo.setLoginType("WEIBO");
        piInfo.signUp(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    RxToast.normal(getString(R.string.toast_register_success));
                } else {
                    //RxToast.normal("注册失败");
                    Log.e("注册失败", e.getMessage()+"注册失败码-->"+e.getErrorCode());
                    if(e.getErrorCode() == 202){
                        //已经有账号进行登录
                        doLoginWEIBO();
                    }
                }
            }
        });
    }

    private void doLoginWEIBO() {
        showProgressDialog(false);
        PersonInfo user = new PersonInfo();
        user.setUsername(Constants.LOGIN_WEIBO_USER_NAME);
        user.setPassword(Constants.LOGIN_WEIBO_USER_UID);
        user.login(new SaveListener<PersonInfo>() {
            @Override
            public void done(PersonInfo user, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    appcache.put("has_login", "yes");
                    appcache.put(ConfigKey.KEY_CURR_LOGIN_MOBILE, Constants.LOGIN_QQ_USER_NAME);
                    PersonInfo pi = PersonInfo.getCurrentUser(PersonInfo.class);
                    if (null != pi) {
                        pi.setSqlusername(Constants.LOGIN_WEIBO_USER_NAME);
                        pi.setSqlpassword(Constants.LOGIN_WEIBO_USER_UID);
                        pi.setUserId(user.getObjectId());
                        Log.i("--------------->",user.getObjectId());
                        DataBaseHandler.insertPesonInfo(pi);
                    }
                    RxToast.success("登录成功");
                    RxActivityTool.skipActivityAndFinish(SocialOauthActivity.this, MainActivity.class);
                } else {
                    RxToast.error("登录失败:" + e.getMessage());
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

    public void skipAuth() {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.umeng.soexample", "com.umeng.soexample.share.InfoActivity");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
