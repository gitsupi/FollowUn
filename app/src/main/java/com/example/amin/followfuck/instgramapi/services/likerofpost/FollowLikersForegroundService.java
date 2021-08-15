package com.example.amin.followfuck.instgramapi.services.likerofpost;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.amin.followfuck.BusinessContext;
import com.example.amin.followfuck.MainActivity;
import com.example.amin.followfuck.R;
import com.example.amin.followfuck.instgramapi.Reqs;
import com.example.amin.followfuck.instgramapi.ResponseAction;
import com.example.amin.followfuck.instgramapi.StatusCodes;
import com.example.amin.followfuck.instgramapi.models.ContinuedEdges;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FollowLikersForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("start following")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
                ;

        new Thread(() -> {
            for (int i = 0; true; i++) {

                String shortcode = "CM2U-PvrTeY";
                UsersofLikedService usersofLikedService = new UsersofLikedService();
//                try {
//                    shortcode = usersofLikedService.lastpostShortcode(fullname -> {
//
//                        builder.setContentTitle(fullname + " followers")
//                                .setContentText(fullname + " selected");
//                        startForeground(3, builder.build());
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                if (shortcode.equals(""))
                    continue;

                try {
                    ContinuedEdges firstsfollowers = usersofLikedService.findfirstlikmers(shortcode);

                    System.out.println(new Gson().toJson(firstsfollowers));

                    String finalShortcode = shortcode;
                    usersofLikedService.startfollowFollowers(firstsfollowers.postedges, new ResponseAction() {
                        @Override
                        public void applyBeforeSendRequest(Object instaUsername) {
                            builder.setContentText(instaUsername + " in "+ finalShortcode);
                            startForeground(3, builder.build());
                        }

                        @Override
                        public void applyAfterSucces(String instaUsername) {
                            builder.setContentText(instaUsername + " followed succfully 😍");
                            startForeground(3, builder.build());
                        }

                        @Override
                        public void applyAfterFollowError(String instaUsername, int errorcode) {
                            if (errorcode == StatusCodes.TOMONAYREQUEST) {
                                builder.setContentTitle("too many requests error ...")
                                        .setContentText(instaUsername + " failed 😢");
                            }


                            startForeground(3, builder.build());
                        }
                    });

                } catch (Exception e) {
                    builder.setContentText(e.toString());
                    startForeground(3, builder.build());

                }


                long millis = (long) ((long) (Math.random() * (60 * 1.5 * 1000)) + 60 * 41 * 1000);
                try {
                    String resp = Reqs.getReq("https://www.instagram.com/" + BusinessContext.Username + "/?__a=1");
                    JSONObject jsonObject = new JSONObject(resp);
                    JSONObject user = (JSONObject) ((JSONObject) jsonObject.get("graphql")).get("user");
                    Integer countedge_follow = ((Integer) ((JSONObject) user.get("edge_follow")).get("count"));
                    Integer edge_followed_by = ((Integer) ((JSONObject) user.get("edge_followed_by")).get("count"));
                    String title = BusinessContext.Username + " " + edge_followed_by + "/" + countedge_follow;
                    builder.setContentTitle(title);
                    //System.out.println(title);


                } catch (IOException e) {

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int k = 0;
                while (k < 10) {
                    try {
                        builder.setContentText(millis * k / 600000 + "m time of wait.passed..");
                        startForeground(3, builder.build());
                        Thread.sleep(millis / 10);
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