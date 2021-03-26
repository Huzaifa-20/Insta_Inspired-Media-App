package com.huzaifa.wallahabibi.NotifPack;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class OreoAndAboveNotifications extends ContextWrapper {

    private static final String ID="some_ID";
    private static final String NAME="FirebaseAPP";

    private NotificationManager notifMgr;

    public OreoAndAboveNotifications(Context base){
        super(base);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notichan=new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notichan.enableLights(true);
        notichan.enableVibration(true);
        notichan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(notichan);
    }

    public NotificationManager getManager(){
        if (notifMgr==null){
            notifMgr=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifMgr;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getONotification(String title,
                                                 String body,
                                                 PendingIntent pIntent,
                                                 Uri soundUri,
                                                 String icon){
        return new Notification.Builder(getApplicationContext(), ID)
                .setContentIntent(pIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setAutoCancel(true)
                .setSmallIcon(Integer.parseInt(icon));


    }


}
