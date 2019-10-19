package com.example.afe.task.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiverRappel extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel2Notification();
        notificationHelper.getManager().notify(2,nb.build());
    }
}