package com.huzaifa.wallahabibi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFBMsgSrvc extends FirebaseMessagingService {
    public static int NOTIFICATION_ID = 1;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        genNotific(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
    }

    private void genNotific(String body, String title) {
        Intent intt= new Intent(this, MainActivity.class);
        intt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendi=PendingIntent.getActivity(this, 0, intt, PendingIntent.FLAG_ONE_SHOT);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifBild= new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendi);

        NotificationManager notiMan=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (NOTIFICATION_ID>1073741824){
            NOTIFICATION_ID=0;
        }

        notiMan.notify(NOTIFICATION_ID++, notifBild.build());

    }
}