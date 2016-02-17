package com.chefcode.android.patungan.ui.detail;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Objects;

import javax.inject.Inject;

public class PaymentGroupPresenter {
    private PaymentGroupDetailView view;
    private Firebase userPaymentGroupRef;
    private Firebase paymentGroupRef;
    private ValueEventListener userPaymentGroupListener;
    private SharedPreferences sharedPreferences;

    @Inject
    public PaymentGroupPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @SuppressLint("NewApi")
    public void init(PaymentGroupDetailView view) {
        this.view = view;
        String encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");

        if (Objects.equals(encodedEmail, "")) return;

        userPaymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL)
                .child(encodedEmail);
    }

    public void getPaymentDetail(String paymentGroupId) {
        // show loading
        paymentGroupRef = userPaymentGroupRef.child(paymentGroupId);
        // call service
        userPaymentGroupListener = paymentGroupRef
                .addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               if (dataSnapshot.getValue() != null) {
                                                   PaymentGroup paymentGroup = dataSnapshot
                                                           .getValue(PaymentGroup.class);
                                                   view.populate(paymentGroup);
                                               }
                                           }

                                           @Override
                                           public void onCancelled(FirebaseError firebaseError) {

                                           }
                                       }
                );

    }

    public void removeListener() {
        paymentGroupRef.removeEventListener(userPaymentGroupListener);
    }
}
