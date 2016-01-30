package com.chefcode.android.patungan.firebase;

import com.chefcode.android.patungan.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class User {
    public String email;
    public String msisdn;
    public String tokenEcash;
    public HashMap<String, Object> timestampJoin;

    public User() {}

    public User(String email, String msisdn, String tokenEcash,
                HashMap<String, Object> timestampJoin) {
        this.email = email;
        this.msisdn = msisdn;
        this.tokenEcash = tokenEcash;
        this.timestampJoin = timestampJoin;
    }

    public String getEmail() {
        return email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getTokenEcash() {
        return tokenEcash;
    }

    public HashMap<String, Object> getTimestampJoin() {
        return timestampJoin;
    }

    @JsonIgnore
    public long getTimestampJoinLong() {
        return (long) timestampJoin.get(Constants.FIREBASE_TIMESTAMP_PROPERTY);
    }
}
