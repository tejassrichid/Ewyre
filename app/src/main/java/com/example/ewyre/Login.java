
package com.example.ewyre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    CardView logincard;
    TextView logintext;
    EditText edittextusername,edittextpassword;
    LinearLayout linearone,lineartwo,linearthree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logincard=findViewById(R.id.logincard);
        logintext=findViewById(R.id.logintext);
        edittextusername=findViewById(R.id.edittextusername);
        edittextpassword=findViewById(R.id.edittextpassword);
        linearone=findViewById(R.id.linearone);
        lineartwo=findViewById(R.id.lineartwo);
        linearthree=findViewById(R.id.linearthree);
    }
}