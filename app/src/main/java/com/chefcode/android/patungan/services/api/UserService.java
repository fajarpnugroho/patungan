package com.chefcode.android.patungan.services.api;

import com.chefcode.android.patungan.services.response.BalancesInquiryResponse;
import com.chefcode.android.patungan.services.response.HistoryResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface UserService {

    @GET("balanceInquiry")
    Call<BalancesInquiryResponse> balanceInquiery(@Query("msisdn") String msisdn,
                                @Query("credentials") String credentials,
                                @Query("token") String token);

    @GET("accountHistory")
    Call<HistoryResponse> accountHistory(@Query("pagesize") String pageSize,
                                         @Query("token") String token,
                                         @Query("msisdn") String msisdn,
                                         @Query("onpage") String onPage);
}
