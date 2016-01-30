package com.chefcode.android.patungan.utils;

public class Constants {
    public static final String MANDIRI_TOKEN = "mandiriToken";
    public static final String MSISDN = "msisdn";
    public static final String ENCODED_EMAIL = "encoded_email";

    public Constants() {}

    // firebase config
    public static final String FIREBASE_BASE_URL = "https://patungan-dev.firebaseio.com";

    // location
    public static final String FIREBASE_USER_LOCATION = "users";

    // url
    public static final String FIREBASE_USER_URL = FIREBASE_BASE_URL + "/" + FIREBASE_USER_LOCATION;

    // property
    public static final String FIREBASE_TIMESTAMP_PROPERTY = "timestamp";

}
