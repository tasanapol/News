package com.example.art_cs19.news;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtEmail, edtTelephone, edtComfirmPassword;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelephone = (EditText) findViewById(R.id.edtTelephone);
        edtComfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        mProgress = new ProgressDialog(this);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

    }

    private void startRegister() {
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        String Telephone = edtTelephone.getText().toString().trim();


        if (!username.equals("") && !email.equals("") && !Telephone.equals("")) {
            if (password.length() < 8 && password.length() > 10) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("กรอกข้อมูลผิดพลาด")
                        .setMessage("กรุณากรอกรหัสผ่านให้มากกว่า 8 แต่น้อยกว่า 10 ")
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edtPassword.requestFocus();
                            }
                        })
                        .setNegativeButton(null, null).show();
            }else if(!edtComfirmPassword.getText().toString().equals(edtPassword.getText().toString())){
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("กรอกข้อมูลผิดพลาด")
                        .setMessage("กรุณากรอกรหัสผ่านให้ตรงกับคอนเฟิร์มรหัสผ่าน")
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edtPassword.requestFocus();
                            }
                        })
                        .setNegativeButton(null, null).show();
            } else {
                mProgress.setMessage("กำลังบันทึก...");
                mProgress.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("name").setValue(username);
                            current_user_db.child("image").setValue("default");

                            mProgress.dismiss();

                            Intent intent = new Intent(RegisterActivity.this, AudioBookMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {

                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("กรุณากรอกอีเมลล์ที่ถูกต้อง, หรือรหัสผ่านให้ครบ 8-10 ตัว")
                                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            edtEmail.requestFocus();
                                        }
                                    }).setNegativeButton("ไม่", null).show();
                            mProgress.dismiss();

                        }

                    }
                });
            }

        } else {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("กรอกข้อมูลผิดพลาด")
                    .setMessage("กรุณากรอกข้อมูลให้ครบทุกช่อง")
                    .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(null, null).show();
        }
    }

}
