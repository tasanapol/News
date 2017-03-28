package com.example.art_cs19.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class NewsMainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private RecyclerView fNewsList;
    private Query fDatabase;
    private final int REQUEST_SPEECH = 100;
    private TextToSpeech tts;
    private ProgressDialog progressbar;
    private String SoundList1, SoundList2, SoundList3, SoundList4, SoundList5, SoundList6, SoundList7, SoundList8, SoundList9, SoundList10;
    private String PageList1, PageList2, PageList3, PageList4, PageList5, PageList6, PageList7, PageList8, PageList9, PageList10;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        tts = new TextToSpeech(this, this, "com.google.android.tts");
        progressbar = new ProgressDialog(this);
        progressbar.setMessage("กำลังโหลด");
        progressbar.show();
        fDatabase = FirebaseDatabase.getInstance().getReference().child("News").orderByChild("id").limitToFirst(10);//ชื่อตารางในfirebase ไม่ใช่ชื่อโครงการนะอาท
        fNewsList = (RecyclerView) findViewById(R.id.recyclerNews);
        fNewsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        fNewsList.setLayoutManager(layoutManager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<News, NewsViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<News, NewsViewHolder>
                (News.class, R.layout.news_row, NewsViewHolder.class, fDatabase) {


            @Override
            public void populateViewHolder(final NewsViewHolder viewHolder, News model, final int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                //เอาลำดับView(getItem).ชื่อตัวแปร มาเก็บในตัวแปรที่ประกาศในคลาส แล้วนำไปเรียกใช้แสดงเสียง
                SoundList1 = getItem(0).getTitle();
                SoundList2 = getItem(1).getTitle();
                SoundList3 = getItem(2).getTitle();
                SoundList4 = getItem(3).getTitle();
                SoundList5 = getItem(4).getTitle();
                SoundList6 = getItem(5).getTitle();
                SoundList7 = getItem(6).getTitle();
                SoundList8 = getItem(7).getTitle();
                SoundList9 = getItem(8).getTitle();
                SoundList10 = getItem(9).getTitle();
                //เอาลำดับเทเบิ้ลในFirebase(getRef).getkey มาเก็บในตัวแปรที่ประกาศในคลาส แล้วนำไปเรียกใช้แสดงข้อมูล
                PageList1 = getRef(0).getKey();
                PageList2 = getRef(1).getKey();
                PageList3 = getRef(2).getKey();
                PageList4 = getRef(3).getKey();
                PageList5 = getRef(4).getKey();
                PageList6 = getRef(5).getKey();
                PageList7 = getRef(6).getKey();
                PageList8 = getRef(7).getKey();
                PageList9 = getRef(8).getKey();
                PageList10 = getRef(9).getKey();


                viewHolder.setImage(getApplicationContext(), model.getImage());
                //ส่งค่า putextras post_key ไปหน้า SingleActivity
                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleintent = new Intent(NewsMainActivity.this, SingleActivity.class);
                        singleintent.putExtra("post_key", post_key);
                        startActivity(singleintent);
                    }
                });
                progressbar.dismiss();

            }
        };


        fNewsList.setAdapter(firebaseRecyclerAdapter);
    }

/////////////////////////////////////////////////คลาส NewsViewHolder นำ setter ที่ประกาศในหน้า News มาใช้แปลงเข้า ตัวแปร
    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        View fView;


        public NewsViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }

        public void setTitle(String title) {
            TextView tvPostTitle = (TextView) fView.findViewById(R.id.tvPostTitle);
            tvPostTitle.setText(title);
        }

        public void setDescription(String description) {
            TextView tvPostDescription = (TextView) fView.findViewById(R.id.tvPostDescription);
            tvPostDescription.setText(description);
        }

        public void setDate(String date) {
            TextView tvPostDate = (TextView) fView.findViewById(R.id.tvPostDate);
            tvPostDate.setText(date);
        }

        public void setTime(String time) {
            TextView tvPostTime = (TextView) fView.findViewById(R.id.tvPostTime);
            tvPostTime.setText(time);
        }

        public void setImage(Context ctx, String image) {
            ImageView imgPostImage = (ImageView) fView.findViewById(R.id.imgPostImage);
            Picasso.with(ctx).load(image).into(imgPostImage);

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this, PostAudioBookActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                    tts.speak("หัวข้อข่าวหนึ่ง คือ " + SoundList1
                                    + "หัวข้อข่าวสอง คือ" + SoundList2
                                    + "หัวข้อข่าวสาม คือ" + SoundList3
                                    + "หัวข้อข่าวสี่ คือ" + SoundList4
                                    + "หัวข้อข่าวห้า คือ" + SoundList5
                                    + "หัวข้อข่าวหก คือ" + SoundList6
                                    + "หัวข้อข่าวเจ็ด คือ" + SoundList7
                                    + "หัวข้อข่าวแปด คือ" + SoundList8
                                    + "หัวข้อข่าวเก้า คือ" + SoundList9
                                    + "หัวข้อข่าวสิบ คือ" + SoundList10
                            , TextToSpeech.QUEUE_FLUSH, null, "");
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

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


                            Intent singleintent = new Intent(NewsMainActivity.this, SingleActivity.class);


                            if (mostLikelyThingHeard.toUpperCase().equals("1")) {
                                singleintent.putExtra("post_key", PageList1);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("2")) {
                                singleintent.putExtra("post_key", PageList2);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("3")) {
                                singleintent.putExtra("post_key", PageList3);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("4")) {
                                singleintent.putExtra("post_key", PageList4);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("5")) {
                                singleintent.putExtra("post_key", PageList5);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("6")) {
                                singleintent.putExtra("post_key", PageList6);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("7")) {
                                singleintent.putExtra("post_key", PageList7);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("8")) {
                                singleintent.putExtra("post_key", PageList8);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("9")) {
                                singleintent.putExtra("post_key", PageList9);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("10")) {
                                singleintent.putExtra("post_key", PageList10);
                                startActivity(singleintent);
                            } else if (mostLikelyThingHeard.toUpperCase().equals("สวัสดี")) {

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
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }


}
