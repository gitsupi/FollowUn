package com.example.amin.followfuck;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.amin.followfuck.instgram.AddFollwerService;
import com.example.amin.followfuck.instgram.ResponseAction;

import org.json.JSONArray;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        new Thread(() -> {
            for (int i = 0; true; i++) {


                AddFollwerService addFollwerService = new AddFollwerService();
                String celebrityId = addFollwerService.selectRandomCelebrityId(fullname -> {
                    builder.setContentText(fullname + " selected");
                    startForeground(2, builder.build());
                });
                try {
                    JSONArray firstsfollowers = addFollwerService.findFirstsfollowers(celebrityId);
                    addFollwerService.startfollowFollowers(firstsfollowers, new ResponseAction() {
                        @Override
                        public void applyBeforeSendFollowRequest(String instaUsername) {
                            builder.setContentText(instaUsername + " is requesting...");
                            startForeground(2, builder.build());
                        }

                        @Override
                        public void applyAfterFollowSucces(String instaUsername) {
                            builder.setContentText(instaUsername + " followed succfully 😍");
                            startForeground(2, builder.build());
                        }

                        @Override
                        public void applyAfterFollowError(String instaUsername, int errorcode) {
                            builder.setContentText(instaUsername + " failed 😢");
                            startForeground(2, builder.build());
                        }
                    });

                } catch (Exception e) {
                    builder.setContentText(e.toString());
                    startForeground(2, builder.build());

                }


                try {
                    long millis = (long) ((long) (Math.random() * (60 * 1.5 * 1000)) + 60 * 15 * 1000);
                    builder.setContentText(millis/60000+"m time of wait...");
                    startForeground(2, builder.build());
                    Thread.sleep(millis);
                    System.out.println("in wait ");
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }
}