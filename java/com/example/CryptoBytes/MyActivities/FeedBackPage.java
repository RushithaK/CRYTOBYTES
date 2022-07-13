package com.example.CryptoBytes.MyActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CryptoBytes.MyModels.UserFeedBack;
import com.example.CryptoBytes.MyUtils.MyGeneralUtils;
import com.example.CryptoBytes.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FeedBackPage extends AppCompatActivity {
    private TextInputEditText fEmail,fName;
    private EditText fSuggest;
    private AppCompatTextView ratingCallback;
    private AppCompatRatingBar ratingBar;
    private String name,email,suggestion,rating;
    private MaterialButton submit;
    private FirebaseDatabase rootNode;
    private DatabaseReference db;
    private UserFeedBack userFeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_page);
        fEmail=findViewById(R.id.feedbackEmail);
        fName=findViewById(R.id.feedbackName);
        fSuggest=findViewById(R.id.feedbackSuggestions);
        ratingBar=findViewById(R.id.feedbackRating);
        submit=findViewById(R.id.feedbackSubmit);
        ratingCallback=findViewById(R.id.ratingCallback);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String temp="";
                if(v>=0 && v<=1)
                    temp="Bad";
                else if(v>1 && v<=2)
                    temp="Need Improvement";
                else if(v>2 && v<=3)
                    temp="Good";
                else if(v>3 && v<=4)
                    temp="Better";
                else if(v>4 && v<=5)
                    temp="Best";
                ratingCallback.setText(temp);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=fName.getText().toString();
                email=fEmail.getText().toString();
                suggestion=fSuggest.getText().toString();
                rating=String.valueOf(ratingBar.getRating());
                if(!name.isEmpty() && !rating.isEmpty())
                {
                    userFeedBack = new UserFeedBack(name,email,suggestion,rating);
                    rootNode = FirebaseDatabase.getInstance();
                    db=rootNode.getReference(UserFeedBack.class.getSimpleName());
                    String dataAndTime= MyGeneralUtils.getCurrentAndTimeAsString();
                    //db.push().setValue(userFeedBack)
                    db.child(dataAndTime).setValue(userFeedBack).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"feedback submitted successfully!",Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alertdialog=new AlertDialog.Builder(FeedBackPage.this);
                            alertdialog.setTitle("Message");
                            alertdialog.setMessage("Thank you for the feedback!");
                            alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            alertdialog.show();
                        }
                    });
                    fName.setText("");
                    fEmail.setText("");
                    fSuggest.setText("");
                    ratingBar.setRating(0);
                }else if(name.isEmpty() && rating.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Name and Email fields can't ne empty",Toast.LENGTH_SHORT).show();
                }else if(name.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Name field can't ne empty",Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email field can't ne empty",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}