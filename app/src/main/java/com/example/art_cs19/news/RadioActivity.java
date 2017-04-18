package com.example.art_cs19.news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;

public class RadioActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    String cool = "http://111.223.51.7:8000/;stream/";//93.0
    String get = "http://radio11.plathong.net:7004/;stream.nsv";//102.5 Get Radio
    String looktung = "http://radio2.spicymkt.com:8814/;stream.nsv";//ลูกทุ่งเน็ตเวิร์ค 94.5
    String efm = "http://real3.atimemedia.com:1935/live/efm.sdp/playlist.m3u8";// efm 94.0
    String buddha = "http://61.19.252.133:8000/korat"; // พุทธเรดิโอ 99.0
    String theshcok = "http://radiom.spicymkt.com:1870/;stream.nsv";// theshock
    String korea = "https://radio.ikorea.in.th/CH01"; // iKorea
    String eazy = "http://wzedge2.becteroradio.com/Eazy/Eazy-Listen-Med/playlist.m3u8"; //105.5
    String virgin = "http://wzedge2.becteroradio.com/Star/Star-Listen-Med/playlist.m3u8"; //virgin star Fm
    String surf = "http://112.121.150.133:9626/;stream.nsv"; //surf 93.5
    String chill = "http://real3.atimemedia.com:1935/live/chill.sdp/playlist.m3u8";//chill
    String rad = "http://wzedge2.becteroradio.com/Rad/Rad-Listen-Med/playlist.m3u8";//RadRadio ตื้ดๆ

    private ImageView radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10;

    private final int REQUEST_SPEECH = 100;

    private TextToSpeech tts;

    private boolean connected = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);


        tts = new TextToSpeech(this, this, "com.google.android.tts");
        radio1 = (ImageView) findViewById(R.id.cool);
        radio2 = (ImageView) findViewById(R.id.get);
        radio3 = (ImageView) findViewById(R.id.looktung);
        radio4 = (ImageView) findViewById(R.id.efm);
        radio5 = (ImageView) findViewById(R.id.chill);
        radio6 = (ImageView) findViewById(R.id.theshock);
        radio7 = (ImageView) findViewById(R.id.rad);
        radio8 = (ImageView) findViewById(R.id.eazy);
        radio9 = (ImageView) findViewById(R.id.surf);
        radio10 = (ImageView) findViewById(R.id.virgin);


        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio1.setImageResource(R.drawable.cool93pressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", cool);
                intent.putExtra("imageRadio", R.drawable.logocool);
                intent.putExtra("nameRadio", "Cool93");
                startActivity(intent);
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio2.setImageResource(R.drawable.getpressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", get);
                intent.putExtra("imageRadio", R.drawable.logoget);
                startActivity(intent);

            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio3.setImageResource(R.drawable.looktungpressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", looktung);
                intent.putExtra("imageRadio", R.drawable.looktunglogo);
                startActivity(intent);

            }
        });

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio4.setImageResource(R.drawable.efmpreesed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", efm);
                intent.putExtra("imageRadio", R.drawable.efmlogo);
                startActivity(intent);

            }
        });

        radio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio5.setImageResource(R.drawable.chillpressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", chill);
                intent.putExtra("imageRadio", R.drawable.chilllogo);
                startActivity(intent);

            }
        });
        radio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio6.setImageResource(R.drawable.theshockpressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", theshcok);
                intent.putExtra("imageRadio", R.drawable.theshocklogo);
                startActivity(intent);

            }
        });

        radio7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio7.setImageResource(R.drawable.radpreesed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", rad);
                intent.putExtra("imageRadio", R.drawable.radlogo);
                startActivity(intent);

            }
        });
        radio8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio8.setImageResource(R.drawable.eazypressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", eazy);
                intent.putExtra("imageRadio", R.drawable.logoeazy);
                startActivity(intent);
            }
        });

        radio9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio9.setImageResource(R.drawable.surfpressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", surf);
                intent.putExtra("imageRadio", R.drawable.logosurf);
                startActivity(intent);
            }
        });

        radio10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio10.setImageResource(R.drawable.virginpressed);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", virgin);
                intent.putExtra("imageRadio", R.drawable.logovirgin);
                startActivity(intent);
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

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    tts.speak("ขณะนี้คุณกำลังอยู่ในหน้าวิทยุ กรุณาเลือกสถานี " +
                            "1 คือ 93 CoolFarenheit" +
                            "2 คือ 102.5 Get " +
                            "3 คือ ลูกทุ่งเน็ตเวิร์ค " +
                            "5 คือ 94 EFM" +
                            "6 คือ พุทธเรดิโอ" +
                            "7 คือ เดอะช็อค" +
                            "8 คือ เกาหลี", TextToSpeech.QUEUE_FLUSH, null, "");

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

                    if (mostLikelyThingHeard.toUpperCase().equals("93")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", cool);
                        intent.putExtra("imageRadio", R.drawable.cool93);
                        intent.putExtra("nameRadio", "Cool93");
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ครู")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", cool);
                        intent.putExtra("imageRadio", R.drawable.cool93);
                        intent.putExtra("nameRadio", "Cool93");
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("get")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", get);
                        intent.putExtra("imageRadio", R.drawable.logoget);
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("102.5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", get);
                        intent.putExtra("imageRadio", R.drawable.logoget);
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("94.5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", looktung);
                        intent.putExtra("imageRadio", R.drawable.looktunglogo);
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ลูกทุ่ง")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", looktung);
                        intent.putExtra("imageRadio", R.drawable.looktunglogo);
                        startActivity(intent);


                    } else if (mostLikelyThingHeard.toUpperCase().equals("94")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", efm);
                        intent.putExtra("imageRadio", R.drawable.efmlogo);
                        startActivity(intent);


                    } else if (mostLikelyThingHeard.toUpperCase().equals("EFM")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", efm);
                        intent.putExtra("imageRadio", R.drawable.efmlogo);
                        startActivity(intent);


                    } else if (mostLikelyThingHeard.toUpperCase().equals("chill")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", chill);
                        intent.putExtra("imageRadio", R.drawable.chilllogo);
                        startActivity(intent);


                    } else if (mostLikelyThingHeard.toUpperCase().equals("เดอะช็อค")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", theshcok);
                        intent.putExtra("imageRadio", R.drawable.theshocklogo);
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("RAD")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", rad);
                        intent.putExtra("imageRadio", R.drawable.radlogo);
                        startActivity(intent);

                    }
                }

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //ปรับแต่ง tts
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            tts.setSpeechRate((float) 0.8);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }
}

