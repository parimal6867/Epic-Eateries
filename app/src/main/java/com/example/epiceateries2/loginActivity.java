package com.example.epiceateries2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {

    TextView frgtPswrd, registerMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Redirects to Forgot Password page
        frgtPswrd=findViewById(R.id.frgtPswrd);
        frgtPswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),frgtPswrdActivity.class);
                startActivity(i);

            }
        });

        // Redirects to Register page
        registerMe=findViewById(R.id.registerMe);
        registerMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),registerActivity.class);
                startActivity(in);

            }
        });
    }
}