package com.example.amin.followfuck.logic;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.amin.followfuck.MainActivity;
import com.example.amin.followfuck.R;
import com.example.amin.followfuck.logic.model.market.ExchangeInfoEntry;
import com.example.amin.followfuck.logic.model.market.ExchangeInformation;

import java.util.List;

public class FutureSymbolsCheckService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("start sending")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
                ;

        new Thread(() -> {
            for (; ; ) {
                builder.setContentText(" is requesting...");
                startForeground(3, builder.build());


                SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

                try {

                    ExchangeInformation exchangeInformation = syncRequestClient.getExchangeInformation();
                    List<ExchangeInfoEntry> symbols = exchangeInformation.getSymbols();

                    builder.setContentText(symbols.get(symbols.size() - 1).getSymbol());
                    startForeground(3, builder.build());

                }
                catch (Exception e){

                    builder.setContentText(e.getMessage());

                    startForeground(3, builder.build());

                }

                int k = 0;
                int millis = 10000;

                try {
                    Thread.sleep(millis);
                    builder.setContentText(millis * k / 600000 + "m time of wait.passed..");
                    startForeground(3, builder.build());

                    k++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}