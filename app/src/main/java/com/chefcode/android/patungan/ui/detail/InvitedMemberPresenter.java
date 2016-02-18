package com.chefcode.android.patungan.ui.detail;

import com.chefcode.android.patungan.firebase.model.InvitedMember;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InvitedMemberPresenter {

    private InvitedMemberView view;
    private Firebase invitedMemberRef;
    private Firebase paymentGroupInvitedMemberRef;
    private ValueEventListener invitedMemberValueListener;

    @Inject
    public InvitedMemberPresenter() {}

    public void init(InvitedMemberView view) {
        this.view = view;

        this.invitedMemberRef = new Firebase(Constants.FIREBASE_INVITED_MEMBER_URL);
    }

    public void getListInvitedMember(String paymentGroupId) {
        paymentGroupInvitedMemberRef = invitedMemberRef.child(paymentGroupId);
        invitedMemberValueListener = paymentGroupInvitedMemberRef
                .addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               List<User> invitedMembers =
                                                       new ArrayList<User>();
                                               for (DataSnapshot child
                                                       : dataSnapshot.getChildren()) {
                                                   User user = child.getValue(User.class);
                                                   invitedMembers.add(user);
                                               }
                                               view.showListInvitedMember(invitedMembers);
                                           }

                                           @Override
                                           public void onCancelled(FirebaseError firebaseError) {

                                           }
                                       }
                );
    }

    public void removeValueEventListener() {
        paymentGroupInvitedMemberRef.removeEventListener(invitedMemberValueListener);
    }
}
