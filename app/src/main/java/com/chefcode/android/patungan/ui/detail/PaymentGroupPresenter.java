package com.chefcode.android.patungan.ui.detail;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.chefcode.android.patungan.delegate.AccountManager;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.services.ServiceConfigs;
import com.chefcode.android.patungan.services.api.TransferService;
import com.chefcode.android.patungan.services.response.TransferResponse;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaymentGroupPresenter {
    private PaymentGroupDetailView view;

    private Firebase rootRef;
    private Firebase userPaymentGroupRef;
    private Firebase paymentGroupRef;
    private ValueEventListener userPaymentGroupListener;

    private SharedPreferences sharedPreferences;
    private TransferService transferService;
    private AccountManager patunganAccount;


    @Inject
    public PaymentGroupPresenter(SharedPreferences sharedPreferences,
                                 TransferService transferService, AccountManager patunganAccount) {
        this.sharedPreferences = sharedPreferences;
        this.transferService = transferService;
        this.patunganAccount = patunganAccount;
    }

    @SuppressLint("NewApi")
    public void init(PaymentGroupDetailView view) {
        this.view = view;
        String encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");

        if (Objects.equals(encodedEmail, "")) return;

        rootRef = new Firebase(Constants.FIREBASE_BASE_URL);

        userPaymentGroupRef = rootRef.child(Constants.FIREBASE_PAYMENT_GROUP_LOCATION)
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

    public void transferToOwnerPaymentGroup(final String groupId,
                                            int minimumPayment,
                                            final String recipient,
                                            String description,
                                            String credential,
                                            final List<User> invitedMembers) {

        view.onTransfering(true);

        if (credential.equals(sharedPreferences.getString(Constants.MY_CREDENTIALS, ""))) {
            String token = sharedPreferences.getString(Constants.MANDIRI_TOKEN, "");
            String toAccount = StringUtils.getPhoneNumberFromEncodedEmail(recipient);
            String fromAccount = StringUtils
                    .getPhoneNumberFromEncodedEmail(sharedPreferences
                            .getString(Constants.ENCODED_EMAIL, ""));
            Call<TransferResponse> call = transferService.transferMember(minimumPayment, toAccount,
                    token, description, credential, fromAccount);
            call.enqueue(new Callback<TransferResponse>() {
                @Override
                public void onResponse(Response<TransferResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        TransferResponse transferResponse = response.body();
                        // success
                        if (transferResponse.status.equals("PROCESSED")) {
                            saveDataToFirebase(groupId, recipient, transferResponse, invitedMembers);
                        } else if (transferResponse.status
                                .equals(ServiceConfigs.RESPONSE_TOKEN_EXPIRED)) {
                            patunganAccount.forceLogout();
                        } else {
                            view.errorTransfer(transferResponse.status);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    view.errorTransfer(t.getMessage());
                }
            });
        } else {
            view.errorTransfer("Wrong password!");
        }
    }

    private void saveDataToFirebase(String paymentGroupId,
                                    String recipient,
                                    TransferResponse transferResponse,
                                    List<User> invitedMembers) {

        HashMap<String, Object> updatedData = new HashMap<>();

        HashMap<String, Object> timestampModified = new HashMap<>();
        timestampModified.put(Constants.FIREBASE_TIMESTAMP_PROPERTY,
                ServerValue.TIMESTAMP);

        String userEncodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL,"");

        for (User user : invitedMembers) {
            if (userEncodedEmail.equals(user.getEmail())) {

                // add user encoded email to paidMember
                HashMap<String, Object> paidMemberFirebase = (HashMap<String, Object>)
                        new ObjectMapper().convertValue(user, Map.class);

                updatedData.put("/" + Constants.FIREBASE_PAID_MEMBER_LOCATION + "/"
                        + paymentGroupId + "/" + user.getEmail(), paidMemberFirebase);
            }

            // update paymentGroup invited member value
            updatedData.put("/" + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                            + user.getEmail() + "/"
                            + paymentGroupId + "/"
                            + Constants.FIREBASE_TIMESTAMP_MODIFIED_PROPERTY,
                    timestampModified);

            updatedData.put("/" + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                            + user.getEmail() + "/"
                            + paymentGroupId + "/"
                            + Constants.FIREBASE_BUCKET_PROPERTY,
                    StringUtils.getNumberOfAccountBalance(transferResponse.amount));

        }

        // update paymentGroup owner value
        updatedData.put("/" + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                        + recipient + "/"
                        + paymentGroupId + "/"
                        + Constants.FIREBASE_TIMESTAMP_MODIFIED_PROPERTY,
                timestampModified);

        updatedData.put("/" + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                        + recipient + "/"
                        + paymentGroupId + "/"
                        + Constants.FIREBASE_BUCKET_PROPERTY,
                StringUtils.getNumberOfAccountBalance(transferResponse.amount));

        rootRef.updateChildren(updatedData, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    view.onTransfering(false);
                    view.successTransfer();
                }
            }
        });

    }
}
