package com.chefcode.android.patungan.services.request;

import org.json.JSONObject;

public class Settings {
    public final Object apns;
    public final Object gcm;

    public Settings(Object apns, Object gcm) {
        this.apns = apns;
        this.gcm = gcm;
    }
}
