
package com.bjfu.mcs.chart;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bjfu.mcs.R;
import com.bjfu.mcs.activity.AboutActivity;
import com.bjfu.mcs.chart.view.DialChart03View;
import com.bjfu.mcs.chart.view.DialChart06View;

import java.util.Random;

public class DialChart3Activity extends Activity {

	DialChart03View chart = null;
	DialChart06View chart6 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dial_chart3);
		
		chart = (DialChart03View)findViewById(R.id.circle_view); 
		chart6 = (DialChart06View)findViewById(R.id.circle_view2); 
		
		final Button button = (Button)findViewById(R.id.button1);
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int max = 100;
			    int min = 1;
				Random random = new Random();
				int p = random.nextInt(max)%(max-min+1) + min;					
				float pf = p / 100f;
				chart.setCurrentStatus(pf);
				chart.invalidate();
				
				chart6.setCurrentStatus(pf);
				chart6.invalidate();
			}
			
		  }
		);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "帮助");
        menu.add(Menu.NONE, Menu.FIRST + 2, 0, "关于Charts");
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
	    		intent.setClass(DialChart3Activity.this,AboutActivity.class);
	    		startActivity(intent); 	        
	            break;
	        }
	        return true;
	    }

}
