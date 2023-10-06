
package com.example.ewyre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {
AppCompatButton trainerbutton,institutionbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AppCompatButton gettrainers = findViewById(R.id.trainerbutton);
        AppCompatButton getinstitutions = findViewById(R.id.institutionbtn);
        gettrainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, TrainerRegistration.class);
                startActivity(intent);

            }
        });

        getinstitutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, InstitutionRegistration.class);
                startActivity(intent);

            }
        });
    }


}