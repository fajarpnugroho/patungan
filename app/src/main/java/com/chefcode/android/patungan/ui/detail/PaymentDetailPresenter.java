package com.chefcode.android.patungan.ui.detail;

import android.content.SharedPreferences;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class PaymentDetailPresenter {
    private PaymentDetailView view;
    private SharedPreferences sharedPreferences;

    private Firebase paymentGroupRef;
    private Firebase userPaymentGroupRef;
    private ValueEventListener userPaymentGroupListener;

    private Firebase invitedMemberRef;
    private Firebase paymentGroupInvitedMemberRef;
    private ValueEventListener invitedMemberValueListener;

    private Firebase paidMemberRef;
    private Firebase paymentGroupPaidMemberRef;
    private ValueEventListener paidMemberValueListener;

    private Firebase discussionRef;
    private Firebase paymentGroupDiscussionRef;
    private ValueEventListener paymentGroupDiscussionListener;

    @Inject
    public PaymentDetailPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void init(PaymentDetailView view) {
        this.view = view;
        this.invitedMemberRef = new Firebase(Constants.FIREBASE_INVITED_MEMBER_URL);
        this.paidMemberRef = new Firebase(Constants.FIREBASE_PAID_MEMBER_URL);

        String encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
        this.userPaymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL)
                .child(encodedEmail);

        this.discussionRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_DISCUSSION_URL);
    }

    public void getPaymentDetail(String paymentGroupId) {
        this.paymentGroupRef = userPaymentGroupRef.child(paymentGroupId);
        userPaymentGroupListener = paymentGroupRef
                .addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               if (dataSnapshot.getValue() != null) {
                                                   PaymentGroup paymentGroup = dataSnapshot
                                                           .getValue(PaymentGroup.class);
                                                   view.paymentGroupDetail(paymentGroup);
                                               }
                                           }

                                           @Override
                                           public void onCancelled(FirebaseError firebaseError) {

                                           }
                                       }
                );
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
                                               view.listInvitedMember(invitedMembers);
                                           }

                                           @Override
                                           public void onCancelled(FirebaseError firebaseError) {

                                           }
                                       }
                );
    }

    public void getPaidMember(String paymentGroupId) {
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

    public void removeValueEventListener() {
        this.paymentGroupInvitedMemberRef.removeEventListener(invitedMemberValueListener);
        this.paymentGroupPaidMemberRef.removeEventListener(paidMemberValueListener);
        this.paymentGroupRef.removeEventListener(userPaymentGroupListener);
        this.paymentGroupDiscussionRef.removeEventListener(paymentGroupDiscussionListener);
    }

    public void getDiscussion(String paymentGroupId) {
        this.paymentGroupDiscussionRef = discussionRef.child(paymentGroupId);
        this.paymentGroupDiscussionListener = paymentGroupDiscussionRef
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> discussionItem = new HashMap<String, Object>();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            discussionItem.put(child.getKey(), child.getValue());
                        }
                        view.listOfMessage(discussionItem);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }
}
