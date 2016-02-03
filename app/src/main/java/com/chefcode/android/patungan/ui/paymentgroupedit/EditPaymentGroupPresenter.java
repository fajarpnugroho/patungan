package com.chefcode.android.patungan.ui.paymentgroupedit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.StringUtils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class EditPaymentGroupPresenter {
    public static final int TOTAL_INVITED_MEMBER = 4;
    private EditPaymentGroupView view;
    private SharedPreferences sharedPreferences;
    private Firebase userPaymentGroupRef;
    private Firebase detailPaymentGroupRef;
    private ValueEventListener valueEventListener;
    private String encodedEmail;
    private String pushId;
    private Context context;

    @Inject
    public EditPaymentGroupPresenter(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    public void init(EditPaymentGroupView view) {
        this.view = view;
        encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
        userPaymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL)
                .child(encodedEmail);
    }

    public void valueListenerPaymentGroup(String pushId) {
        if (pushId == null) return;

        this.pushId = pushId;

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    PaymentGroup paymentGroup = dataSnapshot.getValue(PaymentGroup.class);
                    view.setPaymentGroupName(paymentGroup.getGroupName());
                    view.setTotalCost(paymentGroup.getInvoice());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Timber.d("Edit payment detail " + firebaseError.getMessage());
            }
        };

        detailPaymentGroupRef = userPaymentGroupRef.child(pushId);
        detailPaymentGroupRef.addValueEventListener(valueEventListener);
    }

    public void removeValueListener() {
        detailPaymentGroupRef.removeEventListener(valueEventListener);
    }

    public void saveEditedPaymentGroup() {
        if (TextUtils.isEmpty(view.getPaymentGroupName())) {
            view.showPaymentGroupError("Payment group name cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(view.getTotalCost())) {
            view.showTotalCostError("Total cost cannot be empty");
            return;
        }

        // TODO get total invited member
        String accountBalance = sharedPreferences.getString(Constants.ACCOUNT_BALANCE, "");
        int splitAccountBalanceNumber = StringUtils.getNumberOfAccountBalance(accountBalance)
                / TOTAL_INVITED_MEMBER;

        if (splitAccountBalanceNumber < Integer.parseInt(view.getTotalCost())) {
            String message = String.format(context
                            .getString(R.string.message_unsufficient_account_balance),
                    StringUtils.convertToRupiah(splitAccountBalanceNumber));
            view.showTotalCostError(message);
            return;
        }


        HashMap<String, Object> editPaymentGroup = new HashMap<>();
        editPaymentGroup.put(Constants.FIREBASE_GROUP_NAME_PROPERTY, view.getPaymentGroupName());
        editPaymentGroup.put(Constants.FIREBASE_INVOICE_PROPERTY, view.getTotalCost());

        HashMap<String, Object> timeModified = new HashMap<>();
        timeModified.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);
        editPaymentGroup.put(Constants.FIREBASE_TIMESTAMP_MODIFIED_PROPERTY, timeModified);

        detailPaymentGroupRef.updateChildren(editPaymentGroup, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    view.finishEdit();
                }
            }
        });
    }

    public void deleteGroupIfNotComplete() {
        if (TextUtils.isEmpty(view.getTotalCost()) || Integer.parseInt(view.getTotalCost()) <= 0) {
            HashMap<String, Object> delete = new HashMap<>();
            delete.put("/" + pushId, null);
            userPaymentGroupRef.updateChildren(delete);
        }
        view.finishEdit();
    }
}
