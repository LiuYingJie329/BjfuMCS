package com.bjfu.mcs.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bjfu.mcs.R;
import com.bjfu.mcs.adapter.InfoEntity;
import com.bjfu.mcs.adapter.SectionsPagerAdapter;
import com.bjfu.mcs.base.BaseActivity;
import com.tencent.bugly.crashreport.CrashReport;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<InfoEntity> infoEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("基本设置");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         *地图坐标
         * 116.346639 , 40.006374
         * 116.346639 , 40.006374
         * 116.346553 , 40.009707
         * 116.348121 , 40.013282
         * 116.349679 , 40.013241
         * 116.349908 , 40.009013
         * 116.352743 , 40.00912
         * 116.352594 , 40.013389
         * 116.355487 , 40.01341
         * 116.355711 , 40.009431
         * 116.355658 , 40.008122
         * 116.355658 , 40.006595
         */

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        infoEntities.add(new InfoEntity(getResources().getString(R.string.about), "0"));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.setting), "1"));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.beijing), "2"));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.book), "3"));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.notifyTitle), "4"));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.book_countdown), "5"));
        mSectionsPagerAdapter.init(infoEntities);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // 若设置了该属性 则viewpager会缓存指定数量的Fragment
        // mViewPager.setOffscreenPageLimit(VIEWPAGER_OFF_SCREEN_PAGE_LIMIT);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


}
