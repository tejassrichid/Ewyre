package com.example.ewyre;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewyre.Login;
import com.example.ewyre.Others.RestService;
import com.example.ewyre.R;
import com.example.ewyre.ViewModels.OTPDetails;
import com.example.ewyre.ViewModels.UserDetails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InstitutionRegistration extends AppCompatActivity {

    RestService restService;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private static final int PICK_FILE_REQUEST = 1;


    private int REQUEST_CAMERA = 0;


    String profile = null;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    byte[] fileCopy, b;
    Bitmap thumbnail = null;
    private Uri filePath;
    private Bitmap bitmap;
    private static final int CAMERA_PERMISSION_REQUEST = 3;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    TextView textView, resendclickphone;
    ImageView imageView;
    LinearLayout linearlayoutone, linearlayouttwo, linearlayoutthree, verifyphonelayout;
    AppCompatButton firstbutton, secondbutton, registerbutton, uploadimage, verifyphonebutton,otpverifybtnphone;
    EditText editfirstname, editmiddlename, editlastname, editphonenumber, editemail, editcity, editstate, editcountry;
    EditText editaddress, editpincode, editinstitutionname, editpassword, editconfirmpassword;

    AppCompatEditText otptextphone;
    String userChoosenTask, mId, firstpass, secondpass;
    Boolean imagecature = false;

    AppCompatButton verifyemailbutton;

    long lengthbmp;
    long finalsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_registration);

        mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        linearlayoutone = findViewById(R.id.linearlayoutone);
        linearlayouttwo = findViewById(R.id.linearlayouttwo);
        linearlayoutthree = findViewById(R.id.linearlayoutthree);
        firstbutton = findViewById(R.id.firstbutton);
        secondbutton = findViewById(R.id.secondbutton);
        registerbutton = findViewById(R.id.registerbutton);
        editfirstname = findViewById(R.id.editfirstname);
        editmiddlename = findViewById(R.id.editmiddlename);
        editlastname = findViewById(R.id.editlastname);
        editphonenumber = findViewById(R.id.editphonenumber);
        editemail = findViewById(R.id.editemail);
        editcity = findViewById(R.id.editcity);
        editstate = findViewById(R.id.editstate);
        editcountry = findViewById(R.id.editcountry);
        editaddress = findViewById(R.id.editaddress);
        editpincode = findViewById(R.id.editpincode);
        editinstitutionname = findViewById(R.id.editinstitutionname);
        uploadimage = findViewById(R.id.uploadimage);
        editpassword = findViewById(R.id.editpassword);
        editconfirmpassword = findViewById(R.id.editconfirmpassword);
        verifyphonebutton = findViewById(R.id.verifyphonebutton);
        verifyphonelayout = findViewById(R.id.verifyphonelayout);

        otptextphone = findViewById(R.id.otptextphone);
        resendclickphone = findViewById(R.id.resendclickphone);
        otpverifybtnphone=findViewById(R.id.otpverifybtnphone);
        verifyemailbutton=findViewById(R.id.verifyemailbutton);


        resendclickphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendOTPMobile();
            }
        });
        firstbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editfirstname.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "first name is Required", Toast.LENGTH_LONG).show();

                    editfirstname.setError("first name is required!");

                } else if (TextUtils.isEmpty(editmiddlename.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "middle name is Required", Toast.LENGTH_LONG).show();

                    editmiddlename.setError("middle name is required!");

                } else if (TextUtils.isEmpty(editlastname.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "last name is Required", Toast.LENGTH_LONG).show();

                    editlastname.setError("last name is required!");

                } else if (TextUtils.isEmpty(editphonenumber.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "phone number is Required", Toast.LENGTH_LONG).show();

                    editphonenumber.setError("phone number is required!");

                } else if (editphonenumber.getText().length() == 10) {
                    boolean res1 = isValidMobile(editphonenumber.getText().toString());


                    if (res1 == false) {
                        Toast.makeText(InstitutionRegistration.this, "enter valid phone number", Toast.LENGTH_LONG).show();

                        editphonenumber.setError("enter valid phone number");
                    } else if (res1 == true && !TextUtils.isEmpty(editfirstname.getText()) && !TextUtils.isEmpty(editmiddlename.getText()) && !TextUtils.isEmpty(editlastname.getText())) {
                        linearlayoutone.setVisibility(View.GONE);
                        linearlayouttwo.setVisibility(View.VISIBLE);
                    }
                } else if (editphonenumber.getText().length() != 10) {

                    Toast.makeText(InstitutionRegistration.this, "phone number must be 10 digit", Toast.LENGTH_LONG).show();

                    editphonenumber.setError("phone number must be 10 digit");

                } else {

                    linearlayoutone.setVisibility(View.GONE);
                    linearlayouttwo.setVisibility(View.VISIBLE);
                }
            }
        });

        otpverifybtnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyPhoneOTP();
            }
        });
        secondbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editcity.getText()))
                {
                    Toast.makeText(InstitutionRegistration.this, "city is Required", Toast.LENGTH_LONG).show();

                    editcity.setError("city is required!");

                }
                else if (TextUtils.isEmpty(editstate.getText()))
                {
                    Toast.makeText(InstitutionRegistration.this, "state is Required", Toast.LENGTH_LONG).show();

                    editstate.setError("state is required!");

                }
                else if (TextUtils.isEmpty(editcountry.getText()))
                {
                    Toast.makeText(InstitutionRegistration.this, "country is Required", Toast.LENGTH_LONG).show();

                    editcountry.setError("country is required!");

                }
                if (TextUtils.isEmpty(editemail.getText()))
                {
                    Toast.makeText(InstitutionRegistration.this, "email is Required", Toast.LENGTH_LONG).show();

                    editemail.setError("email is required!");

                }
                if (!editemail.getText().toString().contains("@"))
                {
                    Toast.makeText(InstitutionRegistration.this, "Enter Valid Email", Toast.LENGTH_LONG).show();

                    editemail.setError("Enter Valid Email");

                }
                else if (!TextUtils.isEmpty(editemail.getText()) && editemail.getText().toString().contains("@"))
                {

                    boolean ress = isValidMail(editemail.getText().toString());
                    if (ress == false)
                    {
                        Toast.makeText(InstitutionRegistration.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                    }
                    else if (ress == true && !TextUtils.isEmpty(editcity.getText()) && !TextUtils.isEmpty(editstate.getText()) && !TextUtils.isEmpty(editcountry.getText()))
                    {
                        linearlayouttwo.setVisibility(View.GONE);
                        linearlayoutthree.setVisibility(View.VISIBLE);
                    }

                }
                else
                {


                linearlayouttwo.setVisibility(View.GONE);
                linearlayoutthree.setVisibility(View.VISIBLE);
                 }
            }
        });
        editphonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check if the content length is 10
                if (s.length() == 10) {
                    verifyphonebutton.setVisibility(View.VISIBLE);
                } else {
                    verifyphonebutton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this example
            }
        });






        verifyphonebutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                SendOTPPhone();

//                verifyphonelayout.setVisibility(View.VISIBLE);
//                linearlayoutone.setVisibility(View.GONE);
//                imageView.setVisibility(View.GONE);
//                textView.setVisibility(View.GONE);
            }
        });
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImage();
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstpass = editpassword.getText().toString();
                secondpass = editconfirmpassword.getText().toString();

                if (TextUtils.isEmpty(editaddress.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "address is Required", Toast.LENGTH_LONG).show();

                    editaddress.setError("address is required!");

                } else if (TextUtils.isEmpty(editpincode.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "pincode is Required", Toast.LENGTH_LONG).show();

                    editpincode.setError("pincode is required!");

                } else if (TextUtils.isEmpty(editinstitutionname.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "institution name is Required", Toast.LENGTH_LONG).show();

                    editinstitutionname.setError("institution name is required!");

                } else if (TextUtils.isEmpty(editpassword.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "password is Required", Toast.LENGTH_LONG).show();

                    editinstitutionname.setError("password is required!");

                } else if (TextUtils.isEmpty(editconfirmpassword.getText())) {
                    Toast.makeText(InstitutionRegistration.this, "confirm password is Required", Toast.LENGTH_LONG).show();

                    editinstitutionname.setError("confirm password is required!");

                } else if (!firstpass.equals(secondpass)) {
                    Toast.makeText(InstitutionRegistration.this, "Both Password must be same", Toast.LENGTH_SHORT).show();
                } else if (fileCopy == null) {
                    Toast.makeText(InstitutionRegistration.this, "Please Upload Institution Logo", Toast.LENGTH_LONG).show();


                } else {

                    Register();
                }
            }
        });

    }

    private boolean isValidMail(String email) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        if (android.util.Patterns.PHONE.matcher(phone).matches()) {
            return true;
        }
        return false;
    }

    public void onSelectImage() {
        final CharSequence[] items = {"Choose from Library", "Take Photo",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }

    public void selectImageFromGalleryOrCapture(View view) {


        PackageManager packageManager = getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Device doesn't have a camera
            Toast.makeText(this, "This device does not have a camera.", Toast.LENGTH_SHORT).show();
            return;
        }


        // Check if the app has camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else {
            // Camera permission is granted, proceed with the camera capture
            launchCameraCapture();
        }


        // Create an Intent to choose between gallery and camera
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        // Show a chooser dialog to select either gallery or camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if there is a camera app available on the device
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Intent chooser = Intent.createChooser(intent, "Select Image");
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
            startActivityForResult(chooser, REQUEST_IMAGE_PICK);
        } else {
            Toast.makeText(this, "No camera app available.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }
                Uri selectedFileUri = data.getData();
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        fileCopy = null;
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        Bitmap.createScaledBitmap(thumbnail, 512, 512, true);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bte = bytes.toByteArray();
        lengthbmp = bte.length / 1024;
        finalsize = Math.round(lengthbmp);
        if (finalsize <= 350) {
            fileCopy = bte;
            if (fileCopy != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(fileCopy, 0, fileCopy.length);
                imagecature = true;

            }

        } else {
            Toast.makeText(InstitutionRegistration.this, "ImageSize must be less than 250KB ", Toast.LENGTH_LONG).show();
            if (checkAndRequestPermissions(InstitutionRegistration.this)) {
                fileCopy = null;
                profile = null;
                thumbnail = null;
                filePath = null;
                bitmap = null;
                bytes = null;

                bytes = new ByteArrayOutputStream();
                onSelectImage();
            }
        }
        //camerabytes = bytes.toByteArray();
        //System.out.println(camerabytes);
        //cameraprofile = Base64.encodeToString(camerabytes, Base64.DEFAULT);
    }

    public void Register() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        UserDetails registerdata = new UserDetails();

        registerdata.FirstName = editfirstname.getText().toString();
        registerdata.MiddleName = editmiddlename.getText().toString();
        registerdata.LastName = editlastname.getText().toString();
        registerdata.PhoneNumber = editphonenumber.getText().toString();
        registerdata.Email = editemail.getText().toString();
        registerdata.City = editcity.getText().toString();
        registerdata.State = editstate.getText().toString();
        registerdata.Country = editcountry.getText().toString();
        registerdata.Address = editaddress.getText().toString();
        registerdata.Pincode = Integer.getInteger(editpincode.getText().toString());
        registerdata.InstitutionName = editinstitutionname.getText().toString();
        registerdata.Imagebyte = fileCopy;
        registerdata.Image = ".jpg";
        registerdata.DeviceId = mId;
        registerdata.Password = firstpass;
        registerdata.ConfirmPassword = secondpass;


        restService = new RestService();
        restService.getService().postsaveuser(registerdata, new Callback<String>() {


            @Override
            public void success(String responsee, Response response) {
                String result = responsee;
                if (result.equals("0")) {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "registered successfully", Toast.LENGTH_LONG).show();
//                    linearfive.setVisibility(View.VISIBLE);
//                    verifymaillayout.setVisibility(View.INVISIBLE);
//                    lineartwo.setVisibility(View.INVISIBLE);
//                    lnumverified.setVisibility(View.INVISIBLE);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else if (!result.equals("0")) {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "registeration unsuccessfull", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(InstitutionRegistration.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InstitutionRegistration.this, Login.class);
                startActivity(intent);
            }
        });
//        Intent intent = new Intent(InstitutionRegistration.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);


    }


    private void onSelectFromGalleryResult(Intent data) {
        String fname = "";
        int imagewidtth = 0;
        int imageheight = 0;
        if (data != null) {
            try {
                filePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Bitmap.createScaledBitmap(thumbnail, 512, 512, true);

                imagewidtth = thumbnail.getWidth();
                imageheight = thumbnail.getHeight();
                byte[] imageInByte = bytes.toByteArray();
                lengthbmp = imageInByte.length / 1024;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finalsize = Math.round(lengthbmp);
        if (finalsize <= 350) {
            byte[] b = bytes.toByteArray();
            profile = Base64.encodeToString(b, Base64.DEFAULT);
            fileCopy = b;

            fileCopy = b;
            if (fileCopy != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(fileCopy, 0, fileCopy.length);
                imagecature = true;


            }
        } else {
            Toast.makeText(InstitutionRegistration.this, "ImageSize must be less than 250KB ", Toast.LENGTH_LONG).show();
            if (checkAndRequestPermissions(InstitutionRegistration.this)) {
                fileCopy = null;
                profile = null;
                thumbnail = null;
                filePath = null;
                bitmap = null;
                bytes = new ByteArrayOutputStream();
                imageheight = 0;
                imagewidtth = 0;
                b = null;
                onSelectImage();
            }
        }


    }


    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void launchCameraCapture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void SendOTPPhone() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        OTPDetails reg = new OTPDetails();
        reg.UserName = editfirstname.getText().toString();


        reg.DeviceId = mId;
        reg.PhoneNumber = editphonenumber.getText().toString();
        restService = new RestService();
        restService.getService().postsendotpmobile(reg, new Callback<String>() {
            @Override
            public void success(String responsee, Response response) {
                String result = responsee;
                if (result.equals("0")) {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "OTP sent successfully", Toast.LENGTH_LONG).show();

                    verifyphonelayout.setVisibility(View.VISIBLE);
                    linearlayoutone.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);

                } else if (result.equals("1")) {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "Mobile Number already exist!!", Toast.LENGTH_LONG).show();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(InstitutionRegistration.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InstitutionRegistration.this, Login.class);
                startActivity(intent);
            }
        });
    }
    private void ResendOTPMobile(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        OTPDetails reg = new OTPDetails();
        reg.UserName = editfirstname.getText().toString();


        reg.DeviceId = mId;
        reg.PhoneNumber = editphonenumber.getText().toString();
        restService = new RestService();
        restService.getService().postsendotpmobile(reg, new Callback<String>() {
            @Override
            public void success(String responsee, Response response) {
                String result = responsee;
                if (result.equals("0")) {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "OTP sent successfully", Toast.LENGTH_LONG).show();

                    verifyphonelayout.setVisibility(View.VISIBLE);
                    linearlayoutone.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);

                } else if (result.equals("1")) {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "Mobile Number already exist!!", Toast.LENGTH_LONG).show();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(InstitutionRegistration.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(InstitutionRegistration.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InstitutionRegistration.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void VerifyPhoneOTP(){

        String mobileotp=otptextphone.getText().toString();
        if (TextUtils.isEmpty(mobileotp)) {
            Toast.makeText(InstitutionRegistration.this, "Enter OTP", Toast.LENGTH_SHORT).show();
        } else if (mobileotp.length() < 6 && mobileotp.length() > 6) {
            Toast.makeText(InstitutionRegistration.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            OTPDetails det = new OTPDetails();
            det.Otp = mobileotp;
            det.PhoneNumber = editphonenumber.getText().toString();

            restService = new RestService();

            restService.getService().postverifyotpmobile(det, new Callback<String>() {


                @Override
                public void success(String responsee, Response response) {
                    String result = responsee;

                    if (result.equals("0")) {
                        progressDialog.dismiss();
                        linearlayoutone.setVisibility(View.VISIBLE);
                        firstbutton.setVisibility(View.VISIBLE);
                        verifyphonebutton.setVisibility(View.GONE);

                        verifyphonelayout.setVisibility(View.GONE);

                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);

                        Toast.makeText(InstitutionRegistration.this, " OTP Verified ", Toast.LENGTH_LONG).show();
                    } else if (result.equals("1")) {
                        progressDialog.dismiss();
                        Toast.makeText(InstitutionRegistration.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                    } else if (result.equals("2")) {
                        progressDialog.dismiss();
                        Toast.makeText(InstitutionRegistration.this, "OTP Expired", Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(InstitutionRegistration.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(InstitutionRegistration.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(InstitutionRegistration.this, Login.class);
                    startActivity(intent);
                }


            });

        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
//            // Image selected from gallery
//            Uri selectedImageUri = data.getData();
//            // Handle the selected image, e.g., display it in an ImageView
//        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
//            // Image captured from camera
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            // Handle the captured image, e.g., display it in an ImageView
//        }
//    }

}
