package com.bjfu.mcs.upush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjfu.mcs.R;
import com.bjfu.mcs.application.MCSApplication;
import com.bjfu.mcs.upush.notification.DebugNotification;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.common.inter.ITagManager.Result;
import com.umeng.message.inapp.IUmengInAppMsgCloseCallback;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.tag.TagManager;
import com.umeng.message.tag.TagManager.TCallBack;
import com.umeng.message.tag.TagManager.WeightedTagListCallBack;

import java.util.Hashtable;
import java.util.List;

import butterknife.ButterKnife;

import static com.bjfu.mcs.application.MCSApplication.mPushAgent;

public class UpushActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = UpushActivity.class.getName();
    private static final String TAG_REMAIN = "tag_remain";
    private static final String WEIGHTED_TAG_REMAIN = "weighted_tag_remain";

    private EditText inputTag;
    private EditText inputWeightedTag;
    private EditText inputWeightedTagValue;
    private EditText inputAlias;
    private EditText inputAliasType;

    private TextView tagRemain;

    //private PushAgent mPushAgent;
    private Handler handler;

    private SharedPreferences sharedPref;

    private MyReceiver myReceiver;

    private UpushActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upush);
        ButterKnife.bind(this);
        mContext = this;
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("推送系统");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        initClickListener();
        handler = new Handler();
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MCSApplication.UPDATE_STATUS_ACTION);
        registerReceiver(myReceiver, filter);

        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();

        //展示插屏消息
        if (savedInstanceState == null) {
            showCardMessage();
        }
    }


    private void initClickListener() {
        inputTag = findViewById(R.id.etx_input_tag);
        inputWeightedTag = findViewById(R.id.etx_input_weighted_tag);
        inputWeightedTagValue = findViewById(R.id.etx_input_weighted_tag_value);
        inputAlias = findViewById(R.id.etx_input_alias);
        inputAliasType = findViewById(R.id.etx_input_alias_type);
        tagRemain = findViewById(R.id.tv_tag_remain);
        findViewById(R.id.btn_add_tag).setOnClickListener(this);
        findViewById(R.id.btn_delete_tag).setOnClickListener(this);
        findViewById(R.id.btn_show_tag).setOnClickListener(this);
        findViewById(R.id.btn_add_weighted_tag).setOnClickListener(this);
        findViewById(R.id.btn_delete_weighted_tag).setOnClickListener(this);
        findViewById(R.id.btn_show_weighted_tag).setOnClickListener(this);
        findViewById(R.id.btn_add_alias).setOnClickListener(this);
        findViewById(R.id.btn_delete_alias).setOnClickListener(this);
        findViewById(R.id.btn_show_card_message).setOnClickListener(this);
        findViewById(R.id.btn_serialnet).setOnClickListener(this);
        findViewById(R.id.btn_device_check).setOnClickListener(this);
        findViewById(R.id.btn_devicetoken_check).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_tag:
                addTag();
                break;
            case R.id.btn_delete_tag:
                deleteTag();
                break;
            case R.id.btn_show_tag:
                showTag();
                break;
            case R.id.btn_add_weighted_tag:
                addWeightedTag();
                break;
            case R.id.btn_delete_weighted_tag:
                deleteWeightedTag();
                break;
            case R.id.btn_show_weighted_tag:
                showWeightedTag();
                break;
            case R.id.btn_add_alias:
                addAlias();
                break;
            case R.id.btn_delete_alias:
                deleteAlias();
                break;
            case R.id.btn_set_alias:
                setAlias();
                break;
            case R.id.btn_show_card_message:
                showCardMessage();
                break;
            case R.id.btn_serialnet:
                serialnet();
                break;
            case R.id.btn_device_check:
                deviceCheck();
                break;
            case R.id.btn_devicetoken_check:
                deviceToken();
                break;
            default:
        }
    }

    private void setAlias() {
        String alias = inputAlias.getText().toString();
        String aliasType = inputAliasType.getText().toString();
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(this, "请输入alias", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(aliasType)) {
            Toast.makeText(this, "请输入alias type", Toast.LENGTH_SHORT).show();
            return;
        }
        mPushAgent.setAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputAlias.setText("");
                        inputAliasType.setText("");
                    }
                });
            }
        });
    }

    private void deleteAlias() {
        String alias = inputAlias.getText().toString();
        String aliasType = inputAliasType.getText().toString();
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(this, "请输入alias", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(aliasType)) {
            Toast.makeText(this, "请输入alias type", Toast.LENGTH_SHORT).show();
            return;
        }
        mPushAgent.deleteAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputAlias.setText("");
                        inputAliasType.setText("");
                    }
                });
            }
        });
    }

    private void addAlias() {
        String alias = inputAlias.getText().toString();
        String aliasType = inputAliasType.getText().toString();
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(this, "请先输入alias", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(aliasType)) {
            Toast.makeText(this, "请先输入alias type", Toast.LENGTH_SHORT).show();
            return;
        }
        mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputAlias.setText("");
                        inputAliasType.setText("");
                    }
                });
            }
        });
    }



    private void deleteWeightedTag() {
        mPushAgent.getTagManager().deleteWeightedTags(new TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final Result result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            sharedPref.edit().putInt(WEIGHTED_TAG_REMAIN, result.remain).apply();
                            PushDialogFragment.newInstance(0, 1, getString(R.string.push_delete_success),
                                inputWeightedTag.getText().toString()).show(getFragmentManager(), "deleteWeightedTag");
                            inputWeightedTag.setText("");
                            inputWeightedTagValue.setText("");
                        } else {
                            PushDialogFragment.newInstance(0, 0, getString(R.string.push_delete_fail),
                                inputWeightedTag.getText().toString()).show(getFragmentManager(), "deleteWeightedTag");
                        }
                    }
                });
            }
        }, inputWeightedTag.getText().toString());
    }



    private void showTag() {
        mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final List<String> result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            StringBuilder info = new StringBuilder();
                            if (result != null) {
                                for (int i = 0; i < result.size(); i++) {
                                    String tag = result.get(i);
                                    info.append(tag);
                                    if (i != result.size() - 1) {
                                        info.append("、");
                                    }
                                }
                            }
                            PushDialogFragment.newInstance(1, 1, getString(R.string.push_get_tags),
                                info.toString()).show(getFragmentManager(), "deleteTag");
                        } else {
                            PushDialogFragment.newInstance(1, 0, getString(R.string.push_get_tags),
                                "").show(getFragmentManager(), "deleteTag");
                        }
                    }
                });
            }
        });
    }

    private void showWeightedTag() {
        mPushAgent.getTagManager().getWeightedTags(new WeightedTagListCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final Hashtable<String, Integer> result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            StringBuilder builder = new StringBuilder();
                            for (String key : result.keySet()) {
                                int value = result.get(key);
                                builder.append(getString(R.string.push_tag));
                                builder.append(key);
                                builder.append(" ");
                                builder.append(getString(R.string.push_tag_value));
                                builder.append(value);
                                builder.append("\n");
                            }
                            PushDialogFragment.newInstance(1, 1, getString(R.string.push_get_tags),
                                    builder.toString()).show(getFragmentManager(), "showWeightedTag");
                        } else {
                            PushDialogFragment.newInstance(1, 0, getString(R.string.push_get_tags),
                                    "").show(getFragmentManager(), "showWeightedTag");
                        }
                    }
                });
            }
        });
    }

    private void addTag() {
        final String tag = inputTag.getText().toString();
        if (TextUtils.isEmpty(tag)) {
            Toast.makeText(this, "请先输入tag", Toast.LENGTH_SHORT).show();
            return;
        }
        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputTag.setText("");
                        if (isSuccess) {
                            sharedPref.edit().putInt(TAG_REMAIN, result.remain).apply();
                            tagRemain.setText(String.valueOf(result.remain));
                            PushDialogFragment.newInstance(0, 1, getString(R.string.push_add_success), tag).show(
                                getFragmentManager(), "addTag");
                        } else {
                            PushDialogFragment.newInstance(0, 0, getString(R.string.push_add_fail), tag).show(
                                getFragmentManager(), "addTag");
                        }
                    }
                });
            }
        }, tag);
    }
    private void addWeightedTag() {
        if (TextUtils.isEmpty(inputWeightedTag.getText())) {
            Toast.makeText(this, "请输入weighted tag", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(inputWeightedTagValue.getText())) {
            Toast.makeText(this, "请输入value", Toast.LENGTH_SHORT).show();
            return;
        }
        final Hashtable<String, Integer> table = new Hashtable<String, Integer>();
        table.put(inputWeightedTag.getText().toString(), Integer.valueOf(inputWeightedTagValue.getText().toString()));
        mPushAgent.getTagManager().addWeightedTags(new TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final Result result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            sharedPref.edit().putInt(WEIGHTED_TAG_REMAIN, result.remain).apply();
                            PushDialogFragment.newInstance(0, 1, getString(R.string.push_add_success),
                                    inputWeightedTag.getText().toString() + "\n" + getString(R.string.push_tag_value) +
                                            inputWeightedTagValue.getText().toString()).show(getFragmentManager(),
                                    "addWeightedTag");
                            inputWeightedTag.setText("");
                            inputWeightedTagValue.setText("");
                        } else {
                            PushDialogFragment.newInstance(0, 0, getString(R.string.push_add_fail),
                                    inputWeightedTag.getText().toString() + "\n" + getString(R.string.push_tag_value) + inputWeightedTagValue.getText()
                                            .toString()).show(getFragmentManager(), "addWeightedTag");
                        }
                    }
                });
            }
        }, table);
    }
    private void deleteTag() {
        final String tag = inputTag.getText().toString();
        if (TextUtils.isEmpty(tag)) {
            Toast.makeText(this, "请先输入tag", Toast.LENGTH_SHORT).show();
            return;
        }
        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputTag.setText("");
                        if (isSuccess) {
                            sharedPref.edit().putInt(TAG_REMAIN, result.remain).apply();
                            tagRemain.setText(String.valueOf(result.remain));
                            PushDialogFragment.newInstance(0, 1,
                                    getString(R.string.push_delete_success), tag).show(getFragmentManager(), "deleteTag");
                        } else {
                            PushDialogFragment.newInstance(0, 0,
                                    getString(R.string.push_delete_fail), tag).show(getFragmentManager(), "deleteTag");
                        }
                    }
                });
            }
        }, tag);
    }

/*  每条插屏的消息格式
  {"success":"ok","data":{"t":1527768462305,"check_num":"","sduration":1,"pduration":1000,"card":
        {"msg_info":{"plain_text":{"action_type":"none","button_text":"确定","title":"123123","content":"123123123"},"display_button":false},
            "msg_id":"uaetrws152776828140600","msg_type":5,"policy":
            {"show_type":0,"show_times":0,"start_time":"2018-05-31 17:13:35","expire_time":"2018-06-07 17:13:35"}}}}*/
    private void showCardMessage() {

        InAppMessageManager.getInstance(this).showCardMessage(this, "bjfumain", new IUmengInAppMsgCloseCallback() {
            @Override
            public void onColse() {
                Log.i(TAG, "card message close");

            }
        });
    }

    private void serialnet() {
        DebugNotification.transmission(UpushActivity.this, handler);
    }

    private void deviceCheck() {
        String push_switch = UmengMessageDeviceConfig.isNotificationEnabled(this);
        String status;
        switch (push_switch) {
            case "true":
                status = "是";
                break;
            case "false":
                status = "否";
                break;
            default:
                status = push_switch;
                break;
        }

        String cpu = UmengMessageDeviceConfig.getCPU();

        String osVersion = android.os.Build.VERSION.RELEASE;
        String info = getString(R.string.push_os_version) + osVersion + "\n" + getString(R.string.push_cpu_info) + cpu
            + "\n" + getString(R.string.push_system_notification_switch) + status;
        PushDialogFragment.newInstance(1, 0, getString(
            R.string.push_device_check), info).show(getFragmentManager(), "deviceCheck");
    }

    private void deviceToken() {
        String pkgName = getApplicationContext().getPackageName();
        String info = String.format("DeviceToken:%s\n" + "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(this), UmengMessageDeviceConfig.getAppVersionName(this));
        Log.i("友盟推送","应用包名:" + pkgName + "\n" + info);
        PushDialogFragment.newInstance(1,0,"设备检查",info).show(getFragmentManager(),"deviceToken");
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            deviceToken();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext); // 基础指标统计，不能遗漏
    }

}
