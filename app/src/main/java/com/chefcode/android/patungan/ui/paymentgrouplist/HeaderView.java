package com.chefcode.android.patungan.ui.paymentgrouplist;

public interface HeaderView {
    void onLoading(boolean loading);

    void showAccountName(String name);

    void showBalanceInquiryUser(String balance);

    void showErrorMessage(String message);
}
