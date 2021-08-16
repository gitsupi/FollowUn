package com.example.amin.followfuck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.amin.followfuck.logic.FutureSymbolsCheckService;

public class MainActivity extends AppCompatActivity {


    public void startService() {

        Intent serviceIntent = new Intent(this, FutureSymbolsCheckService.class);
        serviceIntent.putExtra("inputExtra", "FutureSymbolsCheckService");
        ContextCompat.startForegroundService(this, serviceIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(BusinessContext::initsetup).start();
        startService();


    }

}
