package com.example.art_cs19.news;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    private EditText edtLogInEmail;
    private EditText edtLogInPass;
    private Button btnLogIn;
    private TextView tvRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setView();
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRegister.setTextColor(0xFFFFFFFF);
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
                finish();
            }
        });


    }

    private void checkLogin() {
        String email = edtLogInEmail.getText().toString().trim();
        String password = edtLogInPass.getText().toString().trim();

        if(!email.equals("") && !password.equals("")){

            mProgress.setMessage("กำลังเข้าสู่ระบบ..");
            mProgress.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mProgress.dismiss();
                            checkUserExist();


                        }else{
                            mProgress.dismiss();
                            new AlertDialog.Builder(LogInActivity.this)
                                    .setTitle("กรอกข้อมูลผิดพลาด")
                                    .setMessage("อีเมลล์หรือรหัสผ่านไม่ถูกต้อง")
                                    .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                edtLogInEmail.requestFocus();
                                        }
                                    })
                                    .setNegativeButton(null, null).show();
                        }
                    }
                });

        }


    }

    private void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();

        //ตรวจสอบว่า user login หรือ log out อยู่่
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent intent = new Intent(LogInActivity.this, AudioBookMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(LogInActivity.this)
                            .setTitle("อะไรยังงงอยู่")
                            .setMessage("ยังงงอยู่")
                            .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton(null, null).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setView() {
        edtLogInPass = (EditText)findViewById(R.id.edtLogInPass);
        edtLogInEmail = (EditText)findViewById(R.id.edtLogInEmail);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);
        tvRegister = (TextView)findViewById(R.id.tvRegister);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUser.keepSynced(true);

        mProgress = new ProgressDialog(this);

    }

}
