package com.chefcode.android.patungan.ui.login;

import android.app.Application;

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

    @Inject
    public DialogLoginPresenter(LoginService service) {
        this.service = service;
    }

    public void init(DialogLoginView view) {
        this.view = view;
    }

    public void loginMandiriEcash() {
        if (view.isPhoneNumberEmpty()) {
            view.phoneNumberError();
            return;
        }

        if (view.isPasswordEmpty()) {
            view.passwordError();
            return;
        }


        Call<LoginResponse> call = service.login(view.getInputPhoneNumber(),
                "7", view.getInputPhoneNumber());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                // Success response (2xx code)
                if (response.isSuccess()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.status.toLowerCase().equals("success")) {

                    } else {

                    }
                } else {
                // Error response (4xx code)
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Network Error
            }
        });
    }
}
