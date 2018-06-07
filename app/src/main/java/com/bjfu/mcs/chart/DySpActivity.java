
package com.bjfu.mcs.chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;

import com.bjfu.mcs.R;
import com.bjfu.mcs.chart.view.DySplineChart;

import org.xclcharts.chart.PointD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class DySpActivity extends Activity {

	private Timer mTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dy_sp);
		
		this.setTitle("双线图");
				
		mTimer = new Timer();
        setTimerTask();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.dy_sp, menu);
//		return true;
//	}
	
	 @Override
	    protected void onDestroy() {  
	        super.onDestroy();  
	        // cancel timer  
	        mTimer.cancel();  
	    }  
	    
	    private void setTimerTask() {
	        mTimer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                Message message = new Message();
	                message.what = 1;
	                doActionHandler.sendMessage(message);
	            }
	        }, 1000, 1000/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);
	    }

	    /**
	     * do some action
	     */
	    @SuppressLint("HandlerLeak")
		private Handler doActionHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            int msgId = msg.what;
	            switch (msgId) {
	                case 1:
	                	charts();                	
	                    break;
	                default:
	                    break;
	            }
	        }
	    };
	    
	    List<PointD> linePoint1 = new ArrayList<PointD>();
	    List<PointD> linePoint2 = new ArrayList<PointD>();
	    
	    private void charts(){
	    	DySplineChart chartView = (DySplineChart)findViewById(R.id.ln_view); 
	    	
	    	
		    int minY = 30;
		    int maxY = 100;
		    
		    int minX = 10;
		    int maxX = 50;
		    
			Random random = new Random();
			
			linePoint1.clear();
			linePoint2.clear();
			
			
			double x = random.nextInt(maxX)%(maxX-minX+1) + minX;		
			double y = random.nextInt(maxY)%(maxY-minY+1) + minY;	
	        
			for(int i=0;i<5;i++)
			{
				//x +=i*10;	
				//y +=i*10;
				
				x += 12;
				y += 10;
				
				//linePoint1.add(new PointD(x + 50, y+50));
				linePoint1.add(new PointD(x, y));
			}
			
			linePoint1.add(new PointD(45, 70));
			linePoint1.add(new PointD(50, 90));
			linePoint1.add(new PointD(65, 60));
	
			linePoint2.add(new PointD(5, 10));
			linePoint2.add(new PointD(18, 65));
			linePoint2.add(new PointD(20, 55));
			linePoint2.add(new PointD(40, 70));
					
			chartView.refreshChart(linePoint1, linePoint2);		
			
	    }
	    

}
