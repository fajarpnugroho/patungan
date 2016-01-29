package com.chefcode.android.patungan;

import android.app.Application;
import android.os.Build;

import timber.log.Timber;

public class PatunganApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.INSTANCE.initializeApplicationGraph(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
