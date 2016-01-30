package com.chefcode.android.patungan.services.response;

public class BalancesInquiryResponse {
    public final String accountBalance;
    public final String creditLimit;
    public final String status;

    public BalancesInquiryResponse(String accountBalance, String creditLimit, String status) {
        this.accountBalance = accountBalance;
        this.creditLimit = creditLimit;
        this.status = status;
    }
}
