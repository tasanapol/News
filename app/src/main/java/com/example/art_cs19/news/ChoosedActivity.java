package com.example.art_cs19.news;


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

import java.util.ArrayList;
import java.util.Locale;


public class ChoosedActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = ChoosedActivity.class.getName();
    private final int REQUEST_SPEECH = 100;
    private ImageView news, music, utilities, radio, audioBook, call;
    private TextView text;
    private TextToSpeech tts;
    private Toolbar toolbar;
    private boolean connected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosed);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isStoragePermissionGranted();
        findview();

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                news.setImageResource(R.drawable.newspressed);
                isOnlineNews();
                tts.speak("คุณกำลังอยู่ในหน้าข่าว กรุณาเลือกรายการ หรือกดปุ่มลดเสียงเพื่อฟังรายการ", TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });

        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio.setImageResource(R.drawable.radio);
                isOnlineRadio();
                tts.speak("คุณกำลังอยู่ในหน้าวิทยุ กรุณาเลือกรายการ หรือกดปุ่มลดเสียงเพื่อฟังรายการ", TextToSpeech.QUEUE_FLUSH, null, "");

            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.setImageResource(R.drawable.musicpressed);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                tts.speak("คุณกำลังอยู่ในหน้าเพลง กรุณาเลือกรายการ", TextToSpeech.QUEUE_FLUSH, null, "");
                finish();
            }
        });

        audioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioBook.setImageResource(R.drawable.audiobookpressed);
                isOnlineAudioBook();
                tts.speak("คุณกำลังอยู่ในหน้าหนังสือเสียง กรุณาเลือกรายการหนังสือ หรือกดปุ่มลดเสียงเพื่อฟังรายการ", TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });
        utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilities.setImageResource(R.drawable.utilitiespressed);
                startActivity(new Intent(ChoosedActivity.this,UtilitiesActivity.class));

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.setImageResource(R.drawable.callpressed);
                tts.speak("คุณกำลังอยู่ในหน้าโทรฉุกเฉิน กรุณาเลือกรายการเพื่อโทร", TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });

    }

    private void findview() {
        tts = new TextToSpeech(this, this, "com.google.android.tts");
        news = (ImageView) findViewById(R.id.news);
        music = (ImageView) findViewById(R.id.music);
        utilities = (ImageView) findViewById(R.id.utilities);
        news = (ImageView) findViewById(R.id.news);
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
                        tts.speak("คุณกำลังอยู่ในหน้าวิทยุ กรุณาเลือกรายการ หรือกดปุ่มลดเสียงเพื่อฟังรายการ", TextToSpeech.QUEUE_FLUSH, null, "");

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ข่าว")) {
                        isOnlineNews();
                        tts.speak("คุณกำลังอยู่ในหน้าข่าว กรุณาเลือกรายการ หรือกดปุ่มลดเสียงเพื่อฟังรายการ", TextToSpeech.QUEUE_FLUSH, null, "");

                    } else if (mostLikelyThingHeard.toUpperCase().equals("เพลง")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } else if (mostLikelyThingHeard.toUpperCase().equals("หนังสือเสียง")) {
                        isOnlineAudioBook();
                        tts.speak("คุณกำลังอยู่ในหน้าหนังสือเสียง กรุณาเลือกรายการหนังสือ หรือกดปุ่มลดเสียงเพื่อฟังรายการ", TextToSpeech.QUEUE_FLUSH, null, "");
                    } else if (mostLikelyThingHeard.toUpperCase().equals("โทร")) {
                        tts.speak("คุณกำลังอยู่ในหน้าโทรฉุกเฉิน กรุณาเลือกรายการเพื่อโทร", TextToSpeech.QUEUE_FLUSH, null, "");

                    } else if (mostLikelyThingHeard.toUpperCase().equals("อรรถประโยชน์")) {

                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                tts.speak("ปิดระบบคำสั่งเสียงแล้ว", TextToSpeech.QUEUE_FLUSH, null, "");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                            Intent intent1 = new Intent(ChoosedActivity.this, RadioService.class);
                            stopService(intent1);
                            finish();
                            System.exit(0);
                        }
                    }).setNegativeButton("ไม่", null).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isOnlineRadio() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            startActivity(new Intent(this, RadioActivity.class));
            finish();
            return connected = true;
        } else {
            tts.speak("กรุณาเชื่อมต่ออินเตอร์เน็ต", TextToSpeech.QUEUE_FLUSH, null, "");
            Toast.makeText(getApplicationContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ตก่อนใช้งาน", Toast.LENGTH_LONG).show();
            return connected = false;
        }
    }

    public boolean isOnlineAudioBook() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            startActivity(new Intent(this, AudioBookMainActivity.class));
            finish();
            return connected = true;
        } else {
            tts.speak("กรุณาเชื่อมต่ออินเตอร์เน็ต", TextToSpeech.QUEUE_FLUSH, null, "");
            Toast.makeText(getApplicationContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ตก่อนใช้งาน", Toast.LENGTH_LONG).show();
            return connected = false;
        }
    }

    public boolean isOnlineNews() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            startActivity(new Intent(this, NewsMainActivity.class));
            finish();
            return connected = true;
        } else {
            tts.speak("กรุณาเชื่อมต่ออินเตอร์เน็ต", TextToSpeech.QUEUE_FLUSH, null, "");
            Toast.makeText(getApplicationContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ตก่อนใช้งาน", Toast.LENGTH_LONG).show();
            return connected = false;
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            tts.setSpeechRate((float) 0.8);
        }

    }

}
