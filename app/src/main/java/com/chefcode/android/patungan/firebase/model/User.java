package com.chefcode.android.patungan.firebase.model;

import com.chefcode.android.patungan.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class User {
    public String email;
    public String msisdn;
    public String tokenEcash;
    public String accountBalance;
    public String profilePict;
    public HashMap<String, Object> timestampJoin;

    public User() {}

    public User(String email, String msisdn, String tokenEcash, String accountBalance,
                String profilePict, HashMap<String, Object> timestampJoin) {
        this.email = email;
        this.msisdn = msisdn;
        this.tokenEcash = tokenEcash;
        this.accountBalance = accountBalance;
        this.profilePict = profilePict;
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

    public String getAccountBalance() {
        return accountBalance;
    }

    public String getProfilePict() {
        return profilePict;
    }

    public HashMap<String, Object> getTimestampJoin() {
        return timestampJoin;
    }

    @JsonIgnore
    public long getTimestampJoinLong() {
        return (long) timestampJoin.get(Constants.FIREBASE_TIMESTAMP_PROPERTY);
    }
}
