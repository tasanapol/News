package com.example.art_cs19.news;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ohoussein.playpause.PlayPauseDrawable;
import com.ohoussein.playpause.PlayPauseView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class RadioSingleActivity extends AppCompatActivity {

    ImageView imgShowFm;
    MediaPlayer mediaPlayer;
    boolean prepared = false;
    boolean started = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_single);
        imgShowFm = (ImageView) findViewById(R.id.imgShowFm);

        final PlayPauseView playpause = (PlayPauseView) findViewById(R.id.playpause);


        //Picasso.with(RadioSingleActivity.this).load("imageRadio").into(imgShowFm);
        int image = getIntent().getIntExtra("imageRadio", R.drawable.add_btn);
        imgShowFm.setImageResource(image);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        final String Fm = getIntent().getExtras().getString("myFm");



        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    started = false;
                    mediaPlayer.pause();
                    playpause.change(true);
                }else {
                    started = false;
                    mediaPlayer.start();
                    playpause.change(false);
                    new PlayerTask().execute(Fm);
                }
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
