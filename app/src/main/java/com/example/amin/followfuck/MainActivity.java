package com.example.amin.followfuck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public void startunfoloowService() {
        Intent serviceIntent = new Intent(this, UnFollowForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void startService() {

        Intent serviceIntent = new Intent(this, FollowForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(BusinessContext::initsetup).start();
        startService();
        startunfoloowService();

    }

}
