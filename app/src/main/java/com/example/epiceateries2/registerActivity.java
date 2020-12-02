package com.example.epiceateries2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {

    TextView tryLogin;
    String[] Maharashtra={"Nagpur","Pune","Mumbai"};
    String[] Madhyapradesh={"Indore","Ujjain","Bhopal"};
    FirebaseAuth Fauth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    EditText check, password, confirmPassword, address;
    Button register;
    String Password,ConfirmPassword,Address,Check,role="chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        check = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        address = findViewById(R.id.Address);

        databaseReference = firebaseDatabase.getInstance().getReference("chef");
        Fauth = FirebaseAuth.getInstance();

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check = check.getText().toString().trim();
                Password = password.getText().toString().trim();
                ConfirmPassword = confirmPassword.getText().toString().trim();
                Address = address.getText().toString().trim();

                if(isValid())
                {
                    final ProgressDialog mDialog= new ProgressDialog(registerActivity.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in process please wait........");
                    mDialog.show();

                    Fauth.createUserWithEmailAndPassword(Check,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
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
                                        hashMap1.put("Address",Address);
                                        hashMap1.put("Password",Password);

                                        firebaseDatabase.getInstance().getReference("chef").
                                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                                setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                Fauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            AlertDialog.Builder builder=new AlertDialog.Builder(registerActivity.this);
                                                            builder.setMessage("You have Registered! Make sure to verify your email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            AlertDialog alertDialog=builder.create();
                                                            alertDialog.show();
                                                        }
                                                        else
                                                        {
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.showAlert(registerActivity.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });


        // Redirects to Login page if the user has already registered
        tryLogin=findViewById(R.id.Already);
        tryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), EmailLoginActivity.class);
                startActivity(i);

            }
        });
    }
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
    public boolean isValid()
    {
        check.setError("");
        password.setError("");
        confirmPassword.setError("");
        address.setError("");

        boolean isValid=false,isValidCheck=false,isValidPassword=false,isValidConfirmPassword=false,isValidAddress=false;
        if(TextUtils.isEmpty( Check))
        {
            check.setError("Enter Email or Mobile no.");
        }
        else
        {
            if(Patterns.EMAIL_ADDRESS.matcher(Check).matches()) {
                isValidCheck=true;
            }
            else if(Patterns.PHONE.matcher(Check).matches())
            {
                isValidCheck=true;
            }
            else
            {
                check.setError("Enter valid Email or Mobile no.");
            }

        }

        if(TextUtils.isEmpty(Password))
        {
            password.setError("Enter password ");
        }
        else
        {
            if(Password.length()<8)
                password.setError("Password is weak");
            else
                isValidPassword=true;
        }
        if(TextUtils.isEmpty(ConfirmPassword))
        {
            confirmPassword.setError("Enter Password Again");
        }
        else
        {
            if(!Password.equals(ConfirmPassword))
                confirmPassword.setError("Password doesn't match");
            else
                isValidConfirmPassword=true;
        }
        if(TextUtils.isEmpty(Address))
        {
            address.setError("Enter Address");
        }
        else
        {
            isValidAddress=true;
        }
        isValid=(isValidCheck && isValidPassword && isValidConfirmPassword && isValidAddress) ? true :false;
        return isValid;
    }

}