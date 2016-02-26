package com.chefcode.android.patungan.services.response;

public class AccountHistoryDetail {
    public final String transferType;
    public final String name;
    public final String username;
    public final String description;
    public final String transactionDate;
    public final String amount;
    public final String transactionNumber;
    public final String status;
    public final String parentTransactionNumber;

    public AccountHistoryDetail(String transferType, String name, String username,
                                String description, String transactionDate, String amount,
                                String transactionNumber, String status,
                                String parentTransactionNumber) {
        this.transferType = transferType;
        this.name = name;
        this.username = username;
        this.description = description;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionNumber = transactionNumber;
        this.status = status;
        this.parentTransactionNumber = parentTransactionNumber;
    }
}
