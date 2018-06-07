package com.bjfu.mcs.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bjfu.mcs.R;
import com.bjfu.mcs.adapter.InfoEntity;
import com.bjfu.mcs.adapter.SectionsPagerAdapter;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.SystemActivityInfo;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.tencent.bugly.crashreport.CrashReport;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class SettingActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<InfoEntity> infoEntities = new ArrayList<>();
    private String done;
    private String doing;
    private String todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("任务情况");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getdata();
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

        PersonInfo personInfo = BmobUser.getCurrentUser(PersonInfo.class);
        BmobQuery<SystemActivityInfo> bmobQuery = new BmobQuery<SystemActivityInfo>();
        bmobQuery.addQueryKeys("usertodo,userdone,userdoing,syscontent");
        bmobQuery.findObjects(new FindListener<SystemActivityInfo>() {
            @Override
            public void done(List<SystemActivityInfo> list, cn.bmob.v3.exception.BmobException e) {
                if(e == null & list.size() != 0){
                    RxToast.success("查询成功");
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getUserdone() !=null && list.get(i).getUserdone().contains(personInfo.getUsername())){
                            done = done+list.get(i).getSyscontent();
                            Log.i("已经完成的任务",done);
                        }
                        if(list.get(i).getUsertodo() != null && list.get(i).getUsertodo().contains(personInfo.getUsername())){
                            todo = todo+list.get(i).getSyscontent();
                            Log.i("准备去做的任务",todo);
                        }
                        if(list.get(i).getUserdoing() != null && list.get(i).getUserdoing().contains(personInfo.getUsername())){
                            doing = doing+list.get(i).getSyscontent();
                            Log.i("正在进行的任务",doing);
                        }
                    }
                }
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        infoEntities.add(new InfoEntity(getResources().getString(R.string.donetask), "当前已完成任务:"+done));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.doingtask), "正在进行移动群智感知任务，任务内容："+doing));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.untask), "未完成任务:"+todo));
        mSectionsPagerAdapter.init(infoEntities);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // 若设置了该属性 则viewpager会缓存指定数量的Fragment
        // mViewPager.setOffscreenPageLimit(VIEWPAGER_OFF_SCREEN_PAGE_LIMIT);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    private void  getdata(){
        done="升级测试";
        todo="推送测试";
        doing="插屏测试";
    }

}
