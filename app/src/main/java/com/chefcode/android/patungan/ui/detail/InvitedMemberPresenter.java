package com.chefcode.android.patungan.ui.detail;

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

    private Firebase paidMemberRef;
    private Firebase paymentGroupPaidMemberRef;
    private ValueEventListener paidMemberValueListener;

    @Inject
    public InvitedMemberPresenter() {}

    public void init(InvitedMemberView view) {
        this.view = view;
        this.paidMemberRef = new Firebase(Constants.FIREBASE_PAID_MEMBER_URL);
    }

    public void loadPaidMember(String paymentGroupId) {
        this.paymentGroupPaidMemberRef = paidMemberRef.child(paymentGroupId);
        this.paidMemberValueListener = paymentGroupPaidMemberRef
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> paidMember =
                                new ArrayList<User>();
                        for (DataSnapshot child
                                : dataSnapshot.getChildren()) {
                            User user = child.getValue(User.class);
                            paidMember.add(user);
                        }
                        view.listPaidMember(paidMember);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    public void removeValueListener() {
        this.paymentGroupPaidMemberRef.removeEventListener(paidMemberValueListener);
    }

}
