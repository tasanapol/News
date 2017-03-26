package com.example.art_cs19.news;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

public class RadioActivity extends AppCompatActivity  implements TextToSpeech.OnInitListener {

    String cool = "http://111.223.51.7:8000/;stream/";//93.0
    String get = "http://radio11.plathong.net:7004/;stream.nsv";//102.5 Get Radio
    String looktung ="http://radio2.spicymkt.com:8814/;stream.nsv";//ลูกทุ่งเน็ตเวิร์ค 94.5
    String efm = "http://real3.atimemedia.com:1935/live/efm.sdp/playlist.m3u8";// efm 94.0
    String buddha = "http://61.19.252.133:8000/korat"; // พุทธเรดิโอ 99.0
    String theshcok = "http://radiom.spicymkt.com:1870/;stream.nsv";// theshock
    String korea = "https://radio.ikorea.in.th/CH01"; // iKorea

    CardView radio1, radio2, radio3, radio4, radio5, radio6 , radio7;

    private final int REQUEST_SPEECH = 100;

    private TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        tts = new TextToSpeech(this, this, "com.google.android.tts");

        radio1 = (CardView)findViewById(R.id.radio1);
        radio2 = (CardView)findViewById(R.id.radio2);
        radio3 = (CardView)findViewById(R.id.radio3);
        radio4 = (CardView)findViewById(R.id.radio4);
        radio5 = (CardView)findViewById(R.id.radio5);
        radio6 = (CardView)findViewById(R.id.radio6);
        radio7 = (CardView)findViewById(R.id.radio7);


        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", cool);
                intent.putExtra("imageRadio" ,R.drawable.cool93);
                intent.putExtra("nameRadio", "Cool93");
                startActivity(intent);

            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", get);
                intent.putExtra("imageRadio" ,R.drawable.get102);
                startActivity(intent);
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", looktung);
                intent.putExtra("imageRadio" ,R.drawable.looktung);
                startActivity(intent);
            }
        });

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", efm);
                intent.putExtra("imageRadio" ,R.drawable.efm);
                startActivity(intent);
            }
        });

        radio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", buddha);
                intent.putExtra("imageRadio" ,R.drawable.buddha);
                startActivity(intent);
            }
        });
        radio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", theshcok);
                intent.putExtra("imageRadio" ,R.drawable.theshock);
                startActivity(intent);
            }
        });

        radio7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                intent.putExtra("myFm", korea);
                intent.putExtra("imageRadio" ,R.drawable.korea);
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
    protected void  onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_SPEECH){
            if(resultCode == RESULT_OK){
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if(matches.size()== 0){


                }else {
                    //คำสั่งเสียง
                    String mostLikelyThingHeard = matches.get(0);

                    if (mostLikelyThingHeard.toUpperCase().equals("93")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", cool);
                        intent.putExtra("imageRadio" ,R.drawable.cool93);
                        intent.putExtra("nameRadio", "Cool93");
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("ครู")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", cool);
                        intent.putExtra("imageRadio" ,R.drawable.cool93);
                        intent.putExtra("nameRadio", "Cool93");
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("get")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", get);
                        intent.putExtra("imageRadio" ,R.drawable.get102);
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("102.5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", get);
                        intent.putExtra("imageRadio" ,R.drawable.get102);
                        startActivity(intent);

                    } else if (mostLikelyThingHeard.toUpperCase().equals("94.5")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", looktung);
                        intent.putExtra("imageRadio" ,R.drawable.looktung);
                        startActivity(intent);

                    }else if (mostLikelyThingHeard.toUpperCase().equals("ลูกทุ่ง")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", looktung);
                        intent.putExtra("imageRadio" ,R.drawable.looktung);
                        startActivity(intent);


                    }else if (mostLikelyThingHeard.toUpperCase().equals("94")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", efm);
                        intent.putExtra("imageRadio" ,R.drawable.efm);
                        startActivity(intent);


                    }else if (mostLikelyThingHeard.toUpperCase().equals("EFM")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", efm);
                        intent.putExtra("imageRadio" ,R.drawable.efm);
                        startActivity(intent);


                    }else if (mostLikelyThingHeard.toUpperCase().equals("พุทธเรดิโอ")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", buddha);
                        intent.putExtra("imageRadio" ,R.drawable.buddha);
                        startActivity(intent);


                    }else if (mostLikelyThingHeard.toUpperCase().equals("เดอะช็อค")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", theshcok);
                        intent.putExtra("imageRadio" ,R.drawable.theshock);
                        startActivity(intent);

                    }else if (mostLikelyThingHeard.toUpperCase().equals("เกาหลี")) {
                        Intent intent = new Intent(RadioActivity.this, RadioSingleActivity.class);
                        intent.putExtra("myFm", korea);
                        intent.putExtra("imageRadio" ,R.drawable.korea);
                        startActivity(intent);

                    }
                }


            }else if(resultCode == RESULT_CANCELED){


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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

