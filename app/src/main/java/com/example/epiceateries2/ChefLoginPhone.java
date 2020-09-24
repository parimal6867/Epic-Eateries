package com.example.epiceateries2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ChefLoginPhone extends AppCompatActivity {

    Button emailLogin, sendotp,gmailLogin;
    EditText mobile;
    FirebaseAuth FAuth;
    String num;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_phone);

        sendotp=findViewById(R.id.sendotp);
        mobile=findViewById(R.id.mobile);
        FAuth=FirebaseAuth.getInstance();
        signUp=findViewById(R.id.registerMe);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num = "+91"+mobile.getText().toString().trim();
                Intent b=new Intent(ChefLoginPhone.this,Chefsendotp.class);

                b.putExtra("phonenumber",num);
                startActivity(b);
                finish();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ChefLoginPhone.this,ChefRegistration.class);
                startActivity(in);
            }
        });

        emailLogin = findViewById(R.id.EmailButton);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChefLoginPhone.this,loginActivity.class);
                startActivity(in);
                finish();
            }
        });

        gmailLogin = findViewById(R.id.GmailButton);
        gmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChefLoginPhone.this, "Service Not Started Yet", Toast.LENGTH_LONG).show();
            }
        });

    }
}