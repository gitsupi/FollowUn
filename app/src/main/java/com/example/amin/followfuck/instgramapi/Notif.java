package com.example.amin.followfuck.instgramapi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.amin.followfuck.R;

public class Notif {


    public static void notify(Context context, String contentText) {
        String posted_by = "111-333-222-4";

        String uri = "tel:" + posted_by.trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "god")
                .setContentTitle("ik")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentIntent(contentIntent)

                .setOnlyAlertOnce(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(11, builder.build());
        }
    }
}
