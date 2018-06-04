/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 2.1
 */
package com.bjfu.mcs.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.FunnelChart;
import org.xclcharts.chart.FunnelData;
import org.xclcharts.renderer.XEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FunnelChart01View
 * @Description  漏斗图例子 
 *
 */
public class FunnelChart01View extends DemoView {
	
	private String TAG = "FunnelChart01View";
	private FunnelChart chart = new FunnelChart();
	private List<FunnelData> chartData = new ArrayList<FunnelData>();
	
	public FunnelChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public FunnelChart01View(Context context, AttributeSet attrs){
        super(context, attrs);   
        initView();
	 }
	 
	 public FunnelChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	chartDataSet();	
			chartRender();
			
			//綁定手势滑动事件
			this.bindTouch(this,chart);
	 }
	 
	 
	 private void chartRender()
		{
			try {
				
				//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
				int [] ltrb = getBarLnDefaultSpadding();
				chart.setPadding(ltrb[0],ltrb[1], ltrb[2],  ltrb[3]);	
			
						
				//标题
				chart.setTitle("营销漏斗");
				chart.addSubtitle("(XCL-Charts Demo)");	
				//数据源
				chart.setDataSource(chartData);
				
				chart.showBorder();
		
				chart.setSortType(XEnum.SortType.DESC);
				
				//chart.setSortType(XEnum.SortType.ASC);
				
				chart.setLabelAlign(XEnum.HorizontalAlign.LEFT);
				
				chart.syncLabelLineColor();
		
				chart.setPlotWidthPercent(60.f);
				
				chart.getFunnelLinePaint().setColor(Color.WHITE);
				chart.getLabelPaint().setTextSize(18);
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	private void chartDataSet()
	{	
		 
		 FunnelData fd1 = new FunnelData("客户意识",80, Color.RED);
		 chartData.add(fd1);
		 chartData.add(new FunnelData("考量方面",60, Color.rgb(243, 75, 125)));
		 chartData.add(new FunnelData("客户喜好",40, Color.rgb(77, 180, 123)));
		 chartData.add(new FunnelData("客户体验历程",100, Color.BLUE));
		 chartData.add(new FunnelData("诉诸行动",20, Color.rgb(117, 197, 141)));
	}
	 
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
            
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }


}
