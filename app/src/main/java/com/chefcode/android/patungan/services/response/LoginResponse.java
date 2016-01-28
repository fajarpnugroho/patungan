package com.chefcode.android.patungan.services.response;

public class LoginResponse {
    public static final String STATUS_LOGIN_FAILED = "LOGIN_FAILED";

    public final String token;
    public final String status;
    public final String msisdn;

    public LoginResponse(String token, String status, String msisdn) {
        this.token = token;
        this.status = status;
        this.msisdn = msisdn;
    }
}
