package com.chefcode.android.patungan.services.api;

import com.chefcode.android.patungan.services.response.BooleanResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LogoutService {
    @GET("logoutMember")
    Call<BooleanResponse> logoutMember(@Query("msisdn") String phoneNumber,
                                       @Query("token") String token);
}
