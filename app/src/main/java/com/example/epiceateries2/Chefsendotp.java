package com.example.epiceateries2;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Chefsendotp extends AppCompatActivity {

    String verificationId;
    FirebaseAuth FAuth;
    Button verify, resend;
    TextView text;
    EditText enterCode;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefsendotp);

        phoneNumber = getIntent().getStringExtra("phonenumber").trim();
        enterCode = (EditText) findViewById(R.id.otp);
        text = (TextView)findViewById(R.id.text);

        resend=(Button)findViewById(R.id.resendOtp);
        verify=(Button)findViewById(R.id.verify);

        FAuth=FirebaseAuth.getInstance();
        resend.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

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

            Toast.makeText(Chefsendotp.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String S,PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(S,forceResendingToken);

            verificationId=S;
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInwithPhone(credential);
    }

    private void signInwithPhone(PhoneAuthCredential credential) {

        FAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(Chefsendotp.this,ChefFoodPanel_BottomNavigation.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    ReusableCodeForAll.showAlert(Chefsendotp.this,"Error",task.getException().getMessage());
                }
            }
        });

    }

}