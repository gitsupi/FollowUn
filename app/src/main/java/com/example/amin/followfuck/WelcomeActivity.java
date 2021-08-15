package com.example.amin.followfuck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.amin.followfuck.instgramapi.FollowForegroundService;
import com.example.amin.followfuck.instgramapi.UnFollowForegroundService;
import com.example.amin.followfuck.instgramapi.services.likerofpost.FollowLikersForegroundService;

public class WelcomeActivity extends AppCompatActivity {

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

    public void startLikersService() {

        Intent serviceIntent = new Intent(this, FollowLikersForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        final SharedPreferences sharedPref = this.getSharedPreferences("GOD", Context.MODE_PRIVATE);

        // Set up the login form.
        BusinessContext.initsetup(() -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.username), BusinessContext.Username);
            editor.commit();
            Button button = findViewById(R.id.startbtn);
            button.setText(sharedPref.getString(getString(R.string.username), "kos"));
            System.out.println("in start fore");
        });

        startLikersService();
        WelcomeActivity.this.startunfoloowService();
//        WelcomeActivity.this.startService();

    }
}
