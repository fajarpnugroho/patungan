package com.fslab.android.patungan.login;

import android.os.Bundle;

import com.fslab.android.patungan.BaseActivity;
import com.fslab.android.patungan.R;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenterImp> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public LoginPresenterImp createPresenter() {
        return new LoginPresenterImp();
    }
}

