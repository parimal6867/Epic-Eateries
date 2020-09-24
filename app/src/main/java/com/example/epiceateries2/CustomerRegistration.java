package com.example.epiceateries2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerRegistration extends AppCompatActivity {

    String[] Maharashtra={"Nagpur","Pune","Mumbai"};
    String[] Madhyapradesh={"Indore","Ujjain","Bhopal"};

    TextInputLayout Fname,Lname,Email,Pass,cPass,Mobileno,Localaddress,Area,Pincode;
    Spinner Statespin,CitySpin;

    Button signup,gmailS,emailS,phoneS;

    FirebaseAuth Fauth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    String fname,lname,emailid,password,confirmpassword,mobile,localAddress,area,pincode,statee,cityy;
    String role="customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        Fname = (TextInputLayout) findViewById(R.id.Firstname);
        Lname = (TextInputLayout)findViewById(R.id.Lastname);
        Email = (TextInputLayout)findViewById(R.id.Email);
        Pass = (TextInputLayout)findViewById(R.id.Pwd);
        cPass = (TextInputLayout)findViewById(R.id.CfmPwd);
        Mobileno = (TextInputLayout)findViewById(R.id.MobileNo);
        Localaddress= (TextInputLayout)findViewById(R.id.LocalAddress);
        Area = (TextInputLayout)findViewById(R.id.Area);
        Pincode = (TextInputLayout)findViewById(R.id.Pincode);

        Statespin=(Spinner)findViewById(R.id.Statee);
        CitySpin=(Spinner)findViewById(R.id.Cityy);

        signup=(Button)findViewById(R.id.signUp);
        emailS=(Button)findViewById(R.id.emailS);
        phoneS=(Button)findViewById(R.id.PhoneS);
        gmailS=(Button)findViewById(R.id.gmailS);

        Statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object value=parent.getItemAtPosition(position);
                statee=value.toString().trim();
                if(statee.equals("Maharashtra"))
                {
                    ArrayList<String> list=new ArrayList<>();
                    for(String Cities: Maharashtra)
                    {
                        list.add(Cities);
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(CustomerRegistration.this,android.R.layout.simple_spinner_dropdown_item,list);
                    CitySpin.setAdapter(arrayAdapter);

                }
                if(statee.equals("Madhyapradesh"))
                {
                    ArrayList<String> list=new ArrayList<>();
                    for(String Cities: Madhyapradesh)
                    {
                        list.add(Cities);
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(CustomerRegistration.this,android.R.layout.simple_spinner_dropdown_item,list);
                    CitySpin.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CitySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value=parent.getItemAtPosition(position);
                cityy=value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseReference = firebaseDatabase.getInstance().getReference("chef");
        Fauth = FirebaseAuth.getInstance();

    }
}