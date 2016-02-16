package com.chefcode.android.patungan.ui.list;

import com.firebase.client.Firebase;

public interface DialogNewPaymentGroupView {

    String getPaymentGroupName();

    void showPaymentGroupNameError();

    void addPaymentGroupNameDone(Firebase pushId);
}
