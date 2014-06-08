package com.xson.glanceclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    public static boolean userState = true;
    
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            wasScreenOn = false;
        	userState = false;
            Log.d("test","Screen OFF");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            wasScreenOn = true;
            Log.d("test","Screen ON");
        }else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
        	userState = true;
            Log.d("test","userpresent");
        }
        Intent i = new Intent(context, MainService.class);
        i.putExtra("screen_state", wasScreenOn);
        i.putExtra("user_state", userState);
        Log.d("test","putExtra");
        context.startService(i);
    }
 
}