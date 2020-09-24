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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    TextView frgtPswrd, registerMe;

    TextInputLayout Email,Pass;
    Button signIn,Phonesign,Googlesign;
    String emailid,pwd;

    FirebaseAuth FAuth;

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

                    final ProgressDialog mDialog = new ProgressDialog(loginActivity.this);
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

                                    Toast.makeText(loginActivity.this, "Congragulations! You Have Sucessfully Login", Toast.LENGTH_LONG).show();


                                    Intent in = new Intent(loginActivity.this, ChefFoodPanel_BottomNavigation.class);
                                    startActivity(in);
                                    finish();
                                } else {
                                    ReusableCodeForAll.showAlert(loginActivity.this, "Verification Failed!", "You Have Not Verified Your Email");
                                }
                            } else {
                                mDialog.dismiss();

                                ReusableCodeForAll.showAlert(loginActivity.this, "Error", task.getException().getMessage());
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
                Intent in = new Intent(loginActivity.this,ChefLoginPhone.class);
                startActivity(in);
                finish();
            }
        });

        Googlesign=findViewById(R.id.GmailButton);
        Googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(loginActivity.this,"Service Not Started Yet",Toast.LENGTH_LONG).show();
            }
        });

    }catch(Exception e)
    {
        Toast.makeText(loginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
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