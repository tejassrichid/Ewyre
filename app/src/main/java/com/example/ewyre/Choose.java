package com.example.ewyre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Choose extends AppCompatActivity {
LinearLayout linearlayout;
TextView trainers,institutions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        linearlayout=findViewById(R.id.linearlayout);
        trainers=findViewById(R.id.trainers);
        institutions=findViewById(R.id.institutions);

        trainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        institutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Sessionmanagement sessionmanagement = new Sessionmanagement(this);
        int UserID = sessionmanagement.getSession();
        if (UserID != -1) {
            moveToMainActivity();
        } else {

        }

    }

    private void moveToMainActivity() {
        Intent intent = new Intent(Choose.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void Login(){
        Intent intent = new Intent(Choose.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}