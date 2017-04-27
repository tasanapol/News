package com.example.art_cs19.news;

import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import at.markushi.ui.CircleButton;

public class SingleActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private String mPost_key = null;
    private DatabaseReference fDatabase;
    private TextToSpeech tts;

    private ImageView fShowSingleImage;
    private TextView fShowSingleTitle;
    private TextView fShowSingleDescription;
    private TextView fShowSingleTime;
    private TextView fShowSingleDate;

    private String post_title;
    private String post_description;
    private String post_date;
    private String post_img;
    private String post_time;

    private CircleButton btnRead;

    private final int REQUEST_SPEECH = 100;
    private int DayOfWeek;
    private String DayOfWeekName;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        tts = new TextToSpeech(this, this, "com.google.android.tts");
        fShowSingleDescription = (TextView) findViewById(R.id.tvShowDescription);
        fShowSingleTitle = (TextView) findViewById(R.id.tvShowTitle);
        fShowSingleImage = (ImageView) findViewById(R.id.imgShowUpload);
        fShowSingleDate = (TextView) findViewById(R.id.tvShowDate);
        fShowSingleTime = (TextView) findViewById(R.id.tvShowTime);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Calendar cal = Calendar.getInstance();
        DayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        DayOfWeekName = String.valueOf(DayOfWeek);
        switch (DayOfWeek) {
            case 7:
                DayOfWeekName = "เสาร์";
                break;
            case 1:
                DayOfWeekName = "อาทิตย์";

                break;
            case 2:
                DayOfWeekName = "จันทร์";

                break;
            case 3:
                DayOfWeekName = "อังคาร";

                break;
            case 4:
                DayOfWeekName = "พุธ";

                break;
            case 5:
                DayOfWeekName = "พฤหัสบดี";

                break;
            case 6:
                DayOfWeekName = "ศุกร์";

                break;
        }

        fDatabase = FirebaseDatabase.getInstance().getReference("News");
        mPost_key = getIntent().getExtras().getString("post_key");


        fDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                post_title = (String) dataSnapshot.child("title").getValue();
                post_description = (String) dataSnapshot.child("description").getValue();
                post_img = (String) dataSnapshot.child("image").getValue();
                post_time = (String) dataSnapshot.child("time").getValue();
                post_date = (String) dataSnapshot.child("date").getValue();

                fShowSingleTitle.setText(post_title);
                fShowSingleDescription.setText(post_description);
                fShowSingleTime.setText(post_time);
                fShowSingleDate.setText(post_date);
                Picasso.with(SingleActivity.this).load(post_img).into(fShowSingleImage);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
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
                   tts.speak("หัวข้อข่าว" + post_title + "ประจำวัน" + DayOfWeekName + "ที่" + post_date + "คือ" + post_description
                            , TextToSpeech.QUEUE_FLUSH, null, "");
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
public void promtspeech() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "เลือกหัวข้อ");
    startActivityForResult(intent, REQUEST_SPEECH);

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

                    if (mostLikelyThingHeard.toUpperCase().equals("อ่าน")) {
                        tts.speak("หัวข้อข่าว" + post_title + "ประจำวันที่" + post_date + "คือ" + post_description
                                , TextToSpeech.QUEUE_FLUSH, null, "");
                    } else if (mostLikelyThingHeard.toUpperCase().equals("กลับ")) {
                        Intent intent = new Intent(SingleActivity.this, NewsMainActivity.class);
                        startActivity(intent);
                    }
                }
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
