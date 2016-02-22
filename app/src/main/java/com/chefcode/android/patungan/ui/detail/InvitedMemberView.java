package com.chefcode.android.patungan.ui.detail;

import com.chefcode.android.patungan.firebase.model.InvitedMember;
import com.chefcode.android.patungan.firebase.model.User;

import java.util.List;

public interface InvitedMemberView {
    void showListInvitedMember(List<User> invitedMember);

    void listPaidMember(List<User> paidMember);
}
