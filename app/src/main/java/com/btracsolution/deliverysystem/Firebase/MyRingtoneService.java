package com.btracsolution.deliverysystem.Firebase;

/**
 * Created by mahmudul.hasan on 2/20/2018.
 */


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.btracsolution.deliverysystem.R;

public class MyRingtoneService extends Service {

    //creating a media player object
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone

        player = MediaPlayer.create(this,
                R.raw.bc);
        //setting loop play to true
        //this will make the ringtone continuously playing
        player.setLooping(true);
        player.setVolume(1.0f,1.0f);

        //staring the player
        player.start();

        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.stop();
    }
}
