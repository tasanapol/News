package com.example.art_cs19.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;


public class SingleAudioBookActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private ImageButton imgShowPicBook;
    private TextView tvShowNameBook, tvShowNarrator, tvShowTypeBook, tvAudio, tvShowDate, tvShowTime;
    private Button btnStop;
    private String mPost_key;
    private DatabaseReference fDatabase;
    private Runnable runnable;
    private String post_namebook;
    private String post_narrator;
    private String post_id;
    private String post_img;
    private String post_audio;
    private String post_date;
    private String post_time;
    private String audio, audio2;
    private TextView tvBuffer, tvDuration;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler;
    private Button btnPlay, btnPause;
    private int mediaFileLengthInMilliseconds;
    private int playPositionInMillisecconds;
    private int length;
    private int newLength;
    private BackgroundSound mBackgroundSound;
    private int REQUEST_SPEECH = 1;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_audio_book);
        findView();
        setView();
        setClick();

       if(isPlaying == true){
           tvAudio.setText("not null");
       }else {
           tvAudio.setText("null");
       }
    }

    private void setClick() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(audio); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                mediaPlayer.start();
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                //seekBar.setProgress((mediaPlayer.getCurrentPosition()*100) / mediaPlayer.getDuration());
                primarySeekBarProgressUpdater();
                tvAudio.setText("KK");

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                btnPause.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                length = mediaPlayer.getCurrentPosition();

            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mediaPlayer.isPlaying()) {
                    SeekBar sb = (SeekBar) v;
                    playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                    mediaPlayer.seekTo(playPositionInMillisecconds);
                }
                return false;
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });
    }

    private void setView() {
        fDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_namebook = (String) dataSnapshot.child("title").getValue();
                post_audio = (String) dataSnapshot.child("audio").getValue();
                post_date = (String) dataSnapshot.child("date").getValue();
                post_time = (String) dataSnapshot.child("time").getValue();
                post_narrator = (String) dataSnapshot.child("narrator").getValue();
                post_id = (String) dataSnapshot.child("id").getValue();
                post_img = (String) dataSnapshot.child("image").getValue();

                tvShowNameBook.setText(post_namebook);
                tvShowNarrator.setText(post_narrator);
                tvShowTypeBook.setText(post_id);
                tvAudio.setText(post_audio);
                tvShowTime.setText(post_time);
                tvShowDate.setText(post_date);
                Picasso.with(SingleAudioBookActivity.this).load(post_img).into(imgShowPicBook);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    tvBuffer.setText(getDuration(mediaPlayer.getCurrentPosition()));
                    tvDuration.setText(getDuration(mediaPlayer.getDuration()));
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btnPlay.setVisibility(View.VISIBLE);
    }

    private void findView() {
        imgShowPicBook = (ImageButton) findViewById(R.id.imgShowPicBook);
        tvShowNameBook = (TextView) findViewById(R.id.tvShowNameBook);
        tvShowNarrator = (TextView) findViewById(R.id.tvShowNarrator);
        tvShowTypeBook = (TextView) findViewById(R.id.tvShowTypeBook);
        tvAudio = (TextView) findViewById(R.id.tvAudio);
        tvShowDate = (TextView) findViewById(R.id.tvShowDate);
        tvShowTime = (TextView) findViewById(R.id.tvShowTime);
        tvBuffer = (TextView) findViewById(R.id.tvBuffer);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        handler = new Handler();
        //เล่นพร้อม seekbar
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        fDatabase = FirebaseDatabase.getInstance().getReference("Audio");
        mPost_key = getIntent().getExtras().getString("post_key");
        audio = getIntent().getExtras().getString("audio");

    }

    public static String getDuration(long milliseconds) {
        long sec = (milliseconds / 1000) % 60;
        long min = (milliseconds / (60 * 1000)) % 60;
        long hour = milliseconds / (60 * 60 * 1000);

        String s = (sec < 10) ? "0" + sec : "" + sec;
        String m = (min < 10) ? "0" + min : "" + min;
        String h = "" + hour;

        String time = "";
        if (hour > 0) {
            time = h + ":" + m + ":" + s;
        } else {
            time = m + ":" + s;
        }
        return time;
    }


    public class BackgroundSound extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.start();
            mediaPlayer.isPlaying();
            isPlaying = true;
            return null;
        }

    }
/////////////speech prompt Zone /////////////////////////////////

    public void promtspeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "เลือกหัวข้อ");

        startActivityForResult(intent, REQUEST_SPEECH);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        //กด
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    mediaPlayer.pause();
                    promtspeech();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0) {
                    try {
                        promtspeech();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    //คำสั่งเสียง
                    final String mostLikelyThingHeard = matches.get(0);

                    if (mostLikelyThingHeard.toUpperCase().equals("เล่น")) {
                        try {
                            mediaPlayer.setDataSource(post_audio); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                            mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                        mediaPlayer.start();
                        btnPlay.setVisibility(View.GONE);
                        btnPause.setVisibility(View.VISIBLE);
                        //seekBar.setProgress((mediaPlayer.getCurrentPosition()*100) / mediaPlayer.getDuration());
                        primarySeekBarProgressUpdater();
                        tvAudio.setText("KK");

                    } else if (mostLikelyThingHeard.toUpperCase().equals("พัก")) {
                        mediaPlayer.pause();
                        btnPause.setVisibility(View.GONE);
                        btnPlay.setVisibility(View.VISIBLE);
                        mediaPlayer.pause();
                        length = mediaPlayer.getCurrentPosition();

                    } else if (mostLikelyThingHeard.toUpperCase().equals("หยุด")) {
                      mediaPlayer.stop();
                    } else if (mostLikelyThingHeard.toUpperCase().equals("ขยับ5นาที")) {
                        length = mediaPlayer.getCurrentPosition() + 5;
                        mediaPlayer.seekTo(length);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

/////////////speech prompt Zone /////////////////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.seekTo(length);
        mBackgroundSound = new BackgroundSound();
        mBackgroundSound.execute((Void[]) null);
        primarySeekBarProgressUpdater();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        length = mediaPlayer.getCurrentPosition();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        length = mediaPlayer.getCurrentPosition();
        mBackgroundSound.cancel(true);
    }
}

