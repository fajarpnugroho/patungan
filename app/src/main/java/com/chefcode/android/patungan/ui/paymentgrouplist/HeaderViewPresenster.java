package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.Context;
import android.content.SharedPreferences;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.delegate.PatunganAccount;
import com.chefcode.android.patungan.services.ServiceConfigs;
import com.chefcode.android.patungan.services.api.UserService;
import com.chefcode.android.patungan.services.response.BalancesInquiryResponse;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HeaderViewPresenster {

    private HeaderView view;
    private Context context;
    private UserService service;
    private SharedPreferences sharedPreferences;

    @Inject PatunganAccount patunganAccount;

    @Inject
    public HeaderViewPresenster(Context context, UserService service,
                                SharedPreferences sharedPreferences) {

        this.context = context;
        this.service = service;
        this.sharedPreferences = sharedPreferences;
    }

    public void init(HeaderView view) {
        this.view = view;
    }

    public void setAccountName() {
        view.showAccountName(sharedPreferences.getString(Constants.MSISDN, ""));
    }

    public void getBalanceInquiryUser() {

        String msisdn = sharedPreferences.getString(Constants.MSISDN, null);
        String token = sharedPreferences.getString(Constants.MANDIRI_TOKEN, null);

        if (msisdn == null && token == null) {
            patunganAccount.forceLogout();
            return;
        }

        view.showBalanceInquiryUser(sharedPreferences.getString(Constants.ACCOUNT_BALANCE, "-"));

        view.onLoading(true);

        Call<BalancesInquiryResponse> call = service.balanceInquiery(msisdn,
                Constants.CREDENTIALS, token);

        call.enqueue(new Callback<BalancesInquiryResponse>() {
            @Override
            public void onResponse(Response<BalancesInquiryResponse> response, Retrofit retrofit) {
                view.onLoading(false);

                if (response.isSuccess()) {

                    BalancesInquiryResponse balancesInquiryResponse = response.body();

                    if (balancesInquiryResponse.status.toLowerCase()
                            .equals(ServiceConfigs.RESPONSE_TOKEN_EXPIRED)) {
                        patunganAccount.forceLogout();
                        return;
                    }

                    String accountBalance = balancesInquiryResponse.accountBalance;
                    view.showBalanceInquiryUser(accountBalance);

                    sharedPreferences.edit().putString(Constants.ACCOUNT_BALANCE,
                            accountBalance).apply();

                    updateBalanceWithFirebaseHelper(accountBalance);

                } else {

                    view.showErrorMessage("Empty");
                }
            }

            @Override
            public void onFailure(Throwable t) {

                view.onLoading(false);
                view.showErrorMessage(context
                        .getString(R.string.error_message_failed_sign_in_no_network));
            }
        });
    }

    private void updateBalanceWithFirebaseHelper(String accountBalance) {

        if (!accountBalance.equals(sharedPreferences.getString(Constants.ACCOUNT_BALANCE, "-"))) {

            Firebase userFirebase = new Firebase(Constants.FIREBASE_BASE_URL);

            HashMap<String, Object> updateBalanceInquery = new HashMap<>();
            updateBalanceInquery.put("/"
                    + Constants.FIREBASE_USER_LOCATION + "/"
                    + sharedPreferences.getString(Constants.ENCODED_EMAIL, "") + "/"
                    + Constants.FIREBASE_ACCOUNT_BALANCE_PROPERTY, accountBalance);

            userFirebase.updateChildren(updateBalanceInquery);
        }
    }

}
