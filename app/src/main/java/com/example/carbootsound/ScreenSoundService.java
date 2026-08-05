package com.example.carbootsound;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

public class ScreenSoundService extends Service {

    private static final String CHANNEL = "bootsound";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL,
                            "Boot Sound",
                            NotificationManager.IMPORTANCE_LOW);

            NotificationManager manager =
                    getSystemService(NotificationManager.class);

            if (manager != null)
                manager.createNotificationChannel(channel);

            Notification notification =
                    new Notification.Builder(this, CHANNEL)
                            .setSmallIcon(android.R.drawable.ic_media_play)
                            .setContentTitle("Boot Sound")
                            .build();

            startForeground(1, notification);
        }

        MediaPlayer player =
                MediaPlayer.create(this, R.raw.boot_sound);

        if (player != null) {

            player.setOnCompletionListener(mp -> {

                mp.release();

                stopForeground(true);

                stopSelf();

            });

            player.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {

        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}