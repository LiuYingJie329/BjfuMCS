package com.bjfu.mcs.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.bjfu.mcs.R;
import com.bjfu.mcs.fragment.MainBaseFrag;
import com.bjfu.mcs.loginSign.LoginActivity;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.xdandroid.hellodaemon.IntentWrapper;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);


        IntentWrapper.whiteListMatters(this, "群智感知系统服务持续运行");


        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.first_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.img_office)
                        .title("欢迎来到移动群智感知系统")
                        .description("进一步了解吧！")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("我们准备新功能等你来尝试");
                    }
                }, "下一步"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.second_slide_background)
                .buttonsColor(R.color.second_slide_buttons)
                .title("想了解更多?")
                .description("GO ON")
                .build());

        addSlide(new CustomSlide());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.third_slide_background)
                        .buttonsColor(R.color.third_slide_buttons)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .neededPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.CHANGE_WIFI_STATE,
                                Manifest.permission.CHANGE_CONFIGURATION,
                                Manifest.permission.INTERNET,
                                Manifest.permission.WAKE_LOCK,
                                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_SETTINGS,
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.SYSTEM_ALERT_WINDOW,
                                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                                Manifest.permission.SYSTEM_ALERT_WINDOW,
                                Manifest.permission.READ_LOGS,
                                Manifest.permission.BROADCAST_STICKY,
                                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                                Manifest.permission.PROCESS_OUTGOING_CALLS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.CAMERA,})
                        .image(R.drawable.img_equipment)
                        .title("App提供最好用的功能")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("进一步了解吧!");
                    }
                }, "下一步"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.fourth_slide_background)
                .buttonsColor(R.color.fourth_slide_buttons)
                .title("开启移动群智感知新生活吧！")
                .description("欢迎你加入我们")
                .build());

    }


    @Override
    public void onFinish() {
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        super.onFinish();
    }
}