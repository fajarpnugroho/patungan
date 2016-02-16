package com.chefcode.android.patungan.ui.list;

import android.content.SharedPreferences;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.HashMap;

import javax.inject.Inject;

public class DialogNewPaymentGroupPresenter {
    private DialogNewPaymentGroupView view;
    private Firebase paymentGroupRef;
    private SharedPreferences sharedPreferences;

    @Inject
    public DialogNewPaymentGroupPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void init(DialogNewPaymentGroupView view) {
        this.view = view;
        this.paymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL);
    }

    public void addPaymentGroup(String encodedMail) {
        if (view.getPaymentGroupName().equals("")) {
            return;
        }

        Firebase userPaymentGroupRef = paymentGroupRef.child(encodedMail);

        HashMap<String, Object> timestampCreated = new HashMap<>();
        timestampCreated.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);


        PaymentGroup paymentGroup = new PaymentGroup(view.getPaymentGroupName(),
                sharedPreferences.getString(Constants.ENCODED_EMAIL, ""),
                sharedPreferences.getString(Constants.AVATAR, null),
                0,
                0,
                0,
                timestampCreated);

        Firebase pushId = userPaymentGroupRef.push();
        pushId.setValue(paymentGroup);

        view.addPaymentGroupNameDone(pushId);
    }
}
