package com.huzaifa.wallahabibi.NotifPack;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.huzaifa.wallahabibi.ChatScreen;

public class MyFBMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sented=remoteMessage.getData().get("sented");

        FirebaseUser fbu= FirebaseAuth.getInstance().getCurrentUser();

        if (fbu!=null && sented.equals(fbu.getUid())){
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String usr=remoteMessage.getData().get("user");
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String bod=remoteMessage.getData().get("body");

        RemoteMessage.Notification notif=remoteMessage.getNotification();

        int j=Integer.parseInt(usr.replaceAll("[\\D]", ""));
        Intent intent=new Intent(this, ChatScreen.class);
        Bundle bundle=new Bundle();

        bundle.putString("userid", usr);
        intent.putExtras(bundle);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound= RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder bilder=new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(bod)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);

        NotificationManager notifi=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i=0;

        if (j>0){
            i=j;
        }

        notifi.notify(i, bilder.build());

    }
}