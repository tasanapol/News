package com.example.art_cs19.news;


import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Handler;


public class ChoosedActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = ChoosedActivity.class.getName();
    private final int REQUEST_SPEECH = 100;
    private ImageView news, music, utilities, radio, audioBook, call;
    private TextToSpeech tts;
    private Toolbar toolbar;
    private boolean textTospeech = false;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosed);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isStoragePermissionGranted();
        isContactPermission();
        isLocationPermission();
        findview();
        picassoEdit();

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(ChoosedActivity.this).load(R.drawable.newspressed).fit().centerCrop().noFade().into(news);
                isOnlineNews();
            }
        });

        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ChoosedActivity.this).load(R.drawable.radio).fit().centerCrop().noFade().into(radio);
                isOnlineRadio();

            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ChoosedActivity.this).load(R.drawable.musicpressed).fit().centerCrop().into(music);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        audioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ChoosedActivity.this).load(R.drawable.audiobookpressed).fit().centerCrop().into(audioBook);
                isOnlineAudioBook();
            }
        });
        utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ChoosedActivity.this).load(R.drawable.utilitiespressed).into(utilities);
                startActivity(new Intent(ChoosedActivity.this, UtilitiesActivity.class));

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ChoosedActivity.this).load(R.drawable.callpressed).fit().centerCrop().into(call);
                startActivity(new Intent(ChoosedActivity.this, Call.class));
            }
        });

    }


    private void picassoEdit() {
        Picasso.with(this).load(R.drawable.news).fit().centerCrop().into(news);
        Picasso.with(this).load(R.drawable.music).fit().centerCrop().into(music);
        Picasso.with(this).load(R.drawable.utilitites).fit().centerCrop().into(utilities);
        Picasso.with(this).load(R.drawable.radio2).fit().centerCrop().into(radio);
        Picasso.with(this).load(R.drawable.audiobook).fit().centerCrop().into(audioBook);
        Picasso.with(this).load(R.drawable.call).fit().centerCrop().into(call);
    }

    private void findview() {
        tts = new TextToSpeech(this, this, "com.google.android.tts");
        news = (ImageView) findViewById(R.id.news);
        music = (ImageView) findViewById(R.id.music);
        utilities = (ImageView) findViewById(R.id.utilities);
        radio = (ImageView) findViewById(R.id.radio);
        audioBook = (ImageView) findViewById(R.id.audioBook);
        call = (ImageView) findViewById(R.id.call);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                    speakWords("รายการหน้าเมนูมี" +
                            "1 ข่าว" +
                            "2 หนังสือเสียง" +
                            "3 วิทยุ" +
                            "4 เพลง" +
                            "5 โทร" +
                            "6 อรรถประโยชน์");
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "คำสั่งเสียง");
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
                        isOnlineRadio();


                    } else if (mostLikelyThingHeard.toUpperCase().equals("ข่าว")) {
                        isOnlineNews();

                    } else if (mostLikelyThingHeard.toUpperCase().equals("เพลง")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } else if (mostLikelyThingHeard.toUpperCase().equals("หนังสือเสียง")) {
                        isOnlineAudioBook();

                    } else if (mostLikelyThingHeard.toUpperCase().equals("โทร")) {
                        startActivity(new Intent(ChoosedActivity.this, Call.class));


                    } else if (mostLikelyThingHeard.toUpperCase().equals("อรรถประโยชน์")) {
                        startActivity(new Intent(ChoosedActivity.this, UtilitiesActivity.class));


                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                speakWords("ปิดระบบคำสั่งเสียงแล้ว");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            new AlertDialog.Builder(this).setTitle("ออกจากโปรแกรม")
                    .setMessage("คุณต้องการออกจากโปรแกรมใช่หรือไม่")
                    .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);/*/

    /***Change Here***
     startActivity(intent);
     Intent intent1 = new Intent(ChoosedActivity.this, RadioService.class);
     stopService(intent1);
     finish();
     System.exit(0);
     }
     }).setNegativeButton("ไม่", null).show();
     }
     return super.onKeyDown(keyCode, event);
     }*/

    public boolean isOnlineRadio() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            startActivity(new Intent(this, RadioActivity.class));
            tts.stop();
            finish();
            return connected = true;
        } else {
            speakWords("กรุณาเชื่อมต่ออินเตอร์เน็ต");
            Toast.makeText(getApplicationContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ตก่อนใช้งาน", Toast.LENGTH_LONG).show();
            return connected = false;
        }
    }

    public boolean isOnlineAudioBook() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            startActivity(new Intent(this, AudioBookMainActivity.class));
            tts.stop();
            finish();
            return connected = true;
        } else {
            speakWords("กรุณาเชื่อมต่ออินเตอร์เน็ต");
            Toast.makeText(getApplicationContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ตก่อนใช้งาน", Toast.LENGTH_LONG).show();
            return connected = false;
        }
    }

    public boolean isOnlineNews() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            startActivity(new Intent(this, NewsMainActivity.class));
            tts.stop();
            finish();
            return connected = true;
        } else {
            speakWords("กรุณาเชื่อมต่ออินเตอร์เน็ต");
            Toast.makeText(getApplicationContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ตก่อนใช้งาน", Toast.LENGTH_LONG).show();
            return connected = false;
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public void isContactPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 10);
        }

    }

    public void isLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 12345
            );
        }
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
            tts.setSpeechRate((float) 1);
            speakWords("ขณะนี้คุณกำลังอยู่ในหน้าเมนู กรุณาเลือกรายการ หรือกดปุ่มลดเสียงเพื่อฟังรายการเมนู");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        finish();
    }

    private static final int TIME_INTERVAL = 5000; //# milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        tts.stop();
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            Intent intent1 = new Intent(ChoosedActivity.this, RadioService.class);
            stopService(intent1);
            finish();
            System.exit(0);
            super.onBackPressed();

        } else {
            speakWords("หากต้องการออกจากโปรแกรมให้กดปุ่ม Back อีกครั้ง");
        }
        mBackPressed = System.currentTimeMillis();
    }
}

