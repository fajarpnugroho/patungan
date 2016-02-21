package com.chefcode.android.patungan.services.response;

public class TransferResponse {
    public final String fromAccount;
    public final String toAccount;
    public final String amount;
    public final String status;
    public final String trxDate;
    public final String trxID;
    public final String traceNumber;
    public final String description;

    public TransferResponse(String fromAccount, String toAccount, String amount, String status,
                            String trxDate, String trxID, String traceNumber, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
        this.trxDate = trxDate;
        this.trxID = trxID;
        this.traceNumber = traceNumber;
        this.description = description;
    }
}
