package com.bjfu.mcs.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.PieData;
import org.xclcharts.chart.RoseChart;
import org.xclcharts.renderer.XEnum;

import java.util.LinkedList;

/**
 * @ClassName RoseChart01View
 * @Description  南丁格尔玫瑰图 的例子
 */
public class RoseChart01View extends DemoView {

	private String TAG = "RoseChart01View";
	private RoseChart chart = new RoseChart();
	
	LinkedList<PieData> roseData = new LinkedList<PieData>();
	
	public RoseChart01View(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			initView();
	}
	
	public RoseChart01View(Context context, AttributeSet attrs){
        	super(context, attrs);   
        	initView();
	 }
	 
	 public RoseChart01View(Context context, AttributeSet attrs, int defStyle) {
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
	 
	 
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  	
	
	private void chartRender()
	{
		try {						
			
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			//背景 
			chart.setApplyBackgroundColor(true);
			chart.setBackgroundColor(Color.BLACK);
			
			//数据源
			chart.setDataSource(roseData);							
				
			//设置标题
			chart.setTitle("南丁格尔玫瑰图");
			chart.addSubtitle("(XCL-Charts)");
			chart.getPlotTitle().getTitlePaint().setColor(Color.WHITE);
			chart.getPlotTitle().getSubtitlePaint().setColor(Color.WHITE);
			
			//设置标签显示位置,当前设置标签显示在扇区中间
			chart.setLabelStyle(XEnum.SliceLabelStyle.OUTSIDE);
									
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//设置图表数据源
							
		//PieData(标签，百分比，在饼图中对应的颜色)
		roseData.add(new PieData("PostgreSQL",40, Color.rgb(77, 83, 97) ));
		roseData.add(new PieData("Sybase"	 ,50, Color.rgb(148, 159, 181)));
		roseData.add(new PieData("DB2"		 ,60, Color.rgb(253, 180, 90)));
		roseData.add(new PieData("国产及其它"	 ,35, Color.rgb(52, 194, 188)));
		roseData.add(new PieData("SQL Server",70, Color.rgb(39, 51, 72)));
		roseData.add(new PieData("DB2"		 ,80, Color.rgb(255, 135, 195)));
		roseData.add(new PieData("Oracle"    ,90, Color.rgb(215, 124, 124)));
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
        	canvas.drawColor(Color.BLACK);
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

}
