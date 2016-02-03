package com.chefcode.android.patungan.ui.paymentgroupedit;

public interface EditPaymentGroupView {
    void setPaymentGroupName(String value);

    void setTotalCost(int value);

    String getPaymentGroupName();

    String getTotalCost();

    void addedInvitedMember(Object invitedMember);

    void showErrorMessage(String message);

    void showPaymentGroupError(String message);

    void showTotalCostError(String message);

    void finishEdit();

    void showErrorDialog(String message);
}
