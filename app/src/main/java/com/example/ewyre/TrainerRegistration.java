package com.example.ewyre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.ImageView;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.ewyre.ViewModels.UserDetails;

public class TrainerRegistration extends AppCompatActivity {

    LinearLayout firstpage,secondpage,thirdpage,selectreg;
    AppCompatButton buttonone,buttontwo,buttonthree,trainerindi,trainerinsti;
    ImageView imageView;

    EditText editfirstname,editemailid,editpassword,editconfirmpswd,editmobno,editage,editdob,editqualification,
            editexperience,editcurrentcmpny,editdepts,editaddress,editpincode,editusrimage,trainerinstname,
            editmidname,editlastname;

    EditText edityrofestablishment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_registration);
        firstpage=findViewById(R.id.firstpage);
        secondpage=findViewById(R.id.secondpage);
        selectreg = findViewById(R.id.selectreg);
        thirdpage=findViewById(R.id.thirdpage);
        buttonone=findViewById(R.id.buttonone);
        buttontwo=findViewById(R.id.buttontwo);
        trainerindi=findViewById(R.id.trainerindi);
        trainerinsti=findViewById(R.id.trainerinsti);
        imageView = findViewById(R.id.imageView);
        editfirstname = findViewById(R.id.editfirstname);
        editmidname = findViewById(R.id.editmidname);
        editlastname = findViewById(R.id.editlastname);
        editemailid = findViewById(R.id.editemailid);
        editpassword = findViewById(R.id.editpassword);
        editconfirmpswd = findViewById(R.id.editconfirmpswd);
        editmobno = findViewById(R.id.editmobno);
        editage = findViewById(R.id.editage);
        editdob = findViewById(R.id.editdob);
        editqualification = findViewById(R.id.editqualification);
        editexperience = findViewById(R.id.editexperience);
        editcurrentcmpny = findViewById(R.id.editcurrentcmpny);
        editdepts = findViewById(R.id.editdepts);
        editaddress = findViewById(R.id.editaddress);
        editpincode = findViewById(R.id.editpincode);
        editusrimage = findViewById(R.id.editusrimage);
        buttonthree = findViewById(R.id.buttonthree);
        trainerinstname = findViewById(R.id.trainerinstname);
        edityrofestablishment = findViewById(R.id.edityrofestablishment);


        trainerindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstpage.setVisibility(View.VISIBLE);
                selectreg.setVisibility(View.GONE);
            }
        });
        trainerinsti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstpage.setVisibility(View.VISIBLE);
                selectreg.setVisibility(View.GONE);
                trainerinstname.setVisibility(View.VISIBLE);
                editage.setVisibility(View.GONE);
                editdob.setVisibility(View.GONE);
                editqualification.setVisibility(View.GONE);
                editexperience.setVisibility(View.GONE);
                editcurrentcmpny.setVisibility(View.GONE);
                edityrofestablishment.setVisibility(View.VISIBLE);

            }
        });


        buttonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstpage.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                secondpage.setVisibility(View.VISIBLE);
            }
        });

        buttontwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondpage.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                thirdpage.setVisibility(View.VISIBLE);
            }
        });

        buttonthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerRegistration.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }
//    public void savetrainer(){
//        UserDetails regtrainerdata=new UserDetails();
//
//        regtrainerdata.FirstName=editfirstname.getText().toString();
//        regtrainerdata.MiddleName=editmidname.getText().toString();
//        regtrainerdata.LastName=editlastname.getText().toString();
//        regtrainerdata.PhoneNumber=Integer.getInteger(editmobno.getText().toString());
//        regtrainerdata.Email=editemailid.getText().toString();
//        regtrainerdata.Email=editpassword.getText().toString();
//
//    }
}