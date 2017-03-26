package com.example.art_cs19.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ohoussein.playpause.PlayPauseDrawable;
import com.ohoussein.playpause.PlayPauseView;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class RadioSingleActivity extends AppCompatActivity {

    ImageView imgShowFm;
    MediaPlayer mediaPlayer;
    boolean prepared = false;
    boolean started = false;
    static ImageView play;
    static ImageView pause;

    String Fm;

    RippleBackground rippleBackground;

    private final int REQUEST_SPEECH = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_single);
        imgShowFm = (ImageView) findViewById(R.id.imgShowFm);
        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        play = (ImageView) findViewById(R.id.play);
        pause = (ImageView) findViewById(R.id.pause);
        int image1 = getIntent().getIntExtra("imageRadio", R.drawable.add_btn);
        imgShowFm.setImageResource(image1);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final String nameRadio1 = getIntent().getExtras().getString("nameRadio");
        Fm = getIntent().getExtras().getString("myFm");


        final Intent intent = new Intent(RadioSingleActivity.this, RadioService.class);
        intent.putExtra("image", image1);
        intent.putExtra("nameRadio", nameRadio1);
        intent.putExtra("Fm", Fm);


        if (RadioService.isServiceRunning == true) {
            rippleBackground.startRippleAnimation();
            pause.setVisibility(View.VISIBLE);
            play.setVisibility(View.GONE);
        } else if (RadioService.isServiceRunning == false) {
            rippleBackground.stopRippleAnimation();
            pause.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleBackground.startRippleAnimation();
                startService(intent);
                started = true;
                pause.setVisibility(View.VISIBLE);
                play.setVisibility(View.GONE);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleBackground.stopRippleAnimation();
                stopService(intent);
                started = false;
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);


            }
        });

    }

    /////////////////////////////SPEECH PROMPT ZONE///////////////////////////////////////////////
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        //กด
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    promtspeech();
                    final Intent intent = new Intent(RadioSingleActivity.this, RadioService.class);
                    stopService(intent);

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0) {


                } else {
                    //คำสั่งเสียง
                    String mostLikelyThingHeard = matches.get(0);


                    if (mostLikelyThingHeard.toUpperCase().equals("เล่น")) {
                        Intent intent = new Intent(RadioSingleActivity.this, RadioService.class);
                        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
                        Fm = getIntent().getExtras().getString("myFm");
                        intent.putExtra("Fm", Fm);
                        rippleBackground.startRippleAnimation();
                        startService(intent);
                        pause.setVisibility(View.VISIBLE);
                        play.setVisibility(View.GONE);


                    } else if (mostLikelyThingHeard.toUpperCase().equals("หยุด")) {
                        Intent intent = new Intent(RadioSingleActivity.this, RadioService.class);
                        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
                        Fm = getIntent().getExtras().getString("myFm");
                        rippleBackground.stopRippleAnimation();
                        stopService(intent);
                        pause.setVisibility(View.GONE);
                        play.setVisibility(View.VISIBLE);


                    } else if (mostLikelyThingHeard.toUpperCase().equals("เปลี่ยนคลื่น")) {
                        startActivity(new Intent(RadioSingleActivity.this, RadioActivity.class));

                    }
                }


            } else if (resultCode == RESULT_CANCELED) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}


