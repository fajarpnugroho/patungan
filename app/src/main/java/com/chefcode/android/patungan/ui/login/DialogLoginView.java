package com.chefcode.android.patungan.ui.login;

public interface DialogLoginView {
    String getInputPhoneNumber();

    String getInputPassword();

    void phoneNumberError();

    void passwordError();

    void onLogin(boolean login);

    void dismissDialogLogin();

    void showLoginContainer(boolean show);

    void showToastError(String message);
}
