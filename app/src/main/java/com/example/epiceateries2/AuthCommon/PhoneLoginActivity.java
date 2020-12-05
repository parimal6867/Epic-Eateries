package com.example.epiceateries2.AuthCommon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epiceateries2.R;
import com.example.epiceateries2.chefRestaurant.ChefRegistration;
import com.google.firebase.auth.FirebaseAuth;

public class PhoneLoginActivity extends AppCompatActivity {

    Button emailLogin, sendotp,gmailLogin;
    EditText mobile;
    FirebaseAuth FAuth;
    String num;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        sendotp=findViewById(R.id.sendotp);
        mobile=findViewById(R.id.mobile);
        FAuth=FirebaseAuth.getInstance();
        signUp=findViewById(R.id.registerMe);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num = "+91"+mobile.getText().toString().trim();
                Intent b=new Intent(PhoneLoginActivity.this, com.example.epiceateries2.AuthCommon.sendotp.class);

                b.putExtra("phonenumber",num);
                startActivity(b);
                finish();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(PhoneLoginActivity.this, ChefRegistration.class);
                startActivity(in);
            }
        });

        emailLogin = findViewById(R.id.EmailButton);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PhoneLoginActivity.this, EmailLoginActivity.class);
                startActivity(in);
                finish();
            }
        });

        gmailLogin = findViewById(R.id.GmailButton);
        gmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PhoneLoginActivity.this, "Service Not Started Yet", Toast.LENGTH_LONG).show();
            }
        });

    }
}