package com.example.art_cs19.news;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class ChoosedActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private final int REQUEST_SPEECH = 100;
    private ImageView news,music, utilities , choosedRadio;
    private TextView text;
    private TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choosed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tts = new TextToSpeech(this, this, "com.google.android.tts");

        news = (ImageView) findViewById(R.id.news);
        music = (ImageView) findViewById(R.id.music);
        utilities =(ImageView) findViewById(R.id.utilities);
        news = (ImageView)findViewById(R.id.news);
        choosedRadio = (ImageView)findViewById(R.id.choosedRadio);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsMainActivity.class);
                startActivity(intent);
            }
        });

        choosedRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RadioActivity.class);
                startActivity(intent);
            }
        });

        /*music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MusicActivity.class);
                startActivity(intent);
            }
        });

        utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UtilitiesActivity.class);
                startActivity(intent);
                tts.speak("ยินดีต้องรับเข้าสู่หน้าอรรถประโยชน์", TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });*/





    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        //กด
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    promtspeech();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    promtspeech();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    public void promtspeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "เทสๆ");
        startActivityForResult(intent, REQUEST_SPEECH);

    }


    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_SPEECH){
            if(resultCode == RESULT_OK){
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                if(matches.size()== 0){


                }else {
                    //คำสั่งเสียง
                    String mostLikelyThingHeard = matches.get(0);
                    if(mostLikelyThingHeard.toUpperCase().equals("วิทยุ")){
                        startActivity(new Intent(this, RadioActivity.class));

                    }else if(mostLikelyThingHeard.toUpperCase().equals("ข่าว")){
                        startActivity(new Intent(this, NewsMainActivity.class));

                    }else if(mostLikelyThingHeard.toUpperCase().equals("CHANGE")){
                        text = (TextView)findViewById(R.id.text);
                        text.setText("YYYY");

                    }else if(mostLikelyThingHeard.toUpperCase().equals("UTILITY")){

                        tts.speak("ยินดีต้องรับเข้าสู่หน้าอรรถประโยชน์", TextToSpeech.QUEUE_FLUSH, null, "");
                    }


                }
            }else if(resultCode == RESULT_CANCELED){


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
        }

    }
}
