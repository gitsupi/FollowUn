package com.example.amin.followfuck.instgramapi;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.amin.followfuck.BusinessContext;
import com.example.amin.followfuck.MainActivity;
import com.example.amin.followfuck.R;
import com.example.amin.followfuck.instgramapi.models.ContinuedEdges;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UnFollowForegroundService extends Service {
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
                .setContentTitle("start unfollowing")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
                ;
        //System.out.println("gooogle");
        startForeground(2, builder.build());


        new Thread(() -> {


            UnfollowMyFollowingsService unfollowMyFollowingsService = new UnfollowMyFollowingsService();
            ContinuedEdges firstsfollowings = null;
            try {

                firstsfollowings = unfollowMyFollowingsService.findFirstsfollowings();

                builder.setContentText("first followers ");
                startForeground(2, builder.build());
            } catch (Exception e) {
                builder.setContentText(e.toString());
                startForeground(2, builder.build());
                return;
            }


            String end_cursor = firstsfollowings.end_cursor;
            boolean has_next_page = firstsfollowings.has_next_page;
//            wait(builder);
            int delay = 0;
            while (has_next_page) {
                try {

                    ContinuedEdges aftersfollowings = unfollowMyFollowingsService.findAftersfollowings(end_cursor);
                    has_next_page = aftersfollowings.has_next_page;
                    end_cursor = aftersfollowings.end_cursor;
                    if (delay++ < 3) {
                        builder.setContentText("delaying "+delay);
                        startForeground(2, builder.build());
                        continue;
                    }

                    unfollowMyFollowingsService.startUnfollowMyfollowing(aftersfollowings.postedges, new ResponseAction() {
                        @Override
                        public void applyBeforeSendRequest(Object instaUsername) {
                            builder.setContentText(instaUsername + " is requesting to be unfollowed...");
                            startForeground(2, builder.build());
                        }

                        @Override
                        public void applyAfterSucces(String instaUsername) {
                            builder.setContentText(instaUsername + "unfollowed succfully 😍");
                            startForeground(2, builder.build());
                        }

                        @Override
                        public void applyAfterFollowError(String instaUsername, int errorcode) {
                            if (errorcode == StatusCodes.TOMONAYREQUEST)
                                builder.setContentTitle("too many requests error ...")
                                        .setContentText(instaUsername + " failed 😢 to unfollow");
                            else
                                builder.setContentTitle(errorcode + " recieved")
                                        .setContentText(instaUsername + " failed 😢 to unfollow");

                            startForeground(2, builder.build());
                        }
                    });

                } catch (Exception e) {
                    builder.setContentText(e.toString());
                    startForeground(2, builder.build());

                }


                wait(builder);

            }
        }).start();


        //do heavy work on a background thread
        //        //stopSelf();
        return START_NOT_STICKY;
    }


    private void wait(NotificationCompat.Builder builder) {
        long millis = (long) ((long) (Math.random() * (60 * 1.5 * 1000)) + 60 * 41 * 1000);
        try {
            String resp = Reqs.getReq("https://www.instagram.com/" + BusinessContext.Username + "/?__a=1");
            JSONObject jsonObject = new JSONObject(resp);
            JSONObject user = (JSONObject) ((JSONObject) jsonObject.get("graphql")).get("user");
            Integer countedge_follow = ((Integer) ((JSONObject) user.get("edge_follow")).get("count"));
            Integer edge_followed_by = ((Integer) ((JSONObject) user.get("edge_followed_by")).get("count"));
            builder.setContentTitle(BusinessContext.Username+" "+edge_followed_by + "/" + countedge_follow);
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        int k = 0;
        while (k < 10) {
            try {
                builder.setContentText(millis * k / 600000 + "m time of wait.passed..");
                startForeground(2, builder.build());
                Thread.sleep(millis / 10);
                k++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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