package com.bjfu.mcs.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.xclcharts.chart.PieChart3D;
import org.xclcharts.chart.PieData;
import org.xclcharts.event.click.ArcPosition;
import org.xclcharts.renderer.XEnum;

import java.util.LinkedList;

/**
 * @ClassName Pie3DChart01View
 * @Description  3D饼图的例子
 */
public class PieChart3D01View extends DemoView implements Runnable {

	private String TAG = "Pie3DChart01View";
	private PieChart3D chart = new PieChart3D();
	
	private LinkedList<PieData> chartData = new LinkedList<PieData>();
	//Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public PieChart3D01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public PieChart3D01View(Context context, AttributeSet attrs){
        super(context, attrs);   
        initView();
	 }
	 
	 public PieChart3D01View(Context context, AttributeSet attrs, int defStyle) {
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
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			//设定数据源
			//chart.setDataSource(chartData);		
			
			//标题
			//chart.setTitle("个人专业技能分布");
			//chart.addSubtitle("(XCL-Charts Demo)");
			//chart.getPlotTitle().setTitlePosition(XEnum.Position.LOWER);
			
			//不显示key
			chart.getPlotLegend().hide();
			
			//标签文本显示为白色
			chart.getLabelPaint().setColor(Color.WHITE);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//设置图表数据源			
		//PieData(标签，百分比，在饼图中对应的颜色)
		
		chartData.add(new PieData("test","PHP(15%)",15,
								Color.rgb(1, 170, 255)));
		chartData.add(new PieData("Other",10,
								Color.rgb(148, 42, 133),false));
		chartData.add(new PieData("Oracle",40, Color.rgb(241, 62, 1)));
		chartData.add(new PieData("Java",15, Color.rgb(242, 167, 69)));
		
		//将此比例块突出显示
		chartData.add(new PieData("C++(20%)",20,
								Color.rgb(164, 233, 0),true));
	
		
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
           chart.render(canvas);       
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
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
			 //设置数据源
			  chart.setDataSource(chartData);
			  
			  for(int i=10;i>0;i--)
			  {				 
				  Thread.sleep(100);
				 // chart.setChartRange(0.0f, 0.0f,getScreenWidth()/i,getScreenHeight()/i);				  
				  chart.setChartRange(0.0f, 0.0f,this.getWidth()/i,this.getHeight()/i);				  
				  
				  if(1 == i)
				  {
					    //最末显示标题
						chart.setTitle("个人专业技能分布");
						chart.addSubtitle("(XCL-Charts Demo)");
						chart.setTitleVerticalAlign(XEnum.VerticalAlign.BOTTOM);
						chart.setChartRange(0.0f, 0.0f,getWidth(),getHeight());
						
						//激活点击监听
						chart.ActiveListenItemClick();			
					
						/*
						chart.getPlotLegend().show();
						chart.getPlotLegend().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
						chart.getPlotLegend().setHorizontalAlign(XEnum.HorizontalAlign.CENTER);	
						chart.getPlotLegend().hideBox();
						*/
				  }
				  postInvalidate(); 
			  }
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		//super.onTouchEvent(event);		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{						
			triggerClick(event.getX(),event.getY());
		}
		return true;
	}
	

	//触发监听
	private void triggerClick(float x,float y)
	{		
		
		ArcPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;
		
		PieData pData = chartData.get(record.getDataID());
		Toast.makeText(this.getContext(),
				" key:" +  pData.getKey() +
				" Label:" + pData.getLabel() ,
				Toast.LENGTH_SHORT).show();
		/*
		//在点击处显示tooltip
		mPaintToolTip.setColor(Color.RED);			
		chart.getToolTip().setCurrentXY(x,y);		
		chart.getToolTip().addToolTip(" key:" +  pData.getKey() +
										" Label:" + pData.getLabel(),mPaintToolTip);	
		this.invalidate();*/
	}
	
}
