package com.example.epiceateries2.AuthCommon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epiceateries2.R;
import com.example.epiceateries2.RegisterFirst;

public class LoginActivity extends AppCompatActivity {


    TextView googleText,emailText,phoneText,registerMe;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        

        googleText=(TextView)findViewById(R.id.googleText);
        emailText=(TextView)findViewById(R.id.emailText);
        phoneText=(TextView)findViewById(R.id.phoneText);

        googleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Service Not Started Yet", Toast.LENGTH_LONG).show();
            }
        });

        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent el =new Intent(LoginActivity.this, EmailLoginActivity.class);
                startActivity(el);
                finish();
            }
        });

        phoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pl=new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(pl);
                finish();
            }
        });

        registerMe=findViewById(R.id.registerme);
        registerMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r=new Intent(LoginActivity.this, RegisterFirst.class);
                startActivity(r);
                finish();

            }
        });
        registerMe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                registerMe.getPaint().setUnderlineText(true);
                return false;
            }
        });

    }
}