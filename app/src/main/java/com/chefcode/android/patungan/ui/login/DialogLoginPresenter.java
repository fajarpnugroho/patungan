package com.chefcode.android.patungan.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chefcode.android.patungan.services.api.LoginService;
import com.chefcode.android.patungan.services.response.LoginResponse;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DialogLoginPresenter {

    private LoginService service;
    private DialogLoginView view;
    private Context context;

    @Inject
    public DialogLoginPresenter(LoginService service, Context context) {
        this.service = service;
        this.context = context;
    }

    public void init(DialogLoginView view) {
        this.view = view;
    }

    public void loginMandiriEcash() {
        view.onLogin(true);
        view.showLoginContainer(false);

        if (isPhoneNumberEmpty()) {
            view.phoneNumberError();
            return;
        }

        if (isPasswordEmpty()) {
            view.passwordError();
            return;
        }


        Call<LoginResponse> call = service.login(view.getInputPhoneNumber(),
                "7", view.getInputPassword());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                view.onLogin(false);
                // Success response (2xx code)
                if (response.isSuccess()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.status.toLowerCase().equals("valid")) {

                        SharedPreferences preferences = PreferenceManager
                                .getDefaultSharedPreferences(context);

                        preferences.edit().putString("token", loginResponse.token).apply();
                        preferences.edit().putString("msisdn", loginResponse.msisdn).apply();


                    } else {
                        view.showLoginContainer(true);
                        view.showToastError(loginResponse.status);
                    }
                } else {
                // Error response (4xx code)
                    view.showLoginContainer(true);
                    view.showToastError("Network error");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.onLogin(false);
                // Network Error
            }
        });
    }

    public boolean isPhoneNumberEmpty() {
        return view.getInputPhoneNumber().isEmpty() || view.getInputPhoneNumber().length() < 0;
    }

    public boolean isPasswordEmpty() {
        return view.getInputPassword().isEmpty() || view.getInputPassword().length() < 0;
    }
}
