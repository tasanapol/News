package com.example.art_cs19.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;

import info.hoang8f.widget.FButton;

public class PostAudioBookActivity extends AppCompatActivity {

    private Button btnRecord;
    private EditText edtAudioName , edtDate, edtTime, edtUploader , edtNarrator;
    private TextView tvShow;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private String mFileName;
    private Uri uri;
    private MediaPlayer mediaPlayer;
    private Intent intent_upload;
    private RotateLoading rotateloading;
    private FButton btnSubmit;
    private ImageButton imgAudioPic;
    private Uri selectedimg = null;
    private Uri resultUri;
    private StorageReference fStorage;
    private DatabaseReference fDatabase;
    private ProgressDialog progressbar;
    private TextView tvPostId;
    private String time;
    private String date;
    private String MonthName;
    private String DayName;
    private String YearName;
    private String DayOfWeekName;
    private String initialDayofWeekName;
    private int timeSort;
    private int DayOfWeek;
    private static final int GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_audio_book);
        mProgress = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        mediaPlayer = MediaPlayer.create(this, Uri.parse(String.valueOf(mFileName)));
        findView();


        imgAudioPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 1);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

    }


    private void findView() {
        rotateloading = (RotateLoading) findViewById(R.id.rotateloading);
        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnSubmit = (FButton) findViewById(R.id.btnSubmit);
        imgAudioPic = (ImageButton) findViewById(R.id.imgAudioPic);
        edtAudioName = (EditText)findViewById(R.id.edtAudioName);
        edtDate = (EditText)findViewById(R.id.edtDate);
        edtTime = (EditText)findViewById(R.id.edtTime);
        edtUploader = (EditText)findViewById(R.id.edtUploader);

    }
    private void startPosting() {
        progressbar.setMessage("กำลังโพสต์");
        progressbar.show();

        final String audionName_val = edtAudioName.getText().toString().trim();
        final String uploader_val = edtUploader.getText().toString().trim();
        final String date_val = edtDate.getText().toString().trim();
        final String time_val = edtTime.getText().toString().trim();
        final String narrator = edtNarrator.getText().toString().trim();
        final String id_val = tvPostId.getText().toString().trim();

        if (!audionName_val.toString().trim().equals("") && !uploader_val.toString().trim().equals("")
                && resultUri != null
                && !date_val.toString().trim().equals("") && !narrator.toString().trim().equals("")) {


        }
    }





}


