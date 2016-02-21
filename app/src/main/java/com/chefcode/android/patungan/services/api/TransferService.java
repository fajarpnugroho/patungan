package com.chefcode.android.patungan.services.api;

import com.chefcode.android.patungan.services.response.TransferResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface TransferService {
    @GET("transferMember")
    Call<TransferResponse> transferMember(@Query("amount") Integer amount,
                        @Query("to") String to,
                        @Query("token") String token,
                        @Query("description") String description,
                        @Query("credentials") String credentials,
                        @Query("from") String from);
}
