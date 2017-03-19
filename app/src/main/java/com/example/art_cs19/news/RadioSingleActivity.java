package com.example.art_cs19.news;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ohoussein.playpause.PlayPauseDrawable;
import com.ohoussein.playpause.PlayPauseView;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class RadioSingleActivity extends AppCompatActivity {

    ImageView imgShowFm, pause;
    MediaPlayer mediaPlayer;
    boolean prepared = false;
    boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_single);
        imgShowFm = (ImageView) findViewById(R.id.imgShowFm);

        pause = (ImageView) findViewById(R.id.pause);


        //Picasso.with(RadioSingleActivity.this).load("imageRadio").into(imgShowFm);
        int image1 = getIntent().getIntExtra("imageRadio", R.drawable.add_btn);
        imgShowFm.setImageResource(image1);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);



        final String nameRadio1 = getIntent().getExtras().getString("nameRadio");


        final Intent intent = new Intent(RadioSingleActivity.this, RadioService.class);
        intent.putExtra("image", image1);
        intent.putExtra("nameRadio", nameRadio1);

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        final ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rippleBackground.startRippleAnimation();
                startService(intent);
                started = false;
                play.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleBackground.stopRippleAnimation();

                stopService(intent);
                started = false;
                play.setEnabled(true);



            }
        });


    }


    class PlayerTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mediaPlayer.start();
            started = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started) {
            mediaPlayer.pause();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prepared) {
            mediaPlayer.release();
        }
    }

}
