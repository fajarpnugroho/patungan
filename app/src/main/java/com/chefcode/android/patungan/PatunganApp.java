package com.chefcode.android.patungan;

import android.app.Application;

public class PatunganApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.INSTANCE.initializeApplicationGraph(this);
    }
}
