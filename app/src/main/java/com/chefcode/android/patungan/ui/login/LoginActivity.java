package com.chefcode.android.patungan.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.list.MainActivity;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class LoginActivity extends BaseActivity implements DialogOnDismissListener {

    private Firebase.AuthStateListener authStateListener;
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            if (extra.containsKey(Constants.EXTRA_ERROR_MESSAGE)) {
                Toast.makeText(this,
                        extra.getString(Constants.EXTRA_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
            }
        }

        firebaseRef = new Firebase(Constants.FIREBASE_BASE_URL);
        authStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    navigateToMainActivity();
                }
            }
        };
    }

    public void onLoginClick(View view) {
        DialogLoginFragment dialogLoginFragment = DialogLoginFragment.newInstance();
        dialogLoginFragment.show(getSupportFragmentManager(), "ShowLoginDialog");
    }

    @Override
    protected void onPause() {
        firebaseRef.removeAuthStateListener(authStateListener);
        super.onPause();
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        firebaseRef.addAuthStateListener(authStateListener);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onSignupClick(View view) {
        DialogSignupNoticeFragment dialogSignupNoticeFragment =
                DialogSignupNoticeFragment.newInstance();
        dialogSignupNoticeFragment.show(getSupportFragmentManager(), "ShowSignupNoticeDialog");
    }
}
