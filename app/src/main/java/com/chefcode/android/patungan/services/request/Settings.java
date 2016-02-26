package com.chefcode.android.patungan.services.request;

public class Settings {
    public final String apns;
    public final String gcm;

    public Settings(String apns, String gcm) {
        this.apns = apns;
        this.gcm = gcm;
    }
}
