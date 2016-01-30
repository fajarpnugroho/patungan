package com.chefcode.android.patungan.services.api;

import com.chefcode.android.patungan.services.response.BalancesInquiryResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface UserService {

    @GET("balanceInquiry")
    Call<BalancesInquiryResponse> balanceInquiery(@Query("msisdn") String msisdn,
                                @Query("credentials") String credentials,
                                @Query("token") String token);
}
