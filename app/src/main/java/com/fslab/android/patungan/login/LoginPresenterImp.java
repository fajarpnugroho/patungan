package com.fslab.android.patungan.login;

import android.content.Context;

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

    @Inject
    public LoginPresenterImp() {
        Injector.INSTANCE.getApplicationGraph().inject(this);
    }

    @Override
    public void loginMember() {
        getView().showLoading(true);

        String phoneNumber = getView().getPhoneNumber();
        String UID = getView().getUID();
        String credentials = getView().getCredentials();

        Call<LoginResponse> call = loginService.login(phoneNumber, UID, credentials);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                getView().showLoading(false);
                if (response.body() != null) {
                    if (isViewAttached()) {
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
