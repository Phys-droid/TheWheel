package com.example.thewheel;

import android.content.Context;
import android.media.MediaPlayer;

public  class SoundManager {
    public static MediaPlayer togg;
    public static MediaPlayer michael;

    public static void initialize(Context context) {
        togg = MediaPlayer.create(context, R.raw.togg);
        michael = MediaPlayer.create(context, R.raw.michael);
    }
}


