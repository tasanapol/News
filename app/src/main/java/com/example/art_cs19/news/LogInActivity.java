package com.example.art_cs19.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {

    private EditText edtLogInUser;
    private EditText edtLogInPass;
    private Button btnLogIn;
    private TextView tvRegister;

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


    }

    private void setView() {
        edtLogInPass = (EditText)findViewById(R.id.edtLogInPass);
        edtLogInUser = (EditText)findViewById(R.id.edtLogInUser);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);
        tvRegister = (TextView)findViewById(R.id.tvRegister);

    }

}
