package com.chefcode.android.patungan;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return  application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharePreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
