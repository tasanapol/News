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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class RadioActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private String cool = "http://111.223.51.7:8000/;stream/";//93.0
    private String get = "http://radio11.plathong.net:7004/;stream.nsv";//102.5 Get Radio
    private String looktung = "http://radio2.spicymkt.com:8814/;stream.nsv";//ลูกทุ่งเน็ตเวิร์ค 94.5
    private String efm = "http://real3.atimemedia.com:1935/live/efm.sdp/playlist.m3u8";// efm 94.0
    private String theshcok = "http://radiom.spicymkt.com:1870/;stream.nsv";// theshock
    private String eazy = "http://wzedge2.becteroradio.com/Eazy/Eazy-Listen-Med/playlist.m3u8"; //105.5
    private String virgin = "http://wzedge2.becteroradio.com/Star/Star-Listen-Med/playlist.m3u8"; //virgin star Fm
    private String surf = "http://112.121.150.133:9626/;stream.nsv"; //surf 93.5
    private String chill = "http://real3.atimemedia.com:1935/live/chill.sdp/playlist.m3u8";//chill
    private String rad = "http://wzedge2.becteroradio.com/Rad/Rad-Listen-Med/playlist.m3u8";//RadRadio ตื้ดๆ

    private ImageView radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10;

    private final int REQUEST_SPEECH = 100;

    private TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        findView();

        Picasso.with(RadioActivity.this).load(R.drawable.cool93).fit().into(radio1);
        Picasso.with(RadioActivity.this).load(R.drawable.get).fit().into(radio2);
        Picasso.with(RadioActivity.this).load(R.drawable.looktung).fit().into(radio3);
        Picasso.with(RadioActivity.this).load(R.drawable.efm).fit().into(radio4);
        Picasso.with(RadioActivity.this).load(R.drawable.chill).fit().into(radio5);
        Picasso.with(RadioActivity.this).load(R.drawable.theshock).fit().into(radio6);
        Picasso.with(RadioActivity.this).load(R.drawable.rad).fit().into(radio7);
        Picasso.with(RadioActivity.this).load(R.drawable.eazy).fit().into(radio8);
        Picasso.with(RadioActivity.this).load(R.drawable.surf).fit().into(radio9);
        Picasso.with(RadioActivity.this).load(R.drawable.virgin).fit().into(radio10);

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.cool93pressed).fit().into(radio1);
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
                Picasso.with(RadioActivity.this).load(R.drawable.getpressed).fit().into(radio2);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", get);
                intent.putExtra("imageRadio", R.drawable.logoget);
                startActivity(intent);

            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.looktungpressed).fit().into(radio3);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", looktung);
                intent.putExtra("imageRadio", R.drawable.looktunglogo);
                startActivity(intent);

            }
        });

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.efmpreesed).fit().into(radio4);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", efm);
                intent.putExtra("imageRadio", R.drawable.efmlogo);
                startActivity(intent);

            }
        });

        radio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.chillpressed).fit().into(radio5);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", chill);
                intent.putExtra("imageRadio", R.drawable.chilllogo);
                startActivity(intent);

            }
        });
        radio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.theshockpressed).fit().into(radio6);
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
                Picasso.with(RadioActivity.this).load(R.drawable.eazypressed).fit().into(radio8);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", eazy);
                intent.putExtra("imageRadio", R.drawable.logoeazy);
                startActivity(intent);
            }
        });

        radio9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.surfpressed).fit().into(radio9);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", surf);
                intent.putExtra("imageRadio", R.drawable.logosurf);
                startActivity(intent);
            }
        });

        radio10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(RadioActivity.this).load(R.drawable.virgin).fit().into(radio10);
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", virgin);
                intent.putExtra("imageRadio", R.drawable.logovirgin);
                startActivity(intent);
            }
        });


    }

    private void findView() {
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
                    speakWords("รายชื่อสถานี" +
                            "1 คือ 93 CoolFarenheit" +
                            "2 คือ 102.5 Get " +
                            "3 คือ ลูกทุ่งเน็ตเวิร์ค " +
                            "4 คือ 94.0 EFM " +
                            "5 คือ 104.5 Chill " +
                            "6 คือ เดอะช็อค" +
                            "7 คือ เรด เพลงตื้ด" +
                            "8 คือ 105.5 Easy FM " +
                            "9 คือ 93.5 Surf FM" +
                            "10 คือ 98.0 Virgin star");

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
                    } else if (mostLikelyThingHeard.toUpperCase().equals("Easy")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", eazy);
                        intent.putExtra("imageRadio", R.drawable.logoeazy);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("105.5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", eazy);
                        intent.putExtra("imageRadio", R.drawable.logoeazy);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("Surf")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", surf);
                        intent.putExtra("imageRadio", R.drawable.logosurf);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("93.5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", surf);
                        intent.putExtra("imageRadio", R.drawable.logosurf);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("Virgin")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", virgin);
                        intent.putExtra("imageRadio", R.drawable.logovirgin);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("98.0")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", virgin);
                        intent.putExtra("imageRadio", R.drawable.logovirgin);
                        startActivity(intent);
                    }else if(mostLikelyThingHeard.toUpperCase().equals("1")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", cool);
                        intent.putExtra("imageRadio", R.drawable.cool93);
                        intent.putExtra("nameRadio", "Cool93");
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("2")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", get);
                        intent.putExtra("imageRadio", R.drawable.logoget);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("3")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", looktung);
                        intent.putExtra("imageRadio", R.drawable.looktunglogo);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("4")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", efm);
                        intent.putExtra("imageRadio", R.drawable.efmlogo);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", chill);
                        intent.putExtra("imageRadio", R.drawable.chilllogo);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("6")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", theshcok);
                        intent.putExtra("imageRadio", R.drawable.theshocklogo);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("7")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", rad);
                        intent.putExtra("imageRadio", R.drawable.radlogo);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("8")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", eazy);
                        intent.putExtra("imageRadio", R.drawable.logoeazy);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("9")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", surf);
                        intent.putExtra("imageRadio", R.drawable.logosurf);
                        startActivity(intent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("10")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", virgin);
                        intent.putExtra("imageRadio", R.drawable.logovirgin);
                        startActivity(intent);
                    }else if (mostLikelyThingHeard.toUpperCase().equals("กลับหน้าเมนู")) {
                        Intent intent = new Intent(RadioActivity.this, ChoosedActivity.class);
                        startActivity(intent);
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
    //ปรับแต่ง tts
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            tts.setSpeechRate((float) 0.8);
            speakWords("ขณะนี้คุณกำลังอยู่ในหน้าวิทยุ กรุณาเลือกรายการ หรือกดปุ่มลดเสียงเพื่อฟังรายชื่อวิทยุ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }
}

