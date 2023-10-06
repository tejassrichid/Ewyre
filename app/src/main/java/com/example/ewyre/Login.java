package com.example.ewyre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewyre.Others.RestService;
import com.example.ewyre.ViewModels.LoginVM;
import com.example.ewyre.ViewModels.UserDetails;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login extends AppCompatActivity {

    RestService restService;
    CardView logincard;
    TextView logintext,registertest,forgotpassword;

AppCompatButton signinbutton;
    EditText edittextusername,edittextpassword;
    LinearLayout linearone,lineartwo,linearthree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logincard=findViewById(R.id.logincard);
        logintext=findViewById(R.id.logintext);
        edittextusername=findViewById(R.id.editfirstname);
        edittextpassword=findViewById(R.id.edittextpassword);
        signinbutton=findViewById(R.id.signinbutton);
        linearone=findViewById(R.id.linearone);
        lineartwo=findViewById(R.id.lineartwo);
        linearthree=findViewById(R.id.linearthree);
        registertest=findViewById(R.id.registertest);
        forgotpassword=findViewById(R.id.forgotpassword);
        registertest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Welcome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Forgot Password Clicked !!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Login.this, "Sign In is Clicked Navigating to main page", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Login.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);


                if (TextUtils.isEmpty(edittextusername.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edittextpassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    final UserDetails reg = new UserDetails();

                    reg.Password = edittextpassword.getText().toString();
                    reg.PhoneNumber = edittextusername.getText().toString();


                    restService = new RestService();
                    restService.getService().postverifylogin(reg, new Callback<LoginVM>() {
                        //            Call<LoginVM> call1= restService.getService().postverifylogin(reg);
//            call1.enqueue(new Callback<LoginVM>() {
                        @Override
                        public void success(LoginVM loginVM, Response response) {
                            try {
                                if (loginVM.uid == null) {


                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                                } else if (loginVM.uid != null) {
                                    progressDialog.dismiss();

                                    moveToMainActivity();



                                }  else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Something wrong. Try again", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                throw e;
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                    });
                }





            }
        });
    }
    public   void moveToMainActivity(){
        Toast.makeText(Login.this, "Logged In Navigated to main page", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finishAffinity();
        startActivity(intent);
    }
}