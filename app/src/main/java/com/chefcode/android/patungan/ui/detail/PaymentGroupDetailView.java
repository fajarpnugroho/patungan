package com.chefcode.android.patungan.ui.detail;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;

public interface PaymentGroupDetailView {
    void populate(PaymentGroup paymentGroup);

    void errorTransfer(String message);

    void successTransfer();

    void onTransfering(boolean transfering);
}
