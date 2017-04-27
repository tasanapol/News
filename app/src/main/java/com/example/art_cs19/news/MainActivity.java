package com.example.art_cs19.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    private String LOG_CLASS = "MainActivity";
    private CustomAdapter customAdapter = null;
    private static TextView playingSong;
    private Button btnPlayer;
    private static Button btnPause, btnPlay, btnNext, btnPrevious;
    private Button btnStop;
    private LinearLayout mediaLayout;
    private static LinearLayout linearLayoutPlayingSong;
    private ListView mediaListView;
    private ProgressBar progressBar;
    private TextView textBufferDuration, textDuration;
    private static ImageView imageViewAlbumArt;
    private static Context context;
    private final int REQUEST_SPEECH = 100;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        init();
    }

    private void init() {
        getViews();
        setListeners();
        playingSong.setSelected(true);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), Mode.SRC_IN);
        if (PlayerConstants.SONGS_LIST.size() <= 0) {
            PlayerConstants.SONGS_LIST = UtilFunctions.listOfSongs(getApplicationContext());
        }
        setListItems();
    }

    private void setListItems() {
        customAdapter = new CustomAdapter(this, R.layout.custom_list, PlayerConstants.SONGS_LIST);
        mediaListView.setAdapter(customAdapter);
        mediaListView.setFastScrollEnabled(true);
    }

    private void getViews() {
        tts = new TextToSpeech(this, this, "com.google.android.tts");
        playingSong = (TextView) findViewById(R.id.textNowPlaying);
        mediaListView = (ListView) findViewById(R.id.listViewMusic);
        mediaLayout = (LinearLayout) findViewById(R.id.linearLayoutMusicList);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        linearLayoutPlayingSong = (LinearLayout) findViewById(R.id.linearLayoutPlayingSong);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textBufferDuration = (TextView) findViewById(R.id.textBufferDuration);
        textDuration = (TextView) findViewById(R.id.textDuration);
        imageViewAlbumArt = (ImageView) findViewById(R.id.imageViewAlbumArt);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
    }

    private void setListeners() {
        mediaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Log.d("TAG", "TAG Tapped INOUT(IN)");
                PlayerConstants.SONG_PAUSED = false;
                PlayerConstants.SONG_NUMBER = position;

                boolean isServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
                if (!isServiceRunning) {
                    Intent i = new Intent(getApplicationContext(), SongService.class);
                    startService(i);
                } else {
                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                }
                updateUI();
                changeButton();
                Log.d("TAG", "TAG Tapped INOUT(OUT)");
            }
        });


        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.playControl(getApplicationContext());
            }
        });
        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.pauseControl(getApplicationContext());
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.nextControl(getApplicationContext());
            }
        });
        btnPrevious.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.previousControl(getApplicationContext());
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            boolean isServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
            if (isServiceRunning) {
                updateUI();
            } else {
                linearLayoutPlayingSong.setVisibility(View.GONE);
            }
            changeButton();
            PlayerConstants.PROGRESSBAR_HANDLER = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Integer i[] = (Integer[]) msg.obj;
                    textBufferDuration.setText(UtilFunctions.getDuration(i[0]));
                    textDuration.setText(UtilFunctions.getDuration(i[1]));
                    progressBar.setProgress(i[2]);
                }
            };
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("deprecation")
    public static void updateUI() {
        try {
            MediaItem data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
            playingSong.setText(data.getTitle() + " " + data.getArtist() + "-" + data.getAlbum());
            Bitmap albumArt = UtilFunctions.getAlbumart(context, data.getAlbumId());
            if (albumArt != null) {
                imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(albumArt));
            } else {
                imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(context)));
            }
            linearLayoutPlayingSong.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public static void changeButton() {
        if (PlayerConstants.SONG_PAUSED) {
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        } else {
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
        }
    }

    public static void changeUI() {
        updateUI();
        changeButton();
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
                    Controls.pauseControl(getApplicationContext());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0) {


                } else {
                    //คำสั่งเสียง
                    String mostLikelyThingHeard = matches.get(0);
                    if (mostLikelyThingHeard.toUpperCase().equals("เล่น")) {
                        Controls.playControl(getApplicationContext());
                        btnPause.setVisibility(View.VISIBLE);
                        btnPlay.setVisibility(View.GONE);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("หยุด")) {
                        Controls.pauseControl(getApplicationContext());
                        btnPause.setVisibility(View.GONE);
                        btnPlay.setVisibility(View.VISIBLE);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ถัดไป")) {
                        Controls.nextControl(getApplicationContext());

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ก่อนหน้า")) {
                        Controls.previousControl(getApplicationContext());

                    } else if (mostLikelyThingHeard.toUpperCase().equals("สลับเพลง")) {
                        Controls.shuffleControl(getApplicationContext());
                    }
                }


            } else if (resultCode == RESULT_CANCELED) {


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void speakWords(String speech) {
        if (tts != null) {
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            tts.setSpeechRate((float) 0.9);
            speakWords("ขณะนี้คุณกำลังอยู่ในหน้าเพลง กรุณาสัมผัสหน้าจอหนึ่งครั้ง แล้วใช้คำสั่ง เล่น หยุด ถัดไป ก่อนหน้า หรือ สลับเพลง");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        finish();
    }
}