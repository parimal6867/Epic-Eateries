package com.example.epiceateries2.chefRestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epiceateries2.R;
import com.example.epiceateries2.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ChefVerifyPhone extends AppCompatActivity {

    String verificationId;
    FirebaseAuth FAuth;
    Button verify, resend;
    TextView text;
    EditText enterCode;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_verify_phone);

        phoneNumber = getIntent().getStringExtra("phonenumber").trim();
        enterCode = (EditText) findViewById(R.id.otp);
        text = (TextView)findViewById(R.id.text);

        resend=(Button)findViewById(R.id.resendOtp);
        verify=(Button)findViewById(R.id.verify);

        FAuth=FirebaseAuth.getInstance();
        resend.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code=enterCode.getText().toString().trim();
                resend.setVisibility(View.INVISIBLE);

                if(code.isEmpty() && code.length()<6)
                {
                    enterCode.setError("Enter code");
                    enterCode.requestFocus();
                    return;
                }
                verifyCode(code);



            }
        });

        new CountDownTimer(60000,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                text.setVisibility(View.VISIBLE);
                text.setText("Resend code within "+millisUntilFinished/1000+" Seconds");
            }

            @Override
            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                text.setVisibility(View.INVISIBLE);
            }
        }.start();




        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resend.setVisibility(View.INVISIBLE);
                ResendOtp(phoneNumber);

                new CountDownTimer(60000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                        text.setVisibility(View.VISIBLE);
                        text.setText("Resend Code Within"+millisUntilFinished/1000+"Seconds");

                    }

                    /**
                     * Callback fired when the time is up.
                     */
                    @Override
                    public void onFinish() {
                        resend.setVisibility(View.VISIBLE);
                        text.setVisibility(View.INVISIBLE);

                    }
                }.start();
            }
        });



    }

    private void ResendOtp(String phoneNumber) {

        sendVerificationCode(phoneNumber);

    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
                );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String S,PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(S,forceResendingToken);

            verificationId=S;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                enterCode.setText(code);   //Auto Verification
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(ChefVerifyPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        linkCredential(credential);
    }

    private void linkCredential(PhoneAuthCredential credential) {

        FAuth.getCurrentUser().linkWithCredential(credential).
                addOnCompleteListener(ChefVerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Intent intent=new Intent(ChefVerifyPhone.this, ChefFoodPanel_BottomNavigation.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            ReusableCodeForAll.showAlert(ChefVerifyPhone.this,"Error",task.getException().getMessage());
                        }

                    }
                });
    }
}