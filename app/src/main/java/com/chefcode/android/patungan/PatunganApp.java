package com.chefcode.android.patungan;

import android.app.Application;
import android.os.Build;
import android.provider.Settings;

import com.firebase.client.Firebase;
import com.firebase.client.Logger;

import timber.log.Timber;

public class PatunganApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initalize Dagger Injection
        Injector.INSTANCE.initializeApplicationGraph(this);

        // Initalize Timber Log
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Inialitze Firebase
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
