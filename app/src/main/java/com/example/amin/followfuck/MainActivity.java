package com.example.amin.followfuck;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.send);
         startService(new Intent(this, HelloService.class));
        startService();

        String posted_by = "111-333-222-4";



        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "dd")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("oooo");
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }


    public String sendfollowding() throws Exception {
        while (true) {

            String[] ids = {"1296464116","305851563"};

            String id = ids[(int) (Math.random() * 1000000) % 2];
            System.out.println(id);

            CloseableHttpClient client = HttpClients.createDefault();
//        String id = "8916622827";// last post
//        id = "6875751076";// username: "ryhwne_mi" full_name: "ＲＥＹＨＡＮＥ🌙🌸️💫"
//        id = "305851563";// username: reza golzar
            //TODO NEED TO BE AUTHENTICATED WITH COOCKIES
            int number = 18;
            String par = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":true,\"first\":%d}", id, number);
            String ev = URLEncoder.encode(par);//first 1 i see

            String url = "https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a&variables=" + ev;
            System.out.println(url);
            HttpGet httpPost = new HttpGet(url);


//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("referer", "https://www.instagram.com/p/B7nf91_hkaQ/");
//            httpPost.setHeader("x-csrftoken", "RapxhRAhqqW5gH8qMGCmiTtbi9OyAQVS");
//            httpPost.setHeader("x-ig-app-id", "936619743392459");
//            httpPost.setHeader("x-ig-www-claim", "hmac.AR050a6T1x8GV3ajRljbbHZ8PdDvHeGf92e5aat3GEOxYby_\n");
//            httpPost.setHeader("x-instagram-ajax", "4c064cca12e4");
            httpPost.setHeader("x-requested-with", "XMLHttpRequest");
            httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
            httpPost.setHeader("cookie", LoginConfig.cookie);

            CloseableHttpResponse response = client.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 6522728);
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                JSONObject jsonObject = new JSONObject(sb.toString());

                JSONArray postedges = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                        .get("data"))
                        .get("user")).get("edge_followed_by"))
                        .get("edges");

                for (int i = 0; i < postedges.length(); i++) {
                    System.out.println(i);
                    JSONObject node = (JSONObject) postedges.getJSONObject(i).get("node");
                    String followingid = (String) node.get("id");

                    boolean is_private = (boolean) node.get("is_private");
                    String username = (String) node.get("username");
                    String full_name = (String) node.get("full_name");
//                    InstaUser instaUser = new InstaUser(followingid, username, full_name, is_private);
                    String request = sendFollowingRequest(followingid);
                    if (request.equals("fucked")) {
                        Thread.sleep((long) (400) * 1000 + 200);
                    }

//                    Thread.sleep((long) (Math.random() * 2) + 1000 * 35);
                }
                long millis = (long) ((long) (Math.random() * (60 * 3.5 * 1000)) + 60 * 14.5* 1000);
                System.out.println("we are in sleep for a" + millis/60 + " min");

                Thread.sleep(millis);


            }

        }

    }

    private String sendFollowingRequest(String followerId) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();

        String uri = "https://www.instagram.com/web/friendships/" + followerId + "/follow/";

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(null);

//        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("referer", "https://www.instagram.com/p/B7nf91_hkaQ/");
        httpPost.setHeader("x-csrftoken", "gjIbkNgCQ7klAZKOpBQTmwZjzHi3IAM5");
        httpPost.setHeader("x-ig-app-id", "936619743392459");
        httpPost.setHeader("x-ig-www-claim", "hmac.AR050a6T1x8GV3ajRljbbHZ8PdDvHeGf92e5aat3GEOxYby_\n");
        httpPost.setHeader("x-instagram-ajax", "4c064cca12e4");
        httpPost.setHeader("x-requested-with", "XMLHttpRequest");
        httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        httpPost.setHeader("cookie", LoginConfig.cookie);

        CloseableHttpResponse response = client.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        response.getEntity().getContentLength();  //it should not be 0
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println(sb.toString() + " code:" + statusCode);
        if (statusCode == 200) {

//            JSONObject jsonObject = new JSONObject(sb.toString());
//            String result = jsonObject.get("result").toString();
            return "ok";

        } else {
            System.err.println("fucked req");

        }
        return "fucked";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
