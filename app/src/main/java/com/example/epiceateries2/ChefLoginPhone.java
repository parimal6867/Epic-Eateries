package com.example.epiceateries2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChefLoginPhone extends AppCompatActivity {

    Button emailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_phone);

        emailLogin = findViewById(R.id.EmailButton);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChefLoginPhone.this,loginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }
}