package com.chefcode.android.patungan.ui;

import android.content.Intent;
import android.os.Bundle;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.ui.login.LoginActivity;
import com.chefcode.android.patungan.ui.list.MainActivity;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class EntranceActivity extends BaseActivity {

    private Firebase firebaseRef;
    private Firebase.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseRef = new Firebase(Constants.FIREBASE_BASE_URL);

        authStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    Intent intent = new Intent(EntranceActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EntranceActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseRef.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseRef.removeAuthStateListener(authStateListener);
    }
}
