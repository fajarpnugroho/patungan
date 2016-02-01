package com.chefcode.android.patungan.ui.paymentgrouplist;

import com.firebase.client.Firebase;

public interface DialogNewPaymentGroupView {

    String getPaymentGroupName();

    void showPaymentGroupNameError();

    void addPaymentGroupNameDone(Firebase pushId);
}
