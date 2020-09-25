package com.example.epiceateries2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ChefRegistration extends AppCompatActivity {

    String[] Maharashtra={"Nagpur","Pune","Mumbai"};
    String[] Madhyapradesh={"Indore","Ujjain","Bhopal"};

    TextInputLayout Fname,Lname,Email,Pass,cPass,Mobileno,House,Area,Pincode;
    Spinner Statespin,CitySpin;

    Button signup,gmailS,emailS,phoneS;

    FirebaseAuth Fauth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    String fname,lname,emailid,password,confirmpassword,mobile,house,area,pincode,statee,cityy;
    String role="chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);

        Fname = (TextInputLayout) findViewById(R.id.Firstname);
        Lname = (TextInputLayout)findViewById(R.id.Lastname);
        Email = (TextInputLayout)findViewById(R.id.Email);
        Pass = (TextInputLayout)findViewById(R.id.Pwd);
        cPass = (TextInputLayout)findViewById(R.id.CfmPwd);
        Mobileno = (TextInputLayout)findViewById(R.id.MobileNo);
        House= (TextInputLayout)findViewById(R.id.HouseNo);
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
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ChefRegistration.this,android.R.layout.simple_spinner_dropdown_item,list);
                    CitySpin.setAdapter(arrayAdapter);

                }
                if(statee.equals("Madhyapradesh"))
                {
                    ArrayList<String> list=new ArrayList<>();
                    for(String Cities: Madhyapradesh)
                    {
                        list.add(Cities);
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ChefRegistration.this,android.R.layout.simple_spinner_dropdown_item,list);
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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=Fname.getEditText().getText().toString().trim();
                lname=Lname.getEditText().getText().toString().trim();
                emailid=Email.getEditText().getText().toString().trim();
                mobile=Mobileno.getEditText().getText().toString().trim();
                password=Pass.getEditText().getText().toString().trim();
                confirmpassword=cPass.getEditText().getText().toString().trim();
                house=House.getEditText().getText().toString().trim();
                area=Area.getEditText().getText().toString().trim();
                pincode=Pincode.getEditText().getText().toString().trim();

                if(isValid())
                {
                    final ProgressDialog mDialog= new ProgressDialog(ChefRegistration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in process please wait........");
                    mDialog.show();

                    Fauth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.i("Account created","Acc");
                            if(task.isSuccessful())
                            {
                                String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference=FirebaseDatabase.getInstance().getReference("User").child(userId);
                                final HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String,String> hashMap1=new HashMap<>();
                                        hashMap1.put("Fisrt Name",fname);
                                        hashMap1.put("Last Name",lname);
                                        hashMap1.put("Mobile Number",mobile);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("Area",area);

                                        hashMap1.put("Pincode",pincode);
                                        hashMap1.put("City",cityy);
                                        hashMap1.put("State",statee);

                                        firebaseDatabase.getInstance().getReference("chef").
                                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                                setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();
                                                Log.i("tag","email sent");
                                                Fauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            AlertDialog.Builder builder=new AlertDialog.Builder(ChefRegistration.this);
                                                            builder.setMessage("You have Registered! Make sure to verify your email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();

                                                                    String phonenumber="+91"+mobile;
                                                                    Intent in=new Intent(ChefRegistration.this,ChefVerifyPhone.class);
                                                                    in.putExtra("phonenumber",phonenumber);
                                                                    startActivity(in);

                                                                }
                                                            });
                                                            AlertDialog alertDialog=builder.create();
                                                            alertDialog.show();
                                                        }
                                                        else
                                                        {
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.showAlert(ChefRegistration.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }else
                            {
                                mDialog.dismiss();
                                ReusableCodeForAll.showAlert(ChefRegistration.this,"Error",task.getException().getMessage());
                            }
                        }
                    });
                }
            }



        });

        gmailS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChefRegistration.this, "Service Not Started Yet", Toast.LENGTH_LONG).show();
            }
        });

        emailS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent el=new Intent(ChefRegistration.this,loginActivity.class);
                startActivity(el);
                finish();
            }
        });

        phoneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pl=new Intent(ChefRegistration.this,ChefLoginPhone.class);
                startActivity(pl);
                finish();
            }
        });

    }
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
    public boolean isValid()
    {
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Mobileno.setErrorEnabled(false);
        Mobileno.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        cPass.setErrorEnabled(false);
        cPass.setError("");
        Area.setErrorEnabled(false);
        Area.setError("");
        House.setErrorEnabled(false);
        House.setError("");
        Pincode.setErrorEnabled(false);
        Pincode.setError("");


        boolean isValid=false,isValidName=false,isValidlName=false,isValidEmail=false,isValidPassword=false,isValidConfirmPassword=false,isValidMobileNo=false,isValidArea=false,isValidHouse=false,isValidPincode=false;

        if(TextUtils.isEmpty(fname))
        {
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }
        else
        {
            isValidName=true;
        }
        if(TextUtils.isEmpty(lname))
        {
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }
        else
        {
            isValidlName=true;
        }
        if(TextUtils.isEmpty(emailid))
        {
            Email.setErrorEnabled(true);
            Email.setError("Enter Email");
        }
        else
        {
            if(Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
                isValidEmail=true;
            else
            {
                Email.setErrorEnabled(true);
                Email.setError("Enter Valid Email");
            }
        }

        if(TextUtils.isEmpty(password))
        {
            Pass.setErrorEnabled(true);
            Pass.setError("Enter password ");
        }
        else
        {
            if(password.length()<8) {
                Pass.setErrorEnabled(true);
                Pass.setError("Password is weak");
            }
            else
                isValidPassword=true;
        }
        if(TextUtils.isEmpty(confirmpassword))
        {
            cPass.setError("Enter Password Again");
        }
        else
        {
            if(!password.equals(confirmpassword)) {
                cPass.setErrorEnabled(true);
                cPass.setError("Password doesn't match");
            }
            else
                isValidConfirmPassword=true;
        }
        if(TextUtils.isEmpty(mobile))
        {
            Mobileno.setErrorEnabled(true);
            Mobileno.setError("Mobile number is required");
        }
        else
        {
            if(mobile.length()==10)
                isValidMobileNo=true;
            else
            {
                Mobileno.setErrorEnabled(true);
                Mobileno.setError("Invalid Mobile number");
            }
        }
        if(TextUtils.isEmpty(area))
        {
            Area.setErrorEnabled(true);
            Area.setError("Enter Address");
        }
        else
        {
            isValidArea=true;
        }
        if(TextUtils.isEmpty(house))
        {
            House.setErrorEnabled(true);
            House.setError("Enter House No.");
        }
        else
        {
            isValidHouse=true;
        }
        if(TextUtils.isEmpty(pincode))
        {
            Pincode.setErrorEnabled(true);
            Pincode.setError("Enter Pincode");
        }
        else
        {
            isValidPincode=true;
        }
        isValid=(isValidName && isValidlName && isValidEmail && isValidPassword && isValidConfirmPassword && isValidMobileNo && isValidArea && isValidHouse && isValidPincode) ? true :false;
        return isValid;
    }
}