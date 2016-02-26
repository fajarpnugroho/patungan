package com.chefcode.android.patungan.ui.accounthistory;

import android.content.SharedPreferences;

import com.chefcode.android.patungan.delegate.AccountManager;
import com.chefcode.android.patungan.services.ServiceConfigs;
import com.chefcode.android.patungan.services.api.UserService;
import com.chefcode.android.patungan.services.response.HistoryResponse;
import com.chefcode.android.patungan.utils.Constants;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AccountHistoryPresenter {

    private static final String MAX_PAGE = "20";

    private AccountHistoryView view;
    private UserService userService;
    private SharedPreferences sharedPreferences;
    private AccountManager accountManager;

    public void initView(AccountHistoryView view) {
        this.view = view;
    }

    @Inject
    public AccountHistoryPresenter(UserService userService, SharedPreferences sharedPreferences,
                                   AccountManager accountManager) {
        this.userService = userService;
        this.sharedPreferences = sharedPreferences;
        this.accountManager = accountManager;
    }

    public void loadAccountHistory(String page) {
        view.loading(true);
        String token = sharedPreferences.getString(Constants.MANDIRI_TOKEN, "");
        String msisdn = sharedPreferences.getString(Constants.MSISDN, "");
        Call<HistoryResponse> call = userService.accountHistory(MAX_PAGE, token, msisdn, page);
        call.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Response<HistoryResponse> response, Retrofit retrofit) {
                view.loading(false);
                if (response.isSuccess()) {
                    HistoryResponse historyResponse = response.body();
                    if (historyResponse.status.equals(ServiceConfigs.RESPONSE_TOKEN_EXPIRED)) {
                        accountManager.forceLogout();
                        return;
                    }
                    view.showListHistory(historyResponse);
                } else {
                    view.HandleError();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.loading(false);
                view.HandleError();
            }
        });
    }
}
