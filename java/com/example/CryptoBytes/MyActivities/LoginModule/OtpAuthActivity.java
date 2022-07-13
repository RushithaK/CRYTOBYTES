package com.example.CryptoBytes.MyActivities.LoginModule;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CryptoBytes.MainActivity;
import com.example.CryptoBytes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpAuthActivity extends AppCompatActivity {
    TextView changeno;
    EditText getotp;
    Button verifyotp;
    ProgressBar progressBarauthOTP;
    FirebaseAuth firebaseAuth;
    String enteredotp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_auth);
        changeno=findViewById(R.id.idTVChangeNo);
        getotp=findViewById(R.id.ETgetotp);
        verifyotp=findViewById(R.id.TVVerifyOTP);
        progressBarauthOTP=findViewById(R.id.ProgressBarOTP);
        firebaseAuth=FirebaseAuth.getInstance();
        changeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OtpAuthActivity.this, LoginActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredotp=getotp.getText().toString();
                if(enteredotp.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter OTP first!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBarauthOTP.setVisibility(View.VISIBLE);
                    String coderecv=getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(coderecv,enteredotp);
                    SignInwithAuthCredential(credential);
                }
            }
        });
    }

    private void SignInwithAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressBarauthOTP.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"OTP Successful",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(OtpAuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        progressBarauthOTP.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}