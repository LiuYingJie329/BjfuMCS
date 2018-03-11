package com.bjfu.mcs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.ChildData;
import com.bjfu.mcs.bean.MergeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ExcelActivity extends AppCompatActivity {

    private SmartTable<MergeInfo> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        ButterKnife.bind(this);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
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

        List<MergeInfo> list = new ArrayList<>();
        for(int i = 0;i <50; i++) {
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
        }
        table = (SmartTable<MergeInfo>) findViewById(R.id.table);
        table.setData(list);
        table.getConfig().setShowTableTitle(false);
        table.setZoom(true,2,0.2f);
    }
}
