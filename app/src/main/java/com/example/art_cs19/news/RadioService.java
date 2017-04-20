package com.example.art_cs19.news;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Art_cs19 on 3/10/2017 AD.
 */

public class RadioService extends Service {

    private MediaPlayer mediaPlayer;
    private static String Fm;
    public static boolean isServiceRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Fm = intent.getExtras().getString("Fm");

        mediaPlayer = MediaPlayer.create(RadioService.this, Uri.parse(Fm));

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.start();

        isServiceRunning = true;

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }else{
            mediaPlayer.stop();
        }
        isServiceRunning = false;
        super.onDestroy();
    }

}
