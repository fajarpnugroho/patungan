package com.chefcode.android.patungan.ui.paymentgroupedit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;
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
    private Firebase rootRef;

    private Firebase paymentGroupRef;
    private ValueEventListener paymentGroupValueListener;

    private Firebase invitedMemberRef;
    private ValueEventListener invitedMemberValueListener;

    private String encodedEmail;
    private String paymentGroupId;
    private Context context;
    private HashMap<String, User> invitedMember;

    @Inject
    public EditPaymentGroupPresenter(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    public void init(EditPaymentGroupView view, String paymentGroupId) {
        this.view = view;
        this.encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
        this.paymentGroupId = paymentGroupId;

        this.rootRef = new Firebase(Constants.FIREBASE_BASE_URL);
        this.paymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL)
                .child(encodedEmail).child(paymentGroupId);
        this.invitedMemberRef = new Firebase(Constants.FIREBASE_INVITED_MEMBER_URL)
                .child(paymentGroupId);
    }

    public void valueListenerPaymentGroup() {
        paymentGroupValueListener = paymentGroupRef.addValueEventListener(new ValueEventListener() {
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
        });
    }

    public void valueListenerInvitedMember() {
        invitedMemberValueListener = invitedMemberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                invitedMember = new HashMap<>();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    invitedMember.put(user.getKey(), user.getValue(User.class));
                }
                view.showInvitedMember(invitedMember);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Timber.e("Get Invited Member Error " + firebaseError.getMessage());
            }
        });
    }

    public void removePaymentGroupValueListener() {
        paymentGroupRef.removeEventListener(paymentGroupValueListener);
    }

    public void removeInvitedMemberListener() {
        invitedMemberRef.removeEventListener(invitedMemberValueListener);
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

        paymentGroupRef.updateChildren(editPaymentGroup, new Firebase.CompletionListener() {
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
            HashMap<String, Object> deletePaymentGroup = new HashMap<>();
            deletePaymentGroup.put("/"
                    + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                    + encodedEmail + "/"
                    + paymentGroupId, null);

            deletePaymentGroup.put("/"
                    + Constants.FIREBASE_INVITED_MEMBER_LOCATION + "/"
                    + paymentGroupId, null);

            rootRef.updateChildren(deletePaymentGroup);
        }
        view.finishEdit();
    }

    public void uninvitedMember(String paymentGroupId, String encodedMail) {
        HashMap<String, Object> deletePaymentGroup = new HashMap<>();
        deletePaymentGroup.put("/"
                + Constants.FIREBASE_INVITED_MEMBER_LOCATION + "/"
                + paymentGroupId + "/"
                + encodedMail, null);

        rootRef.updateChildren(deletePaymentGroup);
    }
}
