package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.Context;
import android.content.SharedPreferences;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.delegate.AccountManager;
import com.chefcode.android.patungan.services.ServiceConfigs;
import com.chefcode.android.patungan.services.api.UserService;
import com.chefcode.android.patungan.services.response.BalancesInquiryResponse;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

public class HeaderViewPresenster {

    private HeaderView view;
    private Context context;
    private UserService service;
    private SharedPreferences sharedPreferences;

    @Inject
    AccountManager patunganAccount;

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
                            .equals(ServiceConfigs.RESPONSE_TOKEN_EXPIRED)
                            || balancesInquiryResponse.status.toLowerCase()
                                    .equals(ServiceConfigs.RESPONSE_ACCESS_VIOLATION)) {
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
        String encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
        Firebase userFirebase = new Firebase(Constants.FIREBASE_USER_URL).child(encodedEmail);

        HashMap<String, Object> updateBalanceInquery = new HashMap<>();
        updateBalanceInquery.put("/"
                + Constants.FIREBASE_ACCOUNT_BALANCE_PROPERTY, accountBalance);

        userFirebase.updateChildren(updateBalanceInquery, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Timber.e(firebaseError.getMessage());
                }
            }
        });
    }

}
