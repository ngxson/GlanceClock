package com.xson.glanceclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MainService extends Service {

@Override
public IBinder onBind(Intent intent) {
	return null;
}

@Override
public void onCreate() {
	super.onCreate();
	    Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
	Log.d("service", "create");
    // register receiver that handles screen on and screen off logic
	 
    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
    filter.addAction(Intent.ACTION_SCREEN_OFF);
    filter.addAction(Intent.ACTION_USER_PRESENT);
    BroadcastReceiver mReceiver = new ScreenReceiver();
    registerReceiver(mReceiver, filter);
}

public void onStart(Intent intent, int startId) {
	Log.d("service", "start");
    boolean screenOn = intent.getBooleanExtra("screen_state", true);
	Log.d("service", "screenOn=" + screenOn);
    if (!screenOn) {
    	startService(new Intent(getApplicationContext(), ONService.class));
    	stopService(new Intent(getApplicationContext(), ClockService.class));
    } else {
    	stopService(new Intent(getApplicationContext(), ONService.class));
    }
    
    boolean userState = intent.getBooleanExtra("user_state", true);
    if (!userState) {
    	//stopService(new Intent(getApplicationContext(), ClockService.class));
    } else {
    	stopService(new Intent(getApplicationContext(), ClockService.class));
    }
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    //here u should make your service foreground so it will keep working even if app closed

    NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    Intent bIntent = new Intent(MainService.this, MainActivity.class);       
    PendingIntent pbIntent = PendingIntent.getActivity(MainService.this, 0 , bIntent, 0);
    NotificationCompat.Builder bBuilder =
            new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Title")
                .setContentText("Subtitle")
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentIntent(pbIntent);
    Notification barNotif = bBuilder.build();
    this.startForeground(1, barNotif);
    
	return super.onStartCommand(intent, flags, startId);
}

public class LocalBinder extends Binder {
	MainService getService() {
		return MainService.this;
	}
}

public void onDestroy() {
	super.onDestroy();
    Toast.makeText(this, "Service Stopped!", Toast.LENGTH_LONG).show();
	Log.d("service", "destroy");
	}

}