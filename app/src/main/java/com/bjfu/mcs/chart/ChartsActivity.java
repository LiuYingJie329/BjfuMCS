
package com.bjfu.mcs.chart;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bjfu.mcs.R;
import com.bjfu.mcs.activity.AboutActivity;
import com.bjfu.mcs.chart.view.ArcLineChart01View;
import com.bjfu.mcs.chart.view.AreaChart01View;
import com.bjfu.mcs.chart.view.AreaChart02View;
import com.bjfu.mcs.chart.view.AreaChart03View;
import com.bjfu.mcs.chart.view.BarChart01View;
import com.bjfu.mcs.chart.view.BarChart02View;
import com.bjfu.mcs.chart.view.BarChart03View;
import com.bjfu.mcs.chart.view.BarChart04View;
import com.bjfu.mcs.chart.view.BarChart05View;
import com.bjfu.mcs.chart.view.BarChart06View;
import com.bjfu.mcs.chart.view.BarChart08View;
import com.bjfu.mcs.chart.view.BarChart09View;
import com.bjfu.mcs.chart.view.BarChart10View;
import com.bjfu.mcs.chart.view.BarChart11View;
import com.bjfu.mcs.chart.view.BarChart12View;
import com.bjfu.mcs.chart.view.BarChart13View;
import com.bjfu.mcs.chart.view.BarChart3D01View;
import com.bjfu.mcs.chart.view.BarChart3D02View;
import com.bjfu.mcs.chart.view.BubbleChart01View;
import com.bjfu.mcs.chart.view.CircleChart04View;
import com.bjfu.mcs.chart.view.DemoView;
import com.bjfu.mcs.chart.view.DountChart01View;
import com.bjfu.mcs.chart.view.FunnelChart01View;
import com.bjfu.mcs.chart.view.FunnelChart02View;
import com.bjfu.mcs.chart.view.FunnelChart201View;
import com.bjfu.mcs.chart.view.LineChart01View;
import com.bjfu.mcs.chart.view.LineChart02View;
import com.bjfu.mcs.chart.view.MultiAxisChart01View;
import com.bjfu.mcs.chart.view.MultiAxisChart02View;
import com.bjfu.mcs.chart.view.MultiAxisChart03View;
import com.bjfu.mcs.chart.view.MultiBarChart01View;
import com.bjfu.mcs.chart.view.PieChart01View;
import com.bjfu.mcs.chart.view.PieChart02View;
import com.bjfu.mcs.chart.view.PieChart3D01View;
import com.bjfu.mcs.chart.view.QuadrantChart01View;
import com.bjfu.mcs.chart.view.RadarChart01View;
import com.bjfu.mcs.chart.view.RadarChart02View;
import com.bjfu.mcs.chart.view.RadarChart03View;
import com.bjfu.mcs.chart.view.RangeBarChart01View;
import com.bjfu.mcs.chart.view.RoseChart01View;
import com.bjfu.mcs.chart.view.ScatterChart01View;
import com.bjfu.mcs.chart.view.SplineChart01View;
import com.bjfu.mcs.chart.view.SplineChart02View;
import com.bjfu.mcs.chart.view.SplineChart03View;
import com.bjfu.mcs.chart.view.SplineChart04View;
import com.bjfu.mcs.chart.view.SplineChart05View;
import com.bjfu.mcs.chart.view.StackBarChart01View;
import com.bjfu.mcs.chart.view.StackBarChart02View;


public class ChartsActivity extends Activity {
	
	//private ZoomControls mZoomControls;
	private int mSelected = 0;
	
	private DemoView[] mCharts ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置没标题
		
		mCharts = new DemoView[]{
			new BarChart01View(this) ,	//竖向柱形图
			new BarChart02View(this),	//横向柱形图
			new BarChart05View(this),	//横向定制线柱形图
			new BarChart03View(this),	//竖向定制线柱形图
			new BarChart04View(this),	//高密度柱形图
			
			new BarChart3D01View(this),	//竖向3D柱形图
			new BarChart3D02View(this),	//横向3D柱形图
			
			new BarChart08View(this),	//正负背向式图
			new BarChart09View(this),	//正负背向式图(横向)
			
			new BarChart10View(this),   // 双轴柱形图
			new BarChart11View(this),   // 顶轴横向柱形图
			new BarChart12View(this),   // 圆角柱形图
			new BarChart13View(this),   // 圆角柱形图(横向)
			
			
			new StackBarChart01View(this),	//竖向堆积柱形图
			new StackBarChart02View(this),	//横向堆积柱形图
			new LineChart01View(this),	//折线图(封闭式)
			new LineChart02View(this),	//折线图(开放式)
			new SplineChart03View(this),	//平滑曲线图
			new SplineChart01View(this),	//普通曲线图
			new SplineChart02View(this),	//三角函数曲线图
			new SplineChart04View(this),  // github
			new SplineChart05View(this),
			new AreaChart01View(this),	//区域图
			new AreaChart02View(this),	//平滑区域图
			new AreaChart03View(this),	//
			
			new MultiAxisChart01View(this),
			new MultiAxisChart02View(this),
			new MultiBarChart01View(this),	//多柱形堆积效果图
			new MultiAxisChart03View(this),	//面积折线混合图
			
			new PieChart01View(this),	//饼图
			new PieChart02View(this),
			new PieChart3D01View(this),	//3D饼图
			new DountChart01View(this),	//环形图
			new RoseChart01View(this),	//南丁格尔玫瑰图
			new RadarChart01View(this),	//蜘蛛网雷达图
			new RadarChart02View(this),	//圆形雷达图
			new RadarChart03View(this),	//玫瑰风向图
			new BarChart06View(this),
			new ArcLineChart01View(this),	//弧线比较图
			new ScatterChart01View(this),	//散点图
			new BubbleChart01View(this),	//气泡图
			new RangeBarChart01View(this), 	//范围条形图
			new QuadrantChart01View(this),	//象限图
			
			new FunnelChart01View(this), //漏斗图(Desc)
			new FunnelChart02View(this), //漏斗图(Asc)
			new CircleChart04View(this), //圆形图
			new FunnelChart201View(this) //漏斗图(另一种风格)
		}; 
				
		Bundle bunde = this.getIntent().getExtras();
		mSelected = bunde.getInt("selected");  
		String title = bunde.getString("title");
		
		if(mSelected > mCharts.length - 1){									
			setContentView(R.layout.activity_charts);
			this.setTitle(Integer.toString(mSelected));
		}else{			
	        initActivity();
			this.setTitle(title);
		}
						
	}
	
	private void initActivity()
	{
		   //图表的使用方法:
		   //使用方式一:
		   // 1.新增一个Activity
		   // 2.新增一个View,继承Demo中的GraphicalView或DemoView都可，依Demo中View目录下例子绘制图表.
		   // 3.将自定义的图表View放置入Activity对应的XML中，将指明其layout_width与layout_height大小.
		   // 运行即可看到效果. 可参考非ChartsActivity的那几个图的例子，都是这种方式。
		
 		  //使用方式二:
		   //代码调用 方式有下面二种方法:
		   //方法一:
		   //在xml中的FrameLayout下增加图表和ZoomControls,这是利用了现有的xml文件.
   		   // 1. 新增一个View，绘制图表.
 		   // 2. 通过下面的代码得到控件，addview即可
	       //LayoutInflater factory = LayoutInflater.from(this);
	       //View content = (View) factory.inflate(R.layout.activity_multi_touch, null);     
	     
			
			//方法二:
			//完全动态创建,无须XML文件.
	       FrameLayout content = new FrameLayout(this);
	       
	       //缩放控件放置在FrameLayout的上层，用于放大缩小图表
		   FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
		   LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		   frameParm.gravity = Gravity.BOTTOM| Gravity.RIGHT;
		
		   /*
		  //缩放控件放置在FrameLayout的上层，用于放大缩小图表
	       mZoomControls = new ZoomControls(this);
	       mZoomControls.setIsZoomInEnabled(true);
	       mZoomControls.setIsZoomOutEnabled(true);	  
		   mZoomControls.setLayoutParams(frameParm);  
		   */
		   
		   //图表显示范围在占屏幕大小的90%的区域内
		   DisplayMetrics dm = getResources().getDisplayMetrics();
		   int scrWidth = (int) (dm.widthPixels * 0.9); 	
		   int scrHeight = (int) (dm.heightPixels * 0.9); 			   		
	       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
	    		   													scrWidth, scrHeight);
	       
	       //居中显示
           layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
           //图表view放入布局中，也可直接将图表view放入Activity对应的xml文件中
           final RelativeLayout chartLayout = new RelativeLayout(this);
      
           chartLayout.addView( mCharts[mSelected], layoutParams);
  
	        //增加控件
		   ((ViewGroup) content).addView(chartLayout);
		   //((ViewGroup) content).addView(mZoomControls);
		    setContentView(content);		   	       
		    //放大监听
		  //  mZoomControls.setOnZoomInClickListener(new OnZoomInClickListenerImpl());
		    //缩小监听
		  //  mZoomControls.setOnZoomOutClickListener(new OnZoomOutClickListenerImpl());  		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "帮助");
        menu.add(Menu.NONE, Menu.FIRST + 2, 0, "关于XCL-Charts");
		return true;
	}

	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        super.onOptionsItemSelected(item);
	        switch(item.getItemId())
	        {
	        case Menu.FIRST+1:
	        	//String chartsHelp[] = getResources().getStringArray(R.array.chartsHelp);	        
	        	//String URL = chartsHelp[mSelected]; 	        	
	        	String URL =getResources().getString(R.string.helpurl);
		        Uri uri = Uri.parse(URL);
		        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
		        startActivity(intent2);  
		        finish();
	            break;
	        case Menu.FIRST+2:
		        Intent intent = new Intent();
	    		intent.setClass(ChartsActivity.this,AboutActivity.class);
	    		startActivity(intent); 	        
	            break;
	        }
	        return true;
	    }
	 
	 
	 /*
	 private class OnZoomInClickListenerImpl implements OnClickListener {

	        @Override
	        public void onClick(View view) {	        	
	        	mCharts[mSelected].zoomIn();
	        }
	    }

	    private class OnZoomOutClickListenerImpl implements OnClickListener {

	        @Override
	        public void onClick(View view) {
	        	mCharts[mSelected].zoomOut();
	        }
	    }
	    
	 */
	 
}
