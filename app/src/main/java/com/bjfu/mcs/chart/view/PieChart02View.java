
package com.bjfu.mcs.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.MathHelper;
import org.xclcharts.event.click.ArcPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotLegend;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @ClassName PieChart02View
 * @Description  平面饼图的例子
 * 
 */

public class PieChart02View extends DemoView implements Runnable {

	 private String TAG = "PieChart02View";
	 private PieChart chart = new PieChart();
	 private LinkedList<PieData> chartData = new LinkedList<PieData>();
	 Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	 
	 //private int mSelectedID = -1;
	
	 public PieChart02View(Context context) {
		super(context);
		initView();
	 }	
	
	 public PieChart02View(Context context, AttributeSet attrs){
        super(context, attrs);   
        initView();
	 }
	 
	 public PieChart02View(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	 }
	 
	 private void initView()
	 {
		 chartDataSet();	
		 chartRender();
		 
		//綁定手势滑动事件
			this.bindTouch(this,chart);
		 new Thread(this).start();
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
			//标签显示(隐藏，显示在中间，显示在扇区外面,折线注释方式)
			chart.setLabelStyle(XEnum.SliceLabelStyle.BROKENLINE);
			chart.getLabelBrokenLine().setLinePointStyle(XEnum.LabelLinePoint.END);
			chart.syncLabelColor();
			chart.syncLabelPointColor();
			
			
			//图的内边距
			//注释折线较长，缩进要多些
			int [] ltrb = new int[4];
			ltrb[0] = DensityUtil.dip2px(getContext(), 60); //left
			ltrb[1] = DensityUtil.dip2px(getContext(), 65); //top
			ltrb[2] = DensityUtil.dip2px(getContext(), 60); //right
			ltrb[3] = DensityUtil.dip2px(getContext(), 50); //bottom
											
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			
			//设定数据源
			//chart.setDataSource(chartData);												
		
			//标题
			chart.setTitle("一周活动比率");
			chart.addSubtitle("One week activity rate");
			//chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
				
			//隐藏渲染效果
			chart.hideGradient();
			//显示边框
			//chart.showRoundBorder();
			
			/*
			//激活点击监听
			chart.ActiveListenItemClick();
			chart.showClikedFocus();
			chart.disablePanMode();
			
			//显示图例
			PlotLegend legend = chart.getPlotLegend();	
			legend.show();
			legend.setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
			legend.setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			legend.showBox();
			*/
			chart.disablePanMode();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	private void chartDataSet()
	{				
		// 因为Java中Float和double的计算误差问题，所以建议
		//用图库中的MathHelper.getInstance()来做运算,以保证总值为100%
		
		//设置图表数据源				
		PieData pieData = new PieData("图书馆","图书馆:15%",15, Color.rgb(77, 83, 97)) ;
		pieData.setCustLabelStyle(XEnum.SliceLabelStyle.INSIDE, Color.WHITE);
		
		//pieData.setItemLabelRotateAngle(rotateAngle)
		
		chartData.add(pieData);
				
		chartData.add(new PieData("食堂","食堂(5%)",5, Color.rgb(75, 132, 1)));
		
		//将此比例块突出显示		
		PieData pd = new PieData("教室","教室:35%",35, Color.rgb(180, 205, 230));
		pd.setItemLabelRotateAngle(45.f);
		chartData.add(pd);
		
		PieData pdOther = new PieData("其它","其它",15, Color.rgb(148, 159, 181));
		pdOther.setCustLabelStyle(XEnum.SliceLabelStyle.INSIDE, Color.BLACK);
		chartData.add(pdOther);
		
		PieData pdTea = new PieData("操场","操场(30%)",30, Color.rgb(253, 180, 90),true);
		pdTea.setCustLabelStyle(XEnum.SliceLabelStyle.OUTSIDE, Color.rgb(253, 180, 90));
		chartData.add(pdTea);			
	}
	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		 try{
	            chart.render(canvas);
	        } catch (Exception e){
	        	Log.e(TAG, e.toString());
	        }
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		super.onTouchEvent(event);		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{						
			triggerClick(event.getX(),event.getY());
		}
		return true;
	}
	

	//触发监听
	private void triggerClick(float x,float y)
	{		
		if(!chart.getListenItemClickStatus())return;
		ArcPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;
		
		PieData pData = chartData.get(record.getDataID());
		
	//	boolean isInvaldate = true;		
		for(int i=0;i < chartData.size();i++)
		{	
			PieData cData = chartData.get(i);
			if(i == record.getDataID())
			{
				if(cData.getSelected()) 
				{
					//isInvaldate = false;
					break;
				}else{
					cData.setSelected(true);	
				}
			}else
				cData.setSelected(false);			
		}
		
		
		//显示选中框
		chart.showFocusArc(record,pData.getSelected());
		chart.getFocusPaint().setStyle(Style.STROKE);
		chart.getFocusPaint().setStrokeWidth(5);		
		chart.getFocusPaint().setColor(Color.GREEN);
		chart.getFocusPaint().setAlpha(100);
		
		
		//在点击处显示tooltip
		mPaintToolTip.setColor(Color.RED);
		chart.getToolTip().setCurrentXY(x,y);		
		chart.getToolTip().addToolTip(" key:" + pData.getKey() +
							" Label:" + pData.getLabel(),mPaintToolTip);	
											
		this.refreshChart();						
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {          
         	chartAnimation();         	
         }
         catch(Exception e) {
             Thread.currentThread().interrupt();
         }  
	}
	private void chartAnimation()
	{
		  try {       
			 
			  	float sum = 0.0f;
			  	int count = chartData.size();
	          	for(int i=0;i< count ;i++)
	          	{
	          		Thread.sleep(150);
	          	
	          		ArrayList<PieData> animationData = new ArrayList<PieData>();
	        
	          		sum = 0.0f;
	          			          		
	          		for(int j=0;j<=i;j++)
	          		{            			            			
	          			animationData.add(chartData.get(j));
	          			sum = (float) MathHelper.getInstance().add(
	          									sum , chartData.get(j).getPercentage());	          			
	          		}   		          		
	          			          			          				          				          	
	          		animationData.add(new PieData("","",  MathHelper.getInstance().sub(100.0f , sum),
	          											  Color.argb(1, 0, 0, 0)));
	          		chart.setDataSource(animationData);
	          	
	          		//激活点击监听
	    			if(count - 1 == i)
	    			{
	    				//chart.ActiveListenItemClick();
	    				//显示边框线，并设置其颜色
	    				//chart.getArcBorderPaint().setColor(Color.YELLOW);
	    				//chart.getArcBorderPaint().setStrokeWidth(3);
	    				
	    			
	    				
	    				//激活点击监听
	    				chart.ActiveListenItemClick();
	    				chart.showClikedFocus();
	    				chart.disablePanMode();
	    				
	    				//显示图例
	    				PlotLegend legend = chart.getPlotLegend();
	    				legend.show();
	    				legend.setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
	    				legend.setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
	    				legend.showBox();
	    				
	    			}
	    			
	          		postInvalidate();            				          	          	
	          }
			  
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }       
		  
	}
	
	 
}
