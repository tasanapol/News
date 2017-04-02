package com.example.art_cs19.news;


import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class ChoosedActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = ChoosedActivity.class.getName();
    private final int REQUEST_SPEECH = 100;
    private ImageView news, music, utilities, choosedRadio, audioBook;
    private TextView text;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choosed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isStoragePermissionGranted();

        tts = new TextToSpeech(this, this, "com.google.android.tts");

        news = (ImageView) findViewById(R.id.news);
        music = (ImageView) findViewById(R.id.music);
        utilities = (ImageView) findViewById(R.id.utilities);
        news = (ImageView) findViewById(R.id.news);
        choosedRadio = (ImageView) findViewById(R.id.choosedRadio);
        audioBook =(ImageView)findViewById(R.id.audioBook);

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

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        audioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AudioBookMainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                if (matches.size() == 0) {


                } else {
                    //คำสั่งเสียง
                    String mostLikelyThingHeard = matches.get(0);
                    if (mostLikelyThingHeard.toUpperCase().equals("วิทยุ")) {
                        startActivity(new Intent(this, RadioActivity.class));

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ข่าว")) {
                        startActivity(new Intent(this, NewsMainActivity.class));

                    } else if (mostLikelyThingHeard.toUpperCase().equals("เพลง")) {
                        startActivity(new Intent(this, MainActivity.class));

                    } else if (mostLikelyThingHeard.toUpperCase().equals("UTILITY")) {

                        tts.speak("ยินดีต้องรับเข้าสู่หน้าอรรถประโยชน์", TextToSpeech.QUEUE_FLUSH, null, "");
                    }


                }
            } else if (resultCode == RESULT_CANCELED) {


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
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            new AlertDialog.Builder(this).setTitle("ออกจากโปรแกรม")
                    .setMessage("คุณต้องการออกจากโปรแกรมใช่หรือไม่")
                    .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            Intent intent1 = new Intent(ChoosedActivity.this,RadioService.class);
                            stopService(intent1);
                            finish();
                            System.exit(0);
                        }
                    }).setNegativeButton("ไม่", null).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
