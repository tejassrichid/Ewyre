package com.example.ewyre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewyre.Others.RestService;
import com.example.ewyre.ViewModels.OTPDetails;
import com.example.ewyre.ViewModels.UserDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForgotPassword extends AppCompatActivity {


    RestService restService;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    RelativeLayout relativeLayout2;

    ImageButton back;

    TextView logoo, resendtext, resendclick;

    LinearLayout linearone, otplayout, resetpasswordlayout, requestlayout;
    AppCompatEditText otptext;

    EditText password, confirmpassword;

    String otpinfo;
    AppCompatButton resetpasswordbtn, requestbtn, otpverifybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputEditText = findViewById(R.id.textInputEditText);

        relativeLayout2 = findViewById(R.id.relativeLayout2);
        back = findViewById(R.id.back);
        logoo = findViewById(R.id.logoo);
        resendtext = findViewById(R.id.resendtext);
        resendclick = findViewById(R.id.resendclick);
        linearone = findViewById(R.id.linearone);
        otplayout = findViewById(R.id.otplayout);
        resetpasswordlayout = findViewById(R.id.resetpasswordlayout);
        requestlayout = findViewById(R.id.requestlayout);
        otptext = findViewById(R.id.otptext);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        resetpasswordbtn = findViewById(R.id.resetpasswordbtn);
        requestbtn = findViewById(R.id.requestbtn);
        otpverifybtn = findViewById(R.id.otpverifybtn);
        textInputLayout.setHintAnimationEnabled(true);

        // Set the hint for the TextInputEditText
        textInputLayout.setHint("Enter email to reset password");

        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOTP();
            }
        });
        otpverifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPassword();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBack();
            }
        });
        resetpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePassword();
            }
        });


    }

    private void SendOTP() {


        boolean type = false;
        otpinfo = textInputEditText.getText().toString();
        if (otpinfo.length() != 10 && !otpinfo.contains("@")) {
            Toast.makeText(ForgotPassword.this, "You are not doing this in the right order!", Toast.LENGTH_LONG).show();
        } else if (otpinfo.length() == 10 && !otpinfo.contains("@")) {
            boolean res1 = isValidMobile(otpinfo);
            if (res1 == true) {

                sendOTPMobile(otpinfo);

            } else if (res1 == false) {
                Toast.makeText(ForgotPassword.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            }


            type = res1;
        } else if (otpinfo.length() != 10 && otpinfo.contains("@")) {
            boolean res = isValidMail(otpinfo);
            if (res == true) {

                sendOTPMail(otpinfo);

            } else if (res == false) {
                Toast.makeText(ForgotPassword.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }
            type = res;
        }


//        requestlayout.setVisibility(View.GONE);
//        otplayout.setVisibility(View.VISIBLE);
//        linearone.setVisibility(View.GONE);
    }

    private void SetPassword() {

String otptextt=otptext.getText().toString();
        if (TextUtils.isEmpty(otptextt)) {
            Toast.makeText(ForgotPassword.this, "Enter OTP", Toast.LENGTH_SHORT).show();
        } else if (otptextt.length() < 6 && otptextt.length() > 6) {
            Toast.makeText(ForgotPassword.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
        } else {


            if (otptext.toString().contains("@")) {
                VerifyMailOTP(otptextt,otpinfo);
            } else {
                VerifyMobileoOTP(otptextt, otpinfo);
            }

        }




//        otplayout.setVisibility(View.GONE);
//        resetpasswordlayout.setVisibility(View.VISIBLE);

    }


    private boolean isValidMobile(String phone) {
        if (android.util.Patterns.PHONE.matcher(phone).matches()) {
            return true;
        }
        return false;
    }


    private boolean isValidMail(String email) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }

    private void GoBack() {
        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void sendOTPMobile(String num) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        UserDetails reg = new UserDetails();


        reg.PhoneNumber = num;


        restService = new RestService();
        restService.getService().postsendotppasswordmobile(reg, new Callback<String>() {


            @Override
            public void success(String responsee, Response response) {
                String result = responsee;
                if (result.equals("0")) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "OTP sent successfully", Toast.LENGTH_LONG).show();
                    requestlayout.setVisibility(View.GONE);
                    otplayout.setVisibility(View.VISIBLE);
                    linearone.setVisibility(View.GONE);

                } else if (result.equals("1")) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Not Found Account with this Number", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ForgotPassword.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
            }
        });


    }


    private void SavePassword(){
        String firstpass = password.getText().toString();
        String secondpass = confirmpassword.getText().toString();
        if (!firstpass.equals(secondpass)) {
            Toast.makeText(ForgotPassword.this, "Both Password must be same", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(ForgotPassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
        } else if (confirmpassword.length() < 6 && otptext.length() > 6) {
            Toast.makeText(ForgotPassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            OTPDetails det = new OTPDetails();
            det.PhoneNumber = otpinfo;
            det.Email=otpinfo;
            det.Password = firstpass;
            det.ConfirmPassword = secondpass;

            restService = new RestService();

            restService.getService().postsavepassword(det, new Callback<String>() {


                @Override
                public void success(String responsee, Response response) {
                    String result = responsee;

                    if (result.equals("0")) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "New Password Saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (result.equals("1")) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "Cannot Save Password", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(ForgotPassword.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    startActivity(intent);
                }


            });
        }
    }


    public void sendOTPMail(String mail) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        UserDetails reg = new UserDetails();


        reg.Email = mail;


        restService = new RestService();
        restService.getService().postsendotppasswordemail(reg, new Callback<String>() {


            @Override
            public void success(String responsee, Response response) {
                String result = responsee;
                if (result.equals("0")) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "OTP sent successfully", Toast.LENGTH_LONG).show();
                    requestlayout.setVisibility(View.GONE);
                    otplayout.setVisibility(View.VISIBLE);
                    linearone.setVisibility(View.GONE);

                } else if (result.equals("1")) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Not Found Account with this Email", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ForgotPassword.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
            }
        });
    }


    private void VerifyMobileoOTP(String otp,String mobile){




        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        OTPDetails det = new OTPDetails();
        det.Otp = otp;
        det.PhoneNumber = mobile;

        restService = new RestService();

        restService.getService().postverifyotpmobile(det, new Callback<String>() {


            @Override
            public void success(String responsee, Response response) {
                String result = responsee;

                if (result.equals("0")) {
                    progressDialog.dismiss();
                    otplayout.setVisibility(View.GONE);
                    resetpasswordlayout.setVisibility(View.VISIBLE);

                    Toast.makeText(ForgotPassword.this, " OTP Verified ", Toast.LENGTH_LONG).show();
                } else if (result.equals("1")) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                } else if (result.equals("2")) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "OTP Expired", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ForgotPassword.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
            }


        });




}


private void VerifyMailOTP(String otp,String email){
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Please Wait...");
    progressDialog.show();
    progressDialog.setCancelable(false);
    OTPDetails det = new OTPDetails();
    det.Otp = otp;
    det.Email = email;

    restService = new RestService();

    restService.getService().postverifyotpemail(det, new Callback<String>() {


        @Override
        public void success(String responsee, Response response) {
            String result = responsee;

            if (result.equals("0")) {
                progressDialog.dismiss();
                otplayout.setVisibility(View.GONE);
                resetpasswordlayout.setVisibility(View.VISIBLE);

                Toast.makeText(ForgotPassword.this, " OTP Verified ", Toast.LENGTH_LONG).show();
            } else if (result.equals("1")) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "Invalid OTP", Toast.LENGTH_LONG).show();
            } else if (result.equals("2")) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "OTP Expired", Toast.LENGTH_LONG).show();
            } else {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(ForgotPassword.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ForgotPassword.this, Login.class);
            startActivity(intent);
        }


    });

}

}