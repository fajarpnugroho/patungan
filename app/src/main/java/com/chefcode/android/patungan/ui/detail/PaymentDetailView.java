package com.chefcode.android.patungan.ui.detail;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;

import java.util.List;

public interface PaymentDetailView {
    void listInvitedMember(List<User> invitedMembers);

    void listPaidMember(List<User> paidMember);

    void paymentGroupDetail(PaymentGroup paymentGroup);
}
