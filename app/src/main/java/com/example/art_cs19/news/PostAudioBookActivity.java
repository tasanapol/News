package com.example.art_cs19.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import info.hoang8f.widget.FButton;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PostAudioBookActivity extends AppCompatActivity {
    private Button btnRecord;
    private ImageView imgRecord, imgPauseRecord;
    private EditText edtAudioName, edtDate, edtTime, edtUploader, edtNarrator;
    private TextView tvShow;
    private MediaPlayer mediaPlayer;
    private Intent intent_upload;
    private RotateLoading rotateloading;
    private FButton btnSubmit;
    private ImageButton imgAudioPic;
    private Uri selectedimg = null;
    private Uri resultUri;
    private StorageReference fStorage;
    private DatabaseReference fDatabase, fDatabase2, fDatabase3;
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
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String file_path = null;
    private MediaRecorder mediaRecorder;
    private String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    private Random random;
    private String AudioSavePathInDevice = null;
    private ProgressDialog progressbar;
    private Spinner spnType;
    private FirebaseAuth mAuth , mAuth2;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser, mDatabaseUser2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_audio_book);
        findView();
        DateCalender();


        progressbar = new ProgressDialog(this);
        fStorage = FirebaseStorage.getInstance().getReference();
        fDatabase = FirebaseDatabase.getInstance().getReference().child("Audio");
        fDatabase2 = FirebaseDatabase.getInstance().getReference().child("Audio2");
        fDatabase3 = FirebaseDatabase.getInstance().getReference().child("Audio3");
        random = new Random();
        edtDate.setText(DayName + "/" + MonthName + "/" + YearName);
        edtTime.setText(String.valueOf(time));
        tvPostId.setText(String.valueOf(timeSort));



        mAuth = FirebaseAuth.getInstance();
        mAuth2 = FirebaseAuth.getInstance();

        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("User").child(mCurrentUser.getUid());
        mDatabaseUser2 = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUser2.keepSynced(true);



        List<String> listProvince = new ArrayList<String>();
        listProvince.add("--เลือกประเภท--");
        listProvince.add("บันเทิง");
        listProvince.add("สารคดี ");
        listProvince.add("ความรู้ ");
        ArrayAdapter<String> adapterTravel = new ArrayAdapter<String>
                (this, R.layout.spinner_item, listProvince);
        adapterTravel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapterTravel);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPosting();
                        }
                    });
                } else if (position == 2) {
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPosting2();
                        }
                    });
                } else if (position == 3) {
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPosting3();
                        }
                    });
                }else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imgRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {

                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    imgRecord.setVisibility(View.GONE);
                    imgPauseRecord.setVisibility(View.VISIBLE);
                    rotateloading.start();

                    Toast.makeText(PostAudioBookActivity.this, "Recording started", Toast.LENGTH_LONG).show();
                } else {

                    requestPermission();

                }

            }
        });

        imgPauseRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                imgRecord.setVisibility(View.VISIBLE);
                imgPauseRecord.setVisibility(View.GONE);
                rotateloading.stop();

                Toast.makeText(PostAudioBookActivity.this, "Recording Completed", Toast.LENGTH_LONG).show();

            }
        });
        imgAudioPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 1);
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    new AlertDialog.Builder(PostAudioBookActivity.this)
                            .setTitle("กรุณา Log In")
                            .setMessage("กรุณาล็อกอินเพื่อสร้างหนังสือเสียงค่ะ")
                            .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mAuth.addAuthStateListener(mAuthListener);
                                    Intent intent = new Intent(PostAudioBookActivity.this, LogInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("ไม่", null).show();

                } else {
                    Intent intent = new Intent(PostAudioBookActivity.this, AudioBookMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        ////////////////////////เช็คว่าล็อคอินไว้หรือไม่
        final String user_id = mAuth.getCurrentUser().getUid();
        final String Email = mAuth.getCurrentUser().getEmail();


        //ตรวจสอบว่า user login หรือ log out อยู่่
        mDatabaseUser2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)) {
                    edtUploader.setText(Email);

                } else {
                    Toast.makeText(getApplicationContext(),"คุณต้องตั้งค่า",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }






   /* public void pauseRecording() throws IOException {
        mediaRecorder.stop();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        FileOutputStream paused_file = new FileOutputStream(file_path);
        mediaRecorder.setOutputFile(paused_file.getFD());
    }

    public void resumeRecording() throws IOException {
        mediaRecorder.prepare();
        mediaRecorder.start();
    }*/


    private void findView() {
        rotateloading = (RotateLoading) findViewById(R.id.rotateloading);
        btnSubmit = (FButton) findViewById(R.id.btnSubmit);
        imgAudioPic = (ImageButton) findViewById(R.id.imgAudioPic);
        edtAudioName = (EditText) findViewById(R.id.edtAudioName);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTime = (EditText) findViewById(R.id.edtTime);
        edtNarrator = (EditText) findViewById(R.id.edtNarrator);
        edtUploader = (EditText) findViewById(R.id.edtUploader);
        imgRecord = (ImageView) findViewById(R.id.imgRecord);
        imgPauseRecord = (ImageView) findViewById(R.id.imgPauseRecord);
        tvPostId = (TextView) findViewById(R.id.tvPostId);
        spnType = (Spinner) findViewById(R.id.spnType);

    }

    private void startPosting() {
        progressbar.setMessage("กำลังอัพโหลด กรุณารอสักครู่");
        progressbar.show();
        final String audionName_val = edtAudioName.getText().toString().trim();
        final String uploader_val = edtUploader.getText().toString().trim();
        final String date_val = edtDate.getText().toString().trim();
        final String time_val = edtTime.getText().toString().trim();
        final String narrator_val = edtNarrator.getText().toString().trim();
        final String id_val = tvPostId.getText().toString().trim();
        final DatabaseReference newPost = fDatabase.push();
        if (!audionName_val.toString().trim().equals("")
                && !uploader_val.trim().equals("")
                && !narrator_val.trim().equals("")
                && resultUri != null
                && AudioSavePathInDevice != null) {

            final Uri uri = Uri.fromFile(new File(AudioSavePathInDevice));
            StorageReference filepathAudio = fStorage.child("Audio_Files").child(uri.getLastPathSegment());///ประกาศเป็นตัวแปรหลัก
            filepathAudio.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri AudioUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("audio").setValue(AudioUrl.toString());
                }
            });

            StorageReference filepath = fStorage.child("Audio_Images").child(resultUri.getLastPathSegment());
            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    newPost.child("image").setValue(dowloadUri.toString());

                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newPost.child("title").setValue(audionName_val);
                            newPost.child("username").setValue(mCurrentUser.getUid());
                            newPost.child("date").setValue(date_val);
                            newPost.child("time").setValue(time_val);
                            newPost.child("narrator").setValue(narrator_val);
                            newPost.child("id").setValue(id_val);
                            newPost.child("uploader").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(PostAudioBookActivity.this, AudioBookMainActivity.class));
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    progressbar.dismiss();


                    edtAudioName.setText("");
                    edtUploader.setText("");
                    imgAudioPic.setImageResource(R.drawable.add_btn);

                }
            });
        } else {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(PostAudioBookActivity.this);
            builder.setMessage("กรุณารอกข้อมูลให้ครบทุกอย่าง");
            builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.dismiss();
                }
            });
            builder.show();
            progressbar.dismiss();

        }
    }

    private void startPosting2() {
        progressbar.setMessage("กำลังอัพโหลด กรุณารอสักครู่");
        progressbar.show();
        final String audionName_val = edtAudioName.getText().toString().trim();
        final String uploader_val = edtUploader.getText().toString().trim();
        final String date_val = edtDate.getText().toString().trim();
        final String time_val = edtTime.getText().toString().trim();
        final String narrator_val = edtNarrator.getText().toString().trim();
        final String id_val = tvPostId.getText().toString().trim();
        final DatabaseReference newPost = fDatabase2.push();
        if (!audionName_val.toString().trim().equals("")
                && !uploader_val.trim().equals("")
                && !narrator_val.trim().equals("")
                && resultUri != null
                && AudioSavePathInDevice != null) {

            final Uri uri = Uri.fromFile(new File(AudioSavePathInDevice));
            StorageReference filepathAudio = fStorage.child("Audio_Files").child(uri.getLastPathSegment());///ประกาศเป็นตัวแปรหลัก
            filepathAudio.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri AudioUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("audio").setValue(AudioUrl.toString());
                }
            });

            StorageReference filepath = fStorage.child("Audio_Images").child(resultUri.getLastPathSegment());
            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    newPost.child("image").setValue(dowloadUri.toString());

                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newPost.child("title").setValue(audionName_val);
                            newPost.child("username").setValue(mCurrentUser.getUid());
                            newPost.child("date").setValue(date_val);
                            newPost.child("time").setValue(time_val);
                            newPost.child("narrator").setValue(narrator_val);
                            newPost.child("id").setValue(id_val);
                            newPost.child("uploader").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(PostAudioBookActivity.this, AudioBookMainActivity.class));
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    progressbar.dismiss();
                    edtAudioName.setText("");
                    edtUploader.setText("");
                    imgAudioPic.setImageResource(R.drawable.add_btn);

                }
            });
        } else {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(PostAudioBookActivity.this);
            builder.setMessage("กรุณารอกข้อมูลให้ครบทุกอย่าง");
            builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.dismiss();
                }
            });
            builder.show();
            progressbar.dismiss();

        }
    }

    private void startPosting3() {
        progressbar.setMessage("กำลังอัพโหลด กรุณารอสักครู่");
        progressbar.show();
        final String audionName_val = edtAudioName.getText().toString().trim();
        final String uploader_val = edtUploader.getText().toString().trim();
        final String date_val = edtDate.getText().toString().trim();
        final String time_val = edtTime.getText().toString().trim();
        final String narrator_val = edtNarrator.getText().toString().trim();
        final String id_val = tvPostId.getText().toString().trim();
        final DatabaseReference newPost = fDatabase3.push();
        if (!audionName_val.toString().trim().equals("")
                && !uploader_val.trim().equals("")
                && !narrator_val.trim().equals("")
                && resultUri != null
                && AudioSavePathInDevice != null) {

            final Uri uri = Uri.fromFile(new File(AudioSavePathInDevice));

            StorageReference filepathAudio = fStorage.child("Audio_Files").child(uri.getLastPathSegment());///ประกาศเป็นตัวแปรหลัก

            filepathAudio.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri AudioUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("audio").setValue(AudioUrl.toString());
                }
            });

            StorageReference filepath = fStorage.child("Audio_Images").child(resultUri.getLastPathSegment());
            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    newPost.child("title").setValue(audionName_val);
                    newPost.child("uploader").setValue(uploader_val);
                    newPost.child("date").setValue(date_val);
                    newPost.child("time").setValue(time_val);
                    newPost.child("narrator").setValue(narrator_val);
                    newPost.child("id").setValue(id_val);
                    newPost.child("image").setValue(dowloadUri.toString());

                    progressbar.dismiss();

                    startActivity(new Intent(PostAudioBookActivity.this, AudioBookMainActivity.class));
                    edtAudioName.setText("");
                    edtUploader.setText("");
                    imgAudioPic.setImageResource(R.drawable.add_btn);

                }
            });
        } else {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(PostAudioBookActivity.this);
            builder.setMessage("กรุณารอกข้อมูลให้ครบทุกอย่าง");
            builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.dismiss();
                }
            });
            builder.show();
            progressbar.dismiss();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            selectedimg = data.getData();
            //Crop ภาพ มีการระบุ ขนาดครอปไว้แน่นอนคือ 1500*900 pixel
            CropImage.activity(selectedimg)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMinCropResultSize(800, 400)
                    .setMaxCropResultSize(800, 400)
                    .start(this);
            try {
                imgAudioPic.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //นำภาพที่ตัด(resultUri) บันทึกลงFirebase
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imgAudioPic.setImageURI(resultUri);
                //ดักจับผิดพลาดเมื่อกดออกระหว่างครอบภาพ ภาพจะกลับไปว่างเปล่า
                try {
                    imgAudioPic.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void DateCalender() {
        //เวลา
        DateFormat df = new SimpleDateFormat("HH:mm"); //format time
        time = df.format(Calendar.getInstance().getTime());
        timeSort = (int) (-1 * new Date().getTime() / new Date().getYear() + new Date().getMonth() + new Date().getDay());
        //วันที่
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat DayDate = new SimpleDateFormat("dd");
        DayName = DayDate.format(cal.getTime());
        SimpleDateFormat MonthDate = new SimpleDateFormat("MM");
        MonthName = MonthDate.format(cal.getTime());
        SimpleDateFormat YearDate = new SimpleDateFormat("yyyy");
        YearName = YearDate.format(cal.getTime());
        DayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        DayOfWeekName = String.valueOf(DayOfWeek);
        initialDayofWeekName = String.valueOf(DayOfWeek);
        switch (DayOfWeek) {
            case 7:
                DayOfWeekName = "เสาร์";
                initialDayofWeekName = "ส.";
                break;
            case 1:
                DayOfWeekName = "อาทิตย์";
                initialDayofWeekName = "อา.";
                break;
            case 2:
                DayOfWeekName = "จันทร์";
                initialDayofWeekName = "จ.";
                break;
            case 3:
                DayOfWeekName = "อังคาร";
                initialDayofWeekName = "อ.";
                break;
            case 4:
                DayOfWeekName = "พุธ";
                initialDayofWeekName = "พ.";
                break;
            case 5:
                DayOfWeekName = "พฤหัสบดี";
                initialDayofWeekName = "พฤ.";
                break;
            case 6:
                DayOfWeekName = "ศุกร์";
                initialDayofWeekName = "ศ.";
                break;
        }


    }
    ///////////////////////////////////////////////////////////////////////

    public void MediaRecorderReady() {

        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        mediaRecorder.setOutputFile(AudioSavePathInDevice);

    }

    public String CreateRandomAudioFileName(int string) {

        StringBuilder stringBuilder = new StringBuilder(string);

        int i = 0;
        while (i < string) {

            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO},
                RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {

                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {

                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void checkUserExist() {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}




