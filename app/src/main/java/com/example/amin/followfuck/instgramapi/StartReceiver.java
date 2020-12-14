package com.example.amin.followfuck.instgramapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

public class StartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        Intent serviceIntent = new Intent(context, FollowForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(context, serviceIntent);
    }
}
