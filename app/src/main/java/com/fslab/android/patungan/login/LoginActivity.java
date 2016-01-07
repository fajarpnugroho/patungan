package com.fslab.android.patungan.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fslab.android.patungan.BaseActivity;
import com.fslab.android.patungan.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    @Bind(R.id.phone) EditText phoneEditText;
    @Bind(R.id.password) EditText passwordEditText;
    @Bind(R.id.login_progress) ProgressBar loginProgress;

    @Override
    public LoginPresenter createPresenter() {
        if (presenter == null) {
            presenter = new LoginPresenterImp();
        }
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.phone_sign_in_button)
    void onButtonSignInClick() {
        presenter.loginMember();
    }

    @Override
    public String getPhoneNumber() {
        return phoneEditText.getText().toString();
    }

    @Override
    public String getUID() {
        return null;
    }

    @Override
    public String getCredentials() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void showLoading(boolean loading) {
        if (loading) {
            loginProgress.setVisibility(View.VISIBLE);
        } else {
            loginProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorLogin(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMain() {

    }

    @Override
    public void showPhoneNumberFieldError(String message) {
        phoneEditText.setError(message);
    }

    @Override
    public void showCredentialsFieldError(String message) {
        passwordEditText.setError(message);
    }
}

