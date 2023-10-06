package com.example.ewyre.Others;

import com.example.ewyre.ViewModels.LoginVM;
import com.example.ewyre.ViewModels.OTPDetails;
import com.example.ewyre.ViewModels.UserDetails;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UserService {

//    @POST("/Registrations/PostLogin/")
//    public void postverifylogin(@Body UserDetails userDetails, Callback<LoginVM> callback);
//
//    @GET("/StateMasters/GetStateMaster")
//    public void postgetState(Callback<List<StateListVM>> callback);

    @POST("/InstitutionRegistration/PostSaveUserApp/")
    public void postsaveuser(@Body UserDetails userDetails, Callback<String> cb);



    @POST("/ResetPassord/PostResetPasswordMobile/")
    public void postsendotppasswordmobile(@Body UserDetails idd, Callback<String> cb);


    @POST("/ResetPassord/PostResetPasswordEmail/")
    public void postsendotppasswordemail(@Body UserDetails idd, Callback<String> cb);



    @POST("/OTPVerify/PostVerifyOtpMobile/")
    public void postverifyotpmobile(@Body OTPDetails otpDetails, Callback<String> cb);

    @POST("/OTPVerify/PostVerifyOtpEmail/")
    public void postverifyotpemail(@Body OTPDetails otpDetails, Callback<String> cb);

    @POST("/Password/PostSavenewpassword/")
    public void postsavepassword(@Body OTPDetails otpDetails, Callback<String> cb);


    @POST("/OTPVerify/PostSendOTPPhone/")
    public void postsendotpmobile(@Body OTPDetails idd, Callback<String> cb);


    @POST("/Login/PostLogin/")
    public void postverifylogin(@Body UserDetails userDetails, Callback<LoginVM> callback);



}
