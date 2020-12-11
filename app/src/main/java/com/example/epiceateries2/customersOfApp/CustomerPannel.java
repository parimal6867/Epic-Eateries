package com.example.epiceateries2.customersOfApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.epiceateries2.R;

import com.example.epiceateries2.customerFoodPannel.CustomerCartFragment;
import com.example.epiceateries2.customerFoodPannel.CustomerHomeFragment;
import com.example.epiceateries2.customerFoodPannel.CustomerOrderFragment;
import com.example.epiceateries2.customerFoodPannel.CustomerProfileFragment;
import com.example.epiceateries2.customerFoodPannel.CustomerTrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class CustomerPannel extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pannel);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(name!=null){
            if(name.equalsIgnoreCase("Homepage")){
                loadfragment(new CustomerHomeFragment());
            }
            else if(name.equalsIgnoreCase("Preparingpage")){
                loadfragment(new CustomerTrackFragment());
            }
            else if(name.equalsIgnoreCase("DeliveryOrderpage")){
                loadfragment(new CustomerTrackFragment());
            }
            else if(name.equalsIgnoreCase("ThankYoupage")){
                loadfragment(new CustomerHomeFragment());
            }
        }
        else
        {
            loadfragment(new CustomerHomeFragment());
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.cust_Home:
                fragment=new CustomerHomeFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.cart:
                fragment=new CustomerCartFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.cust_profile:
                fragment=new CustomerProfileFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.Cust_order:
                fragment=new CustomerOrderFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.track:
                fragment=new CustomerTrackFragment();
                break;
        }
        return loadfragment(fragment);

    }

    private boolean loadfragment(Fragment fragment) {

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}