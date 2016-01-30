package com.chefcode.android.patungan;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    public Context provideContext() {
        return  application;
    }

    @Provides
    SharedPreferences provideSharePreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
