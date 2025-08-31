package com.btracsolution.deliverysystem.Services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.btracsolution.deliverysystem.R;

import java.util.Objects;

public class HeadsUpNotificationService extends Service {
    private String CHANNEL_ID = "VoipChannel";
    private String CHANNEL_NAME = "Voip Channel";

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Bundle data = null;
        String data=null;
        String userType="";
        if (intent != null && intent.getExtras() != null) {
            data = intent.getStringExtra(ConstantApp.FCM_DATA_KEY);
            userType = intent.getStringExtra(ConstantApp.USERTYPE);

        }
        try {
            Intent receiveCallAction = new Intent(getApplicationContext(), HeadsUpNotificationActionReceiver.class);
            receiveCallAction.putExtra(ConstantApp.CALL_RESPONSE_ACTION_KEY, ConstantApp.CALL_RECEIVE_ACTION);
            receiveCallAction.putExtra(ConstantApp.FCM_DATA_KEY, data);
            receiveCallAction.putExtra(ConstantApp.USERTYPE, userType);
            receiveCallAction.setAction("RECEIVE_CALL");

            Intent cancelCallAction = new Intent(getApplicationContext(), HeadsUpNotificationActionReceiver.class);
            cancelCallAction.putExtra(ConstantApp.CALL_RESPONSE_ACTION_KEY, ConstantApp.CALL_CANCEL_ACTION);
            cancelCallAction.putExtra(ConstantApp.FCM_DATA_KEY, data);
            cancelCallAction.putExtra(ConstantApp.USERTYPE, userType);
            cancelCallAction.setAction("CANCEL_CALL");


            PendingIntent receiveCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1200, receiveCallAction, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            PendingIntent cancelCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1201, cancelCallAction,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            createChannel();
            NotificationCompat.Builder notificationBuilder = null;
            if (data != null) {
                notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentText("New order placed")
                        .setContentTitle("Incoming  Call")
                        .setSmallIcon(R.drawable.ic_confirm)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .addAction(R.drawable.ic_confirm, "Receive Call", receiveCallPendingIntent)
                        .addAction(R.drawable.ic_reject, "Cancel Call", cancelCallPendingIntent)
                        .setAutoCancel(true)
                        //.setOngoing(true)
                      //  .setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.loud_alarm))
                        .setFullScreenIntent(receiveCallPendingIntent, true);
            }

            Notification incomingCallNotification = null;
            if (notificationBuilder != null) {
                incomingCallNotification = notificationBuilder.build();
            }
            startForeground(120, incomingCallNotification);

            setSound();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    private void setSound() {
        player = MediaPlayer.create(this,
                R.raw.bc);
        //setting loop play to true
        //this will make the ringtone continuously playing
        player.setLooping(true);
        player.setVolume(1.0f,1.0f);

        //staring the player
        player.start();
    }

    /*
    Create noticiation channel if OS version is greater than or eqaul to Oreo
    */
    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Call Notifications");
//            channel.setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.loud_alarm),
//                    new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                            .setLegacyStreamType(AudioManager.STREAM_RING)
//                            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION).build());
            Objects.requireNonNull(getApplicationContext().getSystemService(NotificationManager.class)).createNotificationChannel(channel);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.stop();
    }
}