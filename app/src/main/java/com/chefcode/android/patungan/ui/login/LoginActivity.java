package com.chefcode.android.patungan.ui.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.R;

public class LoginActivity extends BaseActivity implements DialogOnDismissListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClick(View view) {
        DialogLoginFragment dialogLoginFragment = DialogLoginFragment.newInstance();
        dialogLoginFragment.show(getSupportFragmentManager(), "ShowLoginDialog");
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {

    }
}
