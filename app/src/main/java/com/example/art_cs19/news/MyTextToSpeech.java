package com.example.art_cs19.news;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;


/**
 * Created by Art_cs19 on 11/28/2016 AD.
 */

public class MyTextToSpeech extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(this, this, "com.google.android.tts");
    }

    public MyTextToSpeech(String speech) {
        // speak straight away
        if (tts != null) {
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
}





