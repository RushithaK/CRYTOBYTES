package com.example.CryptoBytes.MyActivities.LoginModule;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.CryptoBytes.MainActivity;
import com.example.CryptoBytes.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    EditText getphoneno;
    Button sendotp;
    CountryCodePicker countryCodePicker;
    String countryCode;
    String phoneno;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarMain;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBacks;
    String codeSent;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onStart();
        RelativeLayout your_Layout = (RelativeLayout) findViewById(R.id.relLayoutLogin);
        AnimationDrawable animationDrawable = (AnimationDrawable) your_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("308709446867-do658ud7dl1aa70inaukci2nmha201i0.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mAuth=FirebaseAuth.getInstance();

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        //For Phone Number
        countryCodePicker = findViewById(R.id.countryCodeDetect);
        sendotp = findViewById(R.id.idOtpButton);
        getphoneno = findViewById(R.id.idPhonenoET);
        progressBarMain = findViewById(R.id.ProgressBarMain);
        firebaseAuth = FirebaseAuth.getInstance();

        countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = getphoneno.getText().toString();
                if (number.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter A Valid Number", Toast.LENGTH_SHORT).show();
                } else if (number.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Enter A Valid Number", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarMain.setVisibility(View.VISIBLE);
                    phoneno = countryCode + number;
                    PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneno)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(LoginActivity.this)
                            .setCallbacks(mcallBacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);


                }

            }
        });
        mcallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //To automaticaly fetch OTP

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //Means code is sent successfully
                Toast.makeText(getApplicationContext(), "Code Sent successfully", Toast.LENGTH_SHORT).show();
                progressBarMain.setVisibility(View.VISIBLE);
                codeSent = s;
                Intent i = new Intent(LoginActivity.this, OtpAuthActivity.class);
                i.putExtra("otp", codeSent);
                startActivity(i);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);
        activityResultLauncher.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();

                    if (resultCode == RC_SIGN_IN) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        if(task.isSuccessful()) {
                            try {
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                                firebaseAuthWithGoogle(account.getIdToken());
                            } catch (ApiException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            Log.d(TAG, "Failed" );
                        }

                    }
                }
            }
    );

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
}