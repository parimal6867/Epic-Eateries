package com.example.epiceateries2.AuthCommon;

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
import com.example.epiceateries2.chefRestaurant.ChefFoodPanel_BottomNavigation;
import com.example.epiceateries2.customersOfApp.CustomerPannel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class sendotp extends AppCompatActivity {

    String verificationId;
    FirebaseAuth FAuth;
    Button verify, Resend;
    TextView text;
    EditText enterCode;
    String phoneNumber;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp);

        phoneNumber = getIntent().getStringExtra("phonenumber").trim();
        enterCode = (EditText) findViewById(R.id.otp);
        text = (TextView)findViewById(R.id.text);

        Resend=(Button)findViewById(R.id.resendOtp);
        verify=(Button)findViewById(R.id.verify);

        FAuth=FirebaseAuth.getInstance();

        Resend.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneNumber);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = enterCode.getText().toString().trim();
                Resend.setVisibility(View.INVISIBLE);

                if (code.isEmpty() && code.length()<6){
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
                Resend.setVisibility(View.VISIBLE);
                text.setVisibility(View.INVISIBLE);
            }
        }.start();


    Resend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Resend.setVisibility(View.INVISIBLE);
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
                    Resend.setVisibility(View.VISIBLE);
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

            Toast.makeText(sendotp.this, e.getMessage(), Toast.LENGTH_LONG).show();

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
                    FAuth=FirebaseAuth.getInstance();

                    databaseReference= FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String role=dataSnapshot.getValue(String.class);
                            if(role.equals("chef"))
                            {
                                Intent in = new Intent(sendotp.this, ChefFoodPanel_BottomNavigation.class);
                                startActivity(in);
                                finish();
                            }
                            else if(role.equals("customer"))
                            {
                                Intent in = new Intent(sendotp.this, CustomerPannel.class);
                                startActivity(in);
                                finish();
                            }
                            /*
                            else if(role.equals("delivery person"))
                            {
                                Intent in = new Intent(EmailLoginActivity.this, DeliveryHomePage.class);
                                startActivity(in);
                                finish();
                            }

                             */
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
                else
                {
                    ReusableCodeForAll.showAlert(sendotp.this,"Error",task.getException().getMessage());
                }
            }
        });

    }

}