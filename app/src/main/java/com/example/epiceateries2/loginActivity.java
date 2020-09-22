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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    TextView frgtPswrd, registerMe;

    EditText email,pass;
    Button signIn,Phonesign;
    String emailid,pwd;

    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    try{
        email = findViewById(R.id.emailLogin);
        pass = findViewById(R.id.Password);

        FAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.login);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailid = email.getText().toString().trim();
                pwd = pass.getText().toString().trim();

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

    }catch(Exception e)
    {
        Toast.makeText(loginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
    }
    }

    public boolean isValid()
    {

        email.setError("");
        pass.setError("");

        boolean isValid=false,isValidEmail=false,isValidPass=false;

        if(TextUtils.isEmpty(emailid))
        {
            email.setError("Enter Email");
        }
        else
        {
            if(Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
            {
                isValidEmail=true;
            }
            else
            {
                email.setError("Invalid Email");
            }
        }

        if(TextUtils.isEmpty(pwd))
        {
            pass.setError("Enter Password");
        }
        else
        {
            isValidPass=true;
        }

        isValid=(isValidEmail && isValidPass) ? true : false;
        return isValid;

    }

}