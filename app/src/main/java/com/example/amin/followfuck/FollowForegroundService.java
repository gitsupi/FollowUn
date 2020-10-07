package com.example.amin.followfuck;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.amin.followfuck.instgram.AddFollwerService;
import com.example.amin.followfuck.instgram.Reqs;
import com.example.amin.followfuck.instgram.ResponseAction;
import com.example.amin.followfuck.instgram.StatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FollowForegroundService extends Service {
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
                .setContentTitle("start following")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
                ;

        new Thread(() -> {
            for (int i = 0; true; i++) {


                AddFollwerService addFollwerService = new AddFollwerService();
                String celebrityId = addFollwerService.selectRandomCelebrityId(fullname -> {


                    builder.setContentTitle(fullname + " followers")
                            .setContentText(fullname + " selected");
                    startForeground(2, builder.build());
                });
                try {
                    JSONArray firstsfollowers = addFollwerService.findFirstsfollowers(celebrityId);
                    addFollwerService.startfollowFollowers(firstsfollowers, new ResponseAction() {
                        @Override
                        public void applyBeforeSendRequest(Object instaUsername) {
                            builder.setContentText(((String) instaUsername) + " is requesting...");
                            startForeground(2, builder.build());
                        }

                        @Override
                        public void applyAfterSucces(String instaUsername) {
                            builder.setContentText(instaUsername + " followed succfully 😍");
                            startForeground(2, builder.build());
                        }

                        @Override
                        public void applyAfterFollowError(String instaUsername, int errorcode) {
                            if (errorcode== StatusCodes.TOMONAYREQUEST)
                            builder.setContentTitle("too many requests error ..." )
                                    .setContentText(instaUsername + " failed 😢");
                            startForeground(2, builder.build());
                        }
                    });

                } catch (Exception e) {
                    builder.setContentText(e.toString());
                    startForeground(2, builder.build());

                }


                long millis = (long) ((long) (Math.random() * (60 * 1.5 * 1000)) + 60 * 15 * 1000);
                try {
                    String resp = Reqs.getReq("https://www.instagram.com/paksilen_market/?__a=1");
                    JSONObject jsonObject = new JSONObject(resp);
                    JSONObject user = (JSONObject) ((JSONObject) jsonObject.get("graphql")).get("user");
                    Integer countedge_follow = ((Integer) ((JSONObject) user.get("edge_follow")).get("count"));
                    Integer edge_followed_by = ((Integer) ((JSONObject) user.get("edge_followed_by")).get("count"));
                    builder.setContentTitle(edge_followed_by + "\\" + countedge_follow);


                } catch (IOException e) {

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int k=0;
                while (k<10) {
                    try {
                        builder.setContentText(millis *k/ 600000 + "m time of wait.passed..");
                        startForeground(2, builder.build());
                        Thread.sleep(millis/10);
                        k++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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