package com.chefcode.android.patungan.ui.login;

public interface DialogLoginView {
    String getInputPhoneNumber();

    String getInputPassword();
    
    boolean isPhoneNumberEmpty();

    boolean isPasswordEmpty();

    void phoneNumberError();

    void passwordError();
}
