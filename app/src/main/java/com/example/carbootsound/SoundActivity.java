package com.example.carbootsound;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SoundActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = MediaPlayer.create(this, R.raw.boot_sound);

        player.setOnCompletionListener(mp -> {

            mp.release();
            finish();

        });

        player.start();

    }

}