package com.chefcode.android.patungan.services.api;

import com.chefcode.android.patungan.services.response.LoginResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LoginService {
    @GET("loginMember")
    Call<LoginResponse> login(@Query("msisdn") String phoneNumber,
                              @Query("uid") String uid,
                              @Query("credentials") String userCredentials);
}
