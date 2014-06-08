package com.xson.glanceclock;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	protected static final String app = "MainActivity";
	protected CheckBox mShowNoti;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	    	stopServiceIfActive();
	    	
    		Intent i=new Intent(MainActivity.this, ClockService.class);
    		stopService(i);
    		
    		
    		
		  setContentView(R.layout.activity_main);
	}
	

	
    private void stopServiceIfActive(){
    	//if(MainService.STATE == MainService.ACTIVE){
    		//Intent i=new Intent(MainActivity.this, MainService.class);
    		//stopService(i);
    	//}
    		Intent i=new Intent(MainActivity.this, MainService.class);
    		stopService(i);
        	stopService(new Intent(getApplicationContext(), ONService.class));
    }
    
    public void okie(View v) {
    	//Intent i=new Intent(MainActivity.this, MainService.class);
        //startService(i);
        //debug
    	Intent i=new Intent(MainActivity.this, MainService.class);
        startService(i);
        
		Log.d(app, "pressed");
		//Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.startSer),
		//		   Toast.LENGTH_SHORT).show();
		finish();
    }

}
