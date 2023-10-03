package com.example.ewyre.Others;

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

    @POST("/InstitutionRegistration/PostSaveUser/")
    public void postsaveuser(@Body UserDetails userDetails, Callback<String> cb);

}
