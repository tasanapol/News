package com.example.art_cs19.news;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import info.hoang8f.widget.FButton;

public class PostActivity extends AppCompatActivity {
    private ImageButton imgUpload;
    private EditText edtTitle, edtDescription, edtDate, edtTime;
    private FButton btnSubmit;
    private Uri selectedimg = null;
    private Uri resultUri ;
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
        setContentView(R.layout.activity_post);



        imgUpload = (ImageButton) findViewById(R.id.imgUpload);
        tvPostId = (TextView) findViewById(R.id.tvPostId);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTime = (EditText) findViewById(R.id.edtTime);
        btnSubmit = (FButton) findViewById(R.id.btnSubmit);

        fStorage = FirebaseStorage.getInstance().getReference();
        fDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        progressbar = new ProgressDialog(this);

        //เลือกรูปในแกลลอรี่
        imgUpload.setOnClickListener(new View.OnClickListener() {
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
                //เรียกเมทธอดstartPosting มาแสดงในปุ่ม Submit
                startPosting();

            }
        });

        DateCalender();
        edtDate.setText(DayName + "/" + MonthName + "/" + YearName);
        edtTime.setText(String.valueOf(time));
        tvPostId.setText(String.valueOf(timeSort));

    }


    private void startPosting() {
        progressbar.setMessage("กำลังโพสต์");
        progressbar.show();

        //บันทึก
        final String title_val = edtTitle.getText().toString().trim();
        final String description_val = edtDescription.getText().toString().trim();
        final String date_val = edtDate.getText().toString().trim();
        final String time_val = edtTime.getText().toString().trim();
        final String id_val = tvPostId.getText().toString().trim();


        if (!title_val.toString().trim().equals("") && !description_val.toString().trim().equals("") && resultUri != null && !date_val.toString().trim().equals("")) {
            StorageReference filepath = fStorage
                    .child("News_Images")
                    .child(resultUri.getLastPathSegment());
            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = fDatabase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("description").setValue(description_val);
                    newPost.child("date").setValue(date_val);
                    newPost.child("time").setValue(time_val);
                    newPost.child("id").setValue(id_val);
                    newPost.child("image").setValue(dowloadUri.toString());

                    progressbar.dismiss();

                    startActivity(new Intent(PostActivity.this, NewsMainActivity.class));
                    edtTitle.setText("");
                    edtDescription.setText("");
                    imgUpload.setImageResource(R.drawable.add_btn);

                }
            });
        } else {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(PostActivity.this);
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
                imgUpload.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //นำภาพที่ตัด(resultUri) บันทึกลงFirebase
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imgUpload.setImageURI(resultUri);
                //ดักจับผิดพลาดเมื่อกดออกระหว่างครอบภาพ ภาพจะกลับไปว่างเปล่า
                try {
                    imgUpload.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri));
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

}
