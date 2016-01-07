package com.fslab.android.patungan.login;

import android.text.TextUtils;

import com.fslab.android.patungan.BasePresenterImpl;
import com.fslab.android.patungan.Injector;
import com.fslab.android.patungan.services.api.LoginService;
import com.fslab.android.patungan.services.response.LoginResponse;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

public class LoginPresenterImp extends BasePresenterImpl<LoginView> implements LoginPresenter{

    @Inject LoginService loginService;

    public LoginPresenterImp() {
        Injector.INSTANCE.getApplicationGraph().inject(this);
    }

    @Override
    public void loginMember() {

        if (getView() == null) {
            throw new NullPointerException("View is null");
        }

        String phoneNumber = getView().getPhoneNumber();
        String UID = getView().getUID();
        String credentials = getView().getCredentials();

        if (TextUtils.isEmpty(phoneNumber)) {
            getView().showPhoneNumberFieldError("Please provide your phone number");
            return;
        }

        if (TextUtils.isEmpty(credentials)) {
            getView().showCredentialsFieldError("Please provide your password");
            return;
        }

        getView().showLoading(true);
        Call<LoginResponse> call = loginService.login(phoneNumber, UID, credentials);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                getView().showLoading(false);
                if (response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.status.equals(LoginResponse.STATUS_LOGIN_FAILED)) {
                        getView().showErrorLogin("Invalid phone number or password.");
                    } else {
                        getView().navigateToMain();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                getView().showLoading(false);
                getView().showErrorLogin(t.getMessage());
            }
        });
    }

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        Timber.i("View is attached " + view.toString());
    }

    @Override
    public void detachView() {
        super.detachView();
        Timber.i("View is detached");
    }
}
