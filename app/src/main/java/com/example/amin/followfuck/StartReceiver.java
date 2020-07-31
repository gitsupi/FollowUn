package com.example.amin.followfuck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.telephony.ServiceState;

public class StartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        Intent serviceIntent = new Intent(context, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(context, serviceIntent);
    }
}
