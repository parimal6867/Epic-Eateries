package com.example.epiceateries2.chefRestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.epiceateries2.R;
import com.example.epiceateries2.chefFoodPanel.ChefHomeFragement;
import com.example.epiceateries2.chefFoodPanel.ChefOrderFragement;
import com.example.epiceateries2.chefFoodPanel.ChefPendingOrdersFragement;
import com.example.epiceateries2.chefFoodPanel.ChefProfileFragement;
import com.example.epiceateries2.customerFoodPannel.CustomerHomeFragment;
import com.example.epiceateries2.customerFoodPannel.CustomerTrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChefFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_food_panel__bottom_navigation);

        BottomNavigationView navigationView = findViewById(R.id.chef_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(name!=null){
            if(name.equalsIgnoreCase("Orderpage")){
                loadcheffragement(new ChefPendingOrdersFragement());
            }
            else if(name.equalsIgnoreCase("Confirmpage")){
                loadcheffragement(new ChefOrderFragement());
            }
            else if(name.equalsIgnoreCase("AcceptOrderpage")){
                loadcheffragement(new CustomerTrackFragment());
            }
            else if(name.equalsIgnoreCase("Deliveredpage")){
                loadcheffragement(new CustomerHomeFragment());
            }
        }
        else
        {
            loadcheffragement(new ChefHomeFragement());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId())
        {
            case R.id.chefHome:
                fragment = new ChefHomeFragement();
                break;

            case R.id.chefProfile:
                fragment = new ChefProfileFragement();
                break;

            case R.id.pendingOrders:
                fragment = new ChefPendingOrdersFragement();
                break;

            case R.id.Orders:
                fragment = new ChefOrderFragement();
                break;


        }

        return loadcheffragement(fragment);
    }

    private boolean loadcheffragement(Fragment fragment) {

        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }


}