package com.example.epiceateries2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epiceateries2.chefRestaurant.ChefFoodPanel_BottomNavigation;
import com.example.epiceateries2.chefRestaurant.ChefLoginPhone;
import com.example.epiceateries2.chefRestaurant.ChefRegistration;
import com.example.epiceateries2.customersOfApp.CustomerPannel;
import com.example.epiceateries2.deliveryPerson.DeliveryHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmailLoginActivity extends AppCompatActivity {

    TextView frgtPswrd, registerMe;

    TextInputLayout Email,Pass;
    Button signIn,Phonesign,Googlesign;
    String emailid,pwd;

    FirebaseAuth FAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    try{
        Email = (TextInputLayout)findViewById(R.id.Email);
        Pass = (TextInputLayout)findViewById(R.id.Pwd);

        FAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.login);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailid = Email.getEditText().getText().toString().trim();
                pwd = Pass.getEditText().getText().toString().trim();

                if (isValid()) {

                    final ProgressDialog mDialog = new ProgressDialog(EmailLoginActivity.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Sign In please wait...");
                    mDialog.show();



                    FAuth.signInWithEmailAndPassword(emailid, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

                                if (FAuth.getCurrentUser().isEmailVerified()) {

                                    Toast.makeText(EmailLoginActivity.this, "Congragulations! You Have Sucessfully Login", Toast.LENGTH_LONG).show();

                                    FAuth=FirebaseAuth.getInstance();

                                    databaseReference=FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String role=dataSnapshot.getValue(String.class);
                                            if(role.equals("chef"))
                                            {
                                                Intent in = new Intent(EmailLoginActivity.this, ChefFoodPanel_BottomNavigation.class);
                                                startActivity(in);
                                                finish();
                                            }
                                            else if(role.equals("customer"))
                                            {
                                                Intent in = new Intent(EmailLoginActivity.this, CustomerPannel.class);
                                                startActivity(in);
                                                finish();
                                            }
                                            else if(role.equals("delivery person"))
                                            {
                                                Intent in = new Intent(EmailLoginActivity.this, DeliveryHomePage.class);
                                                startActivity(in);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                } else {
                                    ReusableCodeForAll.showAlert(EmailLoginActivity.this, "Verification Failed!", "You Have Not Verified Your Email");
                                }
                            } else {
                                mDialog.dismiss();

                                ReusableCodeForAll.showAlert(EmailLoginActivity.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }

            }
        });


        // Redirects to Register page
        registerMe = findViewById(R.id.registerMe);
        registerMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ChefRegistration.class);
                startActivity(in);
                finish();
            }
        });

        // Redirects to Forgot Password page
        frgtPswrd = findViewById(R.id.frgtPswrd);
        frgtPswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), frgtPswrdActivity.class);
                startActivity(i);
                finish();
            }
        });

        Phonesign=findViewById(R.id.PhoneButton);
        Phonesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EmailLoginActivity.this, ChefLoginPhone.class);
                startActivity(in);
                finish();
            }
        });

        Googlesign=findViewById(R.id.GmailButton);
        Googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmailLoginActivity.this,"Service Not Started Yet",Toast.LENGTH_LONG).show();
            }
        });

    }catch(Exception e)
    {
        Toast.makeText(EmailLoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
    }
    }

    public boolean isValid()
    {
        Email.setErrorEnabled(false);
        Email.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");

        boolean isValid=false,isValidEmail=false,isValidPass=false;

        if(TextUtils.isEmpty(emailid))
        {
            Email.setErrorEnabled(true);
            Email.setError("Enter Email");
        }
        else
        {
            if(Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
            {
                isValidEmail=true;
            }
            else
            {
                Email.setErrorEnabled(true);
                Email.setError("Invalid Email");
            }
        }

        if(TextUtils.isEmpty(pwd))
        {
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }
        else
        {
            isValidPass=true;
        }

        isValid=(isValidEmail && isValidPass) ? true : false;
        return isValid;

    }

}