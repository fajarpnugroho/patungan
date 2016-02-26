package com.chefcode.android.patungan;

import android.app.Application;
import android.os.Build;
import android.provider.Settings;

import com.firebase.client.Firebase;
import com.firebase.client.Logger;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;

import java.net.MalformedURLException;

import timber.log.Timber;

public class PatunganApp extends Application {

    private static final String APP_ROUTE = "http://Patungan.mybluemix.net";
    private static final String APP_GUID = "0fa70150-6494-4f74-b56e-070d6eb84dd5";

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

        // Initialize BMSClient
        try {
            BMSClient.getInstance().initialize(this, APP_ROUTE, APP_GUID);
        }
        catch (MalformedURLException ignored) {
        }

        // Initialize Push client
        MFPPush.getInstance().initialize(this);

        MFPPush push = MFPPush.getInstance();

        push.register(new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String s) {
                Timber.v("Successfully registered device for push notification");
            }

            @Override
            public void onFailure(MFPPushException e) {
                Timber.v("Failed registered device for push notification");
            }
        });
    }

}
