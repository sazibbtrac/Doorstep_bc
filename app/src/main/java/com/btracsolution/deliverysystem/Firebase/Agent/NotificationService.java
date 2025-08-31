package com.btracsolution.deliverysystem.Firebase.Agent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Features.Rider.HomeActivity;
import com.btracsolution.deliverysystem.Features.Waiter.WaiterActivity;
import com.btracsolution.deliverysystem.R;

import java.io.IOException;

public class NotificationService extends Service {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, createNotification());  // Keeps service alive
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playNotificationSound();
        return START_NOT_STICKY;
    }

    private void playNotificationSound() {
        acquireWakeLock();  // Prevent device from sleeping

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.requestAudioFocus(focusChange -> {},
                    AudioManager.STREAM_NOTIFICATION,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }

        new Thread(() -> {  // Run sound playback on a separate thread
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.bc);  // Replace 'bc' with your actual sound file
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                mediaPlayer.setLooping(false);
            }

            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> stopSelf()); // Stop service after playback
        }).start();
    }

    private void acquireWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "NotificationSound::WakeLock");
            wakeLock.acquire(5000); // Hold for 5 seconds
        }
    }

    private Notification createNotification() {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("foreground_service",
                    "Foreground Service", NotificationManager.IMPORTANCE_LOW);
        }
        NotificationManager manager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = getSystemService(NotificationManager.class);
        }
        if (manager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(channel);
            }
        }

        return new NotificationCompat.Builder(this, "foreground_service")
                .setContentTitle("Playing Notification Sound")
                .setSmallIcon(R.drawable.icon_door)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
