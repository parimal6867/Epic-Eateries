package com.example.epiceateries2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class registerActivity extends AppCompatActivity {

    TextView tryLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Redirects to Login page if the user has already registered
        tryLogin=findViewById(R.id.Already);
        tryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),loginActivity.class);
                startActivity(i);

            }
        });
    }
}