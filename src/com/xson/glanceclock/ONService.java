package com.xson.glanceclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


public class ONService extends Service implements SensorEventListener {

	Sensor proxSensor;
	SensorManager sm;
	float mMaxRange;
	@Override
	public void onCreate() {//onCreat shouldn't be used for sensor u should use onStartCommand
	    //Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
	    
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {//here u should unregister sensor
	   // Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
		Log.d("bridge", "stopped");
	    sm.unregisterListener(this);
	}

	@Override//here u should register sensor and write onStartCommand not onStart
	public int onStartCommand(Intent intent, int flags, int startId) {
	   // Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		
		Log.d("bridge", "started");
	    sm=(SensorManager)getSystemService(SENSOR_SERVICE);
	    proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	    sm.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
	    mMaxRange = proxSensor.getMaximumRange();
	    
	        //then you should return sticky
	        return Service.START_STICKY;
	}
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(ONService.this, ClockService.class);
	    if(arg0.values[0] == 0){
	        //stopService(i);
	    } else {
	    	startService(i);
	    }
		
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

        
}