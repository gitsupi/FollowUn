package com.example.amin.followfuck;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

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
//         startService(new Intent(this, HelloService.class));
        startService();
//
//        String posted_by = "111-333-222-4";
//
//
//
//        String uri = "tel:" + posted_by.trim() ;
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        intent.setData(Uri.parse(uri));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "dd")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//
//
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
//
//        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText("oooo");
//        bigText.setBigContentTitle("Today's Bible Verse");
//        bigText.setSummaryText("Text in detail");
//
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setContentTitle("Your Title");
//        mBuilder.setContentText("Your text");
//        mBuilder.setPriority(Notification.PRIORITY_MAX);
//        mBuilder.setStyle(bigText);
//
//        mNotificationManager =
//                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//// === Removed some obsoletes
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            String channelId = "Your_channel_id";
//            NotificationChannel channel = new NotificationChannel(
//                    channelId,
//                    "Channel title",
//                    NotificationManager.IMPORTANCE_HIGH);
//            mNotificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(channelId);
//        }
//
//        mNotificationManager.notify(0, mBuilder.build());
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
