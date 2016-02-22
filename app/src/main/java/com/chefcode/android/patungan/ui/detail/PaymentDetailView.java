package com.chefcode.android.patungan.ui.detail;

import com.chefcode.android.patungan.firebase.model.User;

import java.util.List;

public interface PaymentDetailView {
    void listInvitedMember(List<User> invitedMembers);
}
