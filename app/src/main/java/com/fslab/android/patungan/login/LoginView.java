package com.fslab.android.patungan.login;

import com.fslab.android.patungan.BaseView;

public interface LoginView extends BaseView {
    String getPhoneNumber();

    String getUID();

    String getCredentials();

    void showLoading(boolean loading);

    void showErrorLogin(String message);

    void navigateToMain();
}
