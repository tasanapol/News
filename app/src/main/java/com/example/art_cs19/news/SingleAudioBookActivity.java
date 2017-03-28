package com.example.art_cs19.news;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleAudioBookActivity extends AppCompatActivity {

    private ImageButton imgShowPicBook;
    private TextView tvShowNameBook , tvShowNarrator , tvShowTypeBook , tvAudio , tvShowDate , tvShowTime;
    private Button btnPlay, btnPause, btnStop;
    private MediaPlayer mediaPlayer;
    private String mPost_key;
    private DatabaseReference fDatabase;

    private String post_namebook;
    private String post_narrator;
    private String post_id;
    private String post_img;
    private String post_audio;
    private String post_date;
    private String post_time;
    private String audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_audio_book);
        findView();


        fDatabase = FirebaseDatabase.getInstance().getReference("Audio");
        mPost_key = getIntent().getExtras().getString("post_key");

        audio = getIntent().getExtras().getString("audio");

        btnPlay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);



        fDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                post_namebook = (String) dataSnapshot.child("title").getValue();
                post_audio = (String) dataSnapshot.child("audio").getValue();
                post_date = (String)dataSnapshot.child("date").getValue();
                post_time = (String)dataSnapshot.child("time").getValue();
                post_narrator = (String) dataSnapshot.child("narrator").getValue();
                post_id = (String) dataSnapshot.child("id").getValue();
                post_img = (String) dataSnapshot.child("image").getValue();

                tvShowNameBook.setText(post_namebook);
                tvShowNarrator.setText(post_narrator);
                tvShowTypeBook.setText(post_id);
                tvAudio.setText(post_audio);
                tvShowTime.setText(post_time);
                tvShowDate.setText(post_date);
                Picasso.with(SingleAudioBookActivity.this).load(post_img).into(imgShowPicBook);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mediaPlayer = MediaPlayer.create(SingleAudioBookActivity.this, Uri.parse(audio));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                btnPlay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);
            }
        });

    }

    private void findView() {
        imgShowPicBook = (ImageButton)findViewById(R.id.imgShowPicBook);
        tvShowNameBook = (TextView)findViewById(R.id.tvShowNameBook);
        tvShowNarrator = (TextView)findViewById(R.id.tvShowNarrator);
        tvShowTypeBook = (TextView)findViewById(R.id.tvShowTypeBook);
        tvAudio = (TextView)findViewById(R.id.tvAudio);
        tvShowDate = (TextView)findViewById(R.id.tvShowDate);
        tvShowTime = (TextView)findViewById(R.id.tvShowTime);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPause = (Button)findViewById(R.id.btnPause);
        btnStop = (Button)findViewById(R.id.btnStop);

    }


}
