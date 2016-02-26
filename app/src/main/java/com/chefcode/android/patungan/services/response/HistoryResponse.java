package com.chefcode.android.patungan.services.response;

import java.util.List;

public class HistoryResponse {
    public final String accountBalance;
    public final String creditLimit;
    public final String status;
    public final List<AccountHistoryDetail> accountHistoryDetails;
    public final String onPage;
    public final String pageSize;
    public final String totalCount;

    public HistoryResponse(String accountBalance, String creditLimit, String status,
                           List<AccountHistoryDetail> accountHistoryDetails, String onPage,
                           String pageSize, String totalCount) {
        this.accountBalance = accountBalance;
        this.creditLimit = creditLimit;
        this.status = status;
        this.accountHistoryDetails = accountHistoryDetails;
        this.onPage = onPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
}
