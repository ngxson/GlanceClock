package com.xson.glanceclock;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
* This is the class that is responsible for adding the filter on the
* screen: It works as a service, so that the view persists across all
* activities.
*
* @author Hathibelagal
*/
public class ClockService extends Service {
	
	public static SimpleDateFormat h12_format = new SimpleDateFormat( "hh" );
	public static SimpleDateFormat h24_format = new SimpleDateFormat( "HH" );
	public static SimpleDateFormat m_format = new SimpleDateFormat( "mm" );
	public static final String app = "ClockService";
	
	LinearLayout mView;
	LinearLayout mView1;
	SharedMemory shared;
	
	@Override
	public IBinder onBind(Intent i) {
		return null;
	}

	@Override
	public void onCreate() {
        super.onCreate();
        
    	Log.d(app, "Created");
    	
		Date date = new Date();
		String minutes;
		String hours;
		Boolean use24 = true;
        
        		if( use24 )
        		{
        			hours = h24_format.format( date );
        		} else
        		{
        			hours = h12_format.format( date );
        		}
        		
        		minutes = m_format.format( date );

        
		
    	shared=new SharedMemory(this);
        mView = new LinearLayout(this);                
    	mView.setBackgroundColor(0xff000000);

    	    mView.setOrientation(LinearLayout.VERTICAL);
    	    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.BOTTOM);
    	    params1.gravity = Gravity.BOTTOM;
    	    mView.setLayoutParams(params1);
    	    
    	    TextView ClockView = new TextView(this);
    	    LayoutParams textlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	    ClockView.setLayoutParams(textlp);
    	    ClockView.setTextAppearance(this, android.R.attr.textAppearanceLarge);
    	    ClockView.setTextSize(100.0F);
    	    ClockView.setTextColor(0xffffffff);
    	    ClockView.setText(hours + ":" + minutes);
    	    mView.addView(ClockView);
    	    
            WindowManager.LayoutParams params = new WindowManager.LayoutParams( 
            		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
            		WindowManager.LayoutParams.FLAG_FULLSCREEN ,                 
            		            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,               
            		            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            		            PixelFormat.TRANSLUCENT);
            
    		        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
    	        	wm.addView(mView, params);
    	        	Log.d(app, "Clock has been shown");
    	        	unTouchLayer();



}
	
	private void unTouchLayer() {
        mView1 = new LinearLayout(this);                
    	mView1.setBackgroundColor(0x00000000);
	    mView1.setOrientation(LinearLayout.VERTICAL);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams( 
        		WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, 
        		WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ,                 
        		            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,               
        		            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        		            PixelFormat.TRANSLUCENT);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
    	wm.addView(mView1, params);
    	Log.d(app, "Untouchable layer created!");
    	PowerManager.WakeLock WakeLock1 = ((PowerManager)getApplicationContext().getSystemService("power")).newWakeLock(268435482, "WAKEUP");
    	WakeLock1.acquire();
    	WakeLock1.release();
	}
	
	@Override
	public void onDestroy() {	
		super.onDestroy();
		if(mView!=null){
			WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
			wm.removeView(mView);
			wm.removeView(mView1);
		}
	}
}
