package com.example.art_cs19.news;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity implements TextToSpeech.OnInitListener{

    //ประกาศชื่อออปเจ็กต์ Handler และ Runnable
    private Handler handler;
    private Runnable runnable;
    //ประกาศตัวแปรเพื่อกำหนดเวลา และตัวแปรเพื่อใช้ในการอ้างถึงเวลาที่กำหนดไว้
    private long time = 3000L;
    private long delay_time;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tts = new TextToSpeech(this, this, "com.google.android.tts");
        //ไม่แสดง action Bar
        setContentView(R.layout.activity_splash_screen);

        //ไม่แสดง status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //สร้าง Handler ออบเจ็กต์ เพื่อใช้ควบคุมการทำงาน
        handler = new Handler();
        //สร้าง Thread เพื่อกำหนดการเปิดหน้าจอแบบ Splashscreen
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, ChoosedActivity.class);
                startActivity(intent);
                tts.speak("คุณกำลังอยู่ในหน้าเมนู กรุณาเลือกรายการ", TextToSpeech.QUEUE_FLUSH, null, "");

            }
        };
        //สร้างเสียงใน splashscreen
        //MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.splashsound);
        //mp.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        //code splashscreen
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        //code splashscreen
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
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
}

