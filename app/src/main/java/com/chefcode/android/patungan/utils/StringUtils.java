package com.chefcode.android.patungan.utils;

public class StringUtils {
    public StringUtils() {
    }

    public static String encodedEmail(String email) {
        return email.replace(".", ",");
    }
}
