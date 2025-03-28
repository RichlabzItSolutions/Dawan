package com.dawan.services;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dawan.R;
import com.dawan.SplashScreenActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID_KEY = "channel_id";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage != null && remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String imageUrl = String.valueOf(remoteMessage.getNotification().getImageUrl());
            Map<String, String> data = remoteMessage.getData();
            String type = data.get("type");
            String postId = data.get("postId");
            String soundUrl = data.get("soundUrl");

            // Ensure soundUrl is not null
            if (soundUrl == null) {
                 soundUrl=ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.noti_sound;
//                soundUrl = "https://firebasestorage.googleapis.com/v0/b/dawan-19a87.appspot.com/o/success-fanfare-trumpets-6185.mp3?alt=media&token=2dab6e75-794f-46aa-9332-735414324855"; // Default sound URL if not provided
            }

            // Generate unique channel ID
            String channelId = "Dawan_Richlabz";
//            + Calendar.getInstance().getTimeInMillis();

            // Send notification with the generated channel ID
            sendNotification(title, body, imageUrl, type, postId, soundUrl, channelId);
        }
    }

    private void sendNotification(String title, String body, String imageUrl, String type, String postId, String soundUrl, String channelId) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Create notification channel if Android version is Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, channelId, "Dawan", soundUrl);

        }

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(getBitmapFromUrl(imageUrl));
        bigPictureStyle.setSummaryText(body);

        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (postId != null && !postId.isEmpty()) {
            intent.putExtra("postdetail", true);
            intent.putExtra("postId", postId);
        } else {
            intent.putExtra("postdetail", false);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setStyle(bigPictureStyle)
                .setSound(Uri.parse(soundUrl))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show notification
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat.from(this).notify((int) Calendar.getInstance().getTimeInMillis(), notificationBuilder.build());

        // Store channel ID in SharedPreferences after showing the notification
//        storeChannelId(channelId);
    }

    private void storeChannelId(String channelId) {
        SharedPreferences.Editor editor = getSharedPreferences("notification_prefs", MODE_PRIVATE).edit();
        editor.putString(CHANNEL_ID_KEY, channelId);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager, String channelId, String channelName, String soundUrl) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        channel.setSound(Uri.parse(soundUrl), attributes);
        notificationManager.createNotificationChannel(channel);
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
