package com.faaadev.postari.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.faaadev.postari.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        JSONObject data = new JSONObject(remoteMessage.getData());
        System.out.println("DATA == " + data.toString());
        try {
            createNotification(data.getString("title"), data.getString("body"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createNotification(String title, String body) {
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_round_mark_chat_unread_24)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Notifikasi",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());
    }
}
