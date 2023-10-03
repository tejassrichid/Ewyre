package com.example.ewyre;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.example.ewyre.ViewModels.UserDetails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    TextView textView;
    ImageView imageView;
    LinearLayout linearlayoutone,linearlayouttwo,linearlayoutthree;
    AppCompatButton firstbutton,secondbutton,registerbutton,uploadimage;
    EditText editfirstname,editmiddlename,editlastname,editphonenumber,editemail,editcity,editstate,editcountry;
    EditText editaddress,editpincode,editinstitutionname;


    String userChoosenTask,mId;
    Boolean imagecature = false;

    long lengthbmp;
    long finalsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_registration);

        mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.imageView);
        linearlayoutone=findViewById(R.id.linearlayoutone);
        linearlayouttwo=findViewById(R.id.linearlayouttwo);
        linearlayoutthree=findViewById(R.id.linearlayoutthree);
        firstbutton=findViewById(R.id.firstbutton);
        secondbutton=findViewById(R.id.secondbutton);
        registerbutton=findViewById(R.id.registerbutton);
        editfirstname=findViewById(R.id.editfirstname);
        editmiddlename=findViewById(R.id.editmiddlename);
        editlastname=findViewById(R.id.editlastname);
        editphonenumber=findViewById(R.id.editphonenumber);
        editemail=findViewById(R.id.editemail);
        editcity=findViewById(R.id.editcity);
        editstate=findViewById(R.id.editstate);
        editcountry=findViewById(R.id.editcountry);
        editaddress=findViewById(R.id.editaddress);
        editpincode=findViewById(R.id.editpincode);
        editinstitutionname=findViewById(R.id.editinstitutionname);
        uploadimage=findViewById(R.id.uploadimage);

        firstbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearlayoutone.setVisibility(View.GONE);
                linearlayouttwo.setVisibility(View.VISIBLE);
            }
        });
        secondbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearlayouttwo.setVisibility(View.GONE);
                linearlayoutthree.setVisibility(View.VISIBLE);
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
                Register();
            }
        });

    }

    public void onSelectImage(){
        final CharSequence[] items = {"Choose from Library","Take Photo",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                }
                else if (items[item].equals("Take Photo")) {
                    cameraIntent();
                }
                else if (items[item].equals("Cancel")) {
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

    public void Register(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        UserDetails registerdata=new UserDetails();

        registerdata.FirstName=editfirstname.getText().toString();
        registerdata.MiddleName=editmiddlename.getText().toString();
        registerdata.LastName=editlastname.getText().toString();
        registerdata.PhoneNumber=Integer.getInteger(editphonenumber.getText().toString());
        registerdata.Email=editemail.getText().toString();
        registerdata.City=editcity.getText().toString();
        registerdata.State=editstate.getText().toString();
        registerdata.Country=editcountry.getText().toString();
        registerdata.Address=editaddress.getText().toString();
        registerdata.Pincode=Integer.getInteger(editpincode.getText().toString());
        registerdata.InstitutionName=editinstitutionname.getText().toString();
       // registerdata.Imagebyte=fileCopy;
        registerdata.Image="Image Name";
        registerdata.DeviceId=mId;


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

                    Intent intent = new Intent(getApplicationContext(), Login.class);
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
