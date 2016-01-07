package com.fslab.android.patungan.services.response;

public class LoginResponse {
    public final String token;
    public final String status;
    public final String msisdn;

    public LoginResponse(String token, String status, String msisdn) {
        this.token = token;
        this.status = status;
        this.msisdn = msisdn;
    }
}
