package com.chefcode.android.patungan.delegate;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.chefcode.android.patungan.ui.login.LoginActivity;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;

import javax.inject.Inject;

public class AccountManager {
    private SharedPreferences preferences;
    private Application application;

    @Inject
    public AccountManager(SharedPreferences preferences, Application application) {
        this.preferences = preferences;
        this.application = application;
    }

    public void forceLogout() {
        preferences.edit().clear().apply();

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_BASE_URL);
        firebaseRef.unauth();

        Intent intent = new Intent(application.getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        application.startActivity(intent);
    }
}
