package com.chefcode.android.patungan.utils;

public class Constants {
    public static final String MANDIRI_TOKEN = "mandiriToken";
    public static final String MSISDN = "msisdn";
    public static final String ENCODED_EMAIL = "encoded_email";
    public static final String CREDENTIALS = "123456";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String DEFAULT_ACCOUNT_BALANCE = "Rp 0,00";
    public static final String ACCOUNT_BALANCE = "account_balance";
    public static final String EXTRA_ERROR_MESSAGE = "extra_error_message";
    public static final String DEFAULT_PROFILE_IMAGES = "http://www.gravatar.com/avatar/%s?s=45&d=wavatar";
    public static final String FIREBASE_GROUP_NAME_PROPERTY = "groupName";
    public static final String FIREBASE_INVOICE_PROPERTY = "invoice";
    public static final String FIREBASE_TIMESTAMP_MODIFIED_PROPERTY = "timestampModified";
    public static final String AVATAR = "avatar";
    public static final String PAYMENT_GROUP_ID = "payment_group_id";
    public static final String MY_CREDENTIALS = "myCredentials";


    public Constants() {}

    // firebase config
    public static final String FIREBASE_BASE_URL = "https://patungan-dev.firebaseio.com";

    // location
    public static final String FIREBASE_USER_LOCATION = "users";
    public static final String FIREBASE_PAYMENT_GROUP_LOCATION = "paymentGroup";
    public static final String FIREBASE_INVITED_MEMBER_LOCATION = "invitedMember";

    // url
    public static final String FIREBASE_USER_URL = FIREBASE_BASE_URL + "/" + FIREBASE_USER_LOCATION;
    public static final String FIREBASE_PAYMENT_GROUP_URL = FIREBASE_BASE_URL + "/" + FIREBASE_PAYMENT_GROUP_LOCATION;
    public static final String FIREBASE_INVITED_MEMBER_URL = FIREBASE_BASE_URL + "/" + FIREBASE_INVITED_MEMBER_LOCATION;

    // property
    public static final String FIREBASE_TIMESTAMP_PROPERTY = "timestamp";
    public static final String FIREBASE_ACCOUNT_BALANCE_PROPERTY = "accountBalance";
    public static final String FIREBASE_MINIMUM_PAYMENT_PROPERTY = "minimumPayment";

}
