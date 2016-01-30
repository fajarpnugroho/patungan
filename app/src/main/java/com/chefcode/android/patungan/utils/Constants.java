package com.chefcode.android.patungan.utils;

public class Constants {
    public static final String MANDIRI_TOKEN = "mandiriToken";
    public static final String MSISDN = "msisdn";
    public static final String ENCODED_EMAIL = "encoded_email";
    public static final String CREDENTIALS = "123456";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String DEFAULT_ACCOUNT_BALANCE = "Rp 0,00";
    public static final String ACCOUNT_BALANCE = "account_balance";

    public Constants() {}

    // firebase config
    public static final String FIREBASE_BASE_URL = "https://patungan-dev.firebaseio.com";

    // location
    public static final String FIREBASE_USER_LOCATION = "users";

    // url
    public static final String FIREBASE_USER_URL = FIREBASE_BASE_URL + "/" + FIREBASE_USER_LOCATION;

    // property
    public static final String FIREBASE_TIMESTAMP_PROPERTY = "timestamp";
    public static final String FIREBASE_ACCOUNT_BALANCE_PROPERTY = "accountBalance";


}
