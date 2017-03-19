package com.example.art_cs19.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class RadioActivity extends AppCompatActivity {

    String cool = "http://111.223.51.7:8000/;stream/";//93.0
    String get = "http://radio11.plathong.net:7004/;stream.nsv";//102.5 Get Radio
    String looktung ="http://radio2.spicymkt.com:8814/;stream.nsv";//ลูกทุ่งเน็ตเวิร์ค 94.5
    String efm = "http://real3.atimemedia.com:1935/live/efm.sdp/playlist.m3u8";// efm 94.0
    String buddha = "http://61.19.252.133:8000/korat"; // พุทธเรดิโอ 99.0
    String theshcok = "http://radiom.spicymkt.com:1870/;stream.nsv";// theshock
    String korea = "https://radio.ikorea.in.th/CH01"; // iKorea

    CardView radio1, radio2, radio3, radio4, radio5, radio6 , radio7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

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
}
