package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bjfu.mcs.R;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.fragment.MainBaseFrag;
import com.bjfu.mcs.fragment.ZuJiFragment;
import com.bjfu.mcs.utils.ConfigKey;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

import static com.bjfu.mcs.application.MCSApplication.appcache;

public class ZuJiActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener,ZuJiFragment.OnMenuBtnClickListener{

    private ZuJiActivity mContext;
    private ViewPager mViewPager;
    private RadioGroup mRg;
    private MainFPagerAdaper mainFPagerAdaper;

    // 标示了当前位置
    private final int POS_ONE = 0;
    // 是否已经展示过Fragment，默认是false，没有展示过
    private boolean[] hasFragSelected = new boolean[POS_ONE + 1];
    private MainBaseFrag[] mainBaseFrags = new MainBaseFrag[POS_ONE + 1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_ji);
        ButterKnife.bind(this);
        mContext = this;
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的足迹");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init();

        int pos = 0;

        try {
            String posStr = appcache.getAsString(ConfigKey.KEY_MAIN_TAB_POS);
            if(!TextUtils.isEmpty(posStr))
                pos = Integer.parseInt(posStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        initSelectTab(pos);
        sendFragmentFirstSelectedMsg(pos);

    }

    private void init() {
        initView();
        initData();
        addListner();
    }

    private void addListner() {
        mViewPager.setOnPageChangeListener(this);
        mRg.setOnCheckedChangeListener(this);

    }

    private void initData() {
        mainFPagerAdaper = new MainFPagerAdaper(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mainFPagerAdaper);
        mHandler.sendEmptyMessageDelayed(MSG_QUERY_MESSAGE_COUNT,1500);
    }

    private void initView() {
        mViewPager =  findViewById(R.id.mviewpager);
        mRg = findViewById(R.id.mnc_rg);
//        final PersonInfo piInfo = BmobUser.getCurrentUser(PersonInfo.class);
//        if(piInfo == null)
//            return;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        RadioButton rb = findRadioButtonByPos(position);
        if (rb != null) {
            rb.setChecked(true);
        }
        appcache.put(ConfigKey.KEY_MAIN_TAB_POS, position+"");
        sendFragmentFirstSelectedMsg(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbnt_zuji:
                if (POS_ONE != mViewPager.getCurrentItem()) {
                    mViewPager.setCurrentItem(POS_ONE, false);
                }
                break;
        }
    }

    private void initSelectTab(int pos) {
        if (pos < POS_ONE || pos > POS_ONE)
            return;
        RadioButton rb = findRadioButtonByPos(pos);
        if (rb != null) {
            rb.setChecked(true);
        }
        mViewPager.setCurrentItem(pos, false);
    }

    private RadioButton findRadioButtonByPos(int position) {
        switch (position) {
            case POS_ONE:
                return (RadioButton) mRg.findViewById(R.id.rbnt_zuji);
        }
        return null;
    }
    private void sendFragmentFirstSelectedMsg(int position) {
        if(false == hasFragSelected[position]) {
            hasFragSelected[position] = true;
            int msgWhat = position + 1000; // 消息和位置的规则为相差1000
            FragmentSelectedHandler.sendEmptyMessageDelayed(msgWhat, 10);
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler FragmentSelectedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int position = msg.what - 1000;
            if(position ==POS_ONE ) {
                if(mainBaseFrags[position] == null) {
                    this.removeMessages(msg.what);
                    this.sendEmptyMessageDelayed(msg.what, 10);
                } else {
                    mainBaseFrags[position].onFragmentFirstSelected();
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void OpenLeftMenu() {

    }

    /**
     * 页面适配器start
     */
    public class MainFPagerAdaper extends FragmentPagerAdapter {

        public MainFPagerAdaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment retFragment = null;
            switch (position) {
                case 0:
                    retFragment = new ZuJiFragment();
                    mainBaseFrags[0] = (ZuJiFragment) retFragment;
                    break;
            }
            return retFragment;
        }

        @Override
        public int getCount() {
            return POS_ONE + 1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }

    private final int MSG_QUERY_MESSAGE_COUNT = 101;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_QUERY_MESSAGE_COUNT:
                    Log.i("新消息通知","新消息通知");
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
