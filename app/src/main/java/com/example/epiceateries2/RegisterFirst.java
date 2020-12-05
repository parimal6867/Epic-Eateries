package com.example.epiceateries2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.epiceateries2.AuthCommon.LoginActivity;
import com.example.epiceateries2.chefRestaurant.ChefRegistration;
import com.example.epiceateries2.customersOfApp.CustomerRegistration;

public class RegisterFirst extends AppCompatActivity {

    Button customerReg,restaurantReg,deliveryReg;
    TextView userlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);

        customerReg=findViewById(R.id.CustomerReg);
        restaurantReg=findViewById(R.id.RestaurantReg);
        //deliveryReg=findViewById(R.id.DeliveryReg);

        userlog=findViewById(R.id.userLog);

        customerReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cr=new Intent(RegisterFirst.this, CustomerRegistration.class);
                startActivity(cr);
                finish();
            }
        });

        restaurantReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rr=new Intent(RegisterFirst.this, ChefRegistration.class);
                startActivity(rr);
                finish();
            }
        });
        /*
        deliveryReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dr=new Intent(RegisterFirst.this, DeliveryRegistration.class);
                startActivity(dr);
                finish();
            }
        });
        */


        userlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ul=new Intent(RegisterFirst.this, LoginActivity.class);
                startActivity(ul);
                finish();
            }
        });

    }
}