package com.bjfu.mcs.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bjfu.mcs.R;
import com.bjfu.mcs.chart.ChartsActivity;
import com.bjfu.mcs.chart.CircleChartActivity;
import com.bjfu.mcs.chart.ClickChartsActivity;
import com.bjfu.mcs.chart.DialChart2Activity;
import com.bjfu.mcs.chart.DialChart3Activity;
import com.bjfu.mcs.chart.DialChart4Activity;
import com.bjfu.mcs.chart.DialChartActivity;
import com.bjfu.mcs.chart.DySpActivity;
import com.bjfu.mcs.chart.GaugeChartActivity;
import com.bjfu.mcs.chart.HBARScrollActivity;
import com.bjfu.mcs.chart.HLNScrollActivity;
import com.bjfu.mcs.chart.SpinnerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartMainActivity extends AppCompatActivity {

    @BindView(R.id.chart_view)
    ListView chart_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_main);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("图表功能列表");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewsandData();

    }

    private void initViewsandData() {

        SimpleAdapter adapter = new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_2,
                new String[] { "title","description" },
                new int[] { android.R.id.text1, android.R.id.text2 });

        chart_view.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    android.view.View view, int position, long id) {
                // TODO Auto-generated method stub

                String chartsTitleCurr[] = getResources().getStringArray(R.array.chartsTitle);
                if(position > chartsTitleCurr.length - 1) return;

                Bundle bundleSimple = new Bundle();
                Intent intent = new Intent();
                bundleSimple.putString("title", chartsTitleCurr[position]);


                int id_desc_3_4 = chartsTitleCurr.length - 4;


                if(position == chartsTitleCurr.length - 1) //倒数1 仪表盘
                {
                    intent.setClass(ChartMainActivity.this,GaugeChartActivity.class);

                }else if(position == chartsTitleCurr.length - 2) //倒数2  圆/半圆图
                {
                    intent.setClass(ChartMainActivity.this,CircleChartActivity.class);
                    //}else if(position >= id_desc_1_2_3) //倒数1,2,3 seekbar图
                    //{
                    //	 position = chartsTitleCurr.length - 1 - position;
                    //	 intent.setClass(ChartMainActivity.this,SeekBarActivity.class);	
                }else if(position >= id_desc_3_4) //倒数3,4 同源汇总图
                {
                    position = chartsTitleCurr.length - 3 - position;
                    intent.setClass(ChartMainActivity.this,SpinnerActivity.class);
                }else if(position >= chartsTitleCurr.length - 5) //倒数5  scroll view line
                {
                    intent.setClass(ChartMainActivity.this,HLNScrollActivity.class);
                }else if(position >= chartsTitleCurr.length - 6) //倒数6  scroll view bar
                {
                    intent.setClass(ChartMainActivity.this,HBARScrollActivity.class);

                }else if(position >= chartsTitleCurr.length - 7) //倒数6  scroll view bar
                {
                    position = chartsTitleCurr.length - 7 - position;
                    intent.setClass(ChartMainActivity.this,ClickChartsActivity.class);

                }else if(position >= chartsTitleCurr.length - 8) //倒数8  dial chart
                {
                    position = chartsTitleCurr.length - 8 - position;
                    intent.setClass(ChartMainActivity.this,DialChartActivity.class);
                }else if(position >= chartsTitleCurr.length - 9) //倒数9  dial chart
                {
                    position = chartsTitleCurr.length - 9 - position;
                    intent.setClass(ChartMainActivity.this,DialChart2Activity.class);

                }else if(position >= chartsTitleCurr.length - 10) //倒数9  dial chart
                {
                    position = chartsTitleCurr.length - 10 - position;
                    intent.setClass(ChartMainActivity.this,DialChart3Activity.class);
                }else if(position >= chartsTitleCurr.length - 11) //倒数9  dial chart
                {
                    position = chartsTitleCurr.length - 11 - position;
                    intent.setClass(ChartMainActivity.this,DialChart4Activity.class);

                }else if(position >= chartsTitleCurr.length - 12) //倒数9  dial chart
                {
                    position = chartsTitleCurr.length - 12 - position;
                    intent.setClass(ChartMainActivity.this,DySpActivity.class);

                }else{
                    intent.setClass(ChartMainActivity.this,ChartsActivity.class);
                }
				 
				 /*
				 if(position >= chartsTitleCurr.length - 3) //倒数1,2,3 seekbar图
				 {
					 position = chartsTitleCurr.length - 1 - position;
					 intent.setClass(ChartMainActivity.this,SeekBarActivity.class);						
				 }else if(position >= chartsTitleCurr.length - 5) ////倒数4,5 同源汇总图
				 {
					 position = chartsTitleCurr.length - 4 - position;
					 intent.setClass(ChartMainActivity.this,SpinnerActivity.class);						
				 }else{				
					 intent.setClass(ChartMainActivity.this,ChartsActivity.class);					
				 }	
				 */

                bundleSimple.putInt("selected", position);
                intent.putExtras(bundleSimple);
                startActivity(intent);

            }
        };
        chart_view.setOnItemClickListener(listener);
    }

    private List<Map<String, String>> getData() {
        List<Map<String, String>> listData = new ArrayList<Map<String, String>>();

        String chartsTitle[] = getResources().getStringArray(R.array.chartsTitle);
        String chartsDesc[] = getResources().getStringArray(R.array.chartsDesc);

        int count = chartsDesc.length < chartsTitle.length?
                chartsDesc.length: chartsTitle.length;

        for(int i = 0; i< count; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("title",chartsTitle[i]);
            map.put("description",chartsDesc[i]);
            listData.add(map);
        }
        return listData;
    }
}
