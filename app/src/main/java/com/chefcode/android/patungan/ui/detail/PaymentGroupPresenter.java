package com.chefcode.android.patungan.ui.detail;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.chefcode.android.patungan.delegate.AccountManager;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.services.ServiceConfigs;
import com.chefcode.android.patungan.services.api.PushService;
import com.chefcode.android.patungan.services.api.TransferService;
import com.chefcode.android.patungan.services.request.Message;
import com.chefcode.android.patungan.services.request.SendMessageBody;
import com.chefcode.android.patungan.services.request.Settings;
import com.chefcode.android.patungan.services.request.TagNames;
import com.chefcode.android.patungan.services.request.Target;
import com.chefcode.android.patungan.services.response.MessagePushResponse;
import com.chefcode.android.patungan.services.response.TransferResponse;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaymentGroupPresenter {
    private PaymentGroupDetailView view;

    private Firebase rootRef;


    private SharedPreferences sharedPreferences;
    private TransferService transferService;
    private AccountManager patunganAccount;

    private PushService service;

    @Inject
    public PaymentGroupPresenter(SharedPreferences sharedPreferences,
                                 TransferService transferService, AccountManager patunganAccount,
                                 PushService service) {
        this.sharedPreferences = sharedPreferences;
        this.transferService = transferService;
        this.patunganAccount = patunganAccount;
        this.service = service;
    }

    @SuppressLint("NewApi")
    public void init(PaymentGroupDetailView view) {
        this.view = view;
        rootRef = new Firebase(Constants.FIREBASE_BASE_URL);
    }

    public void transferToOwnerPaymentGroup(final String groupId,
                                            int minimumPayment,
                                            final String recipient,
                                            final String description,
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
                            saveDataToFirebase(groupId, recipient, transferResponse,
                                    invitedMembers, description);
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

    private void saveDataToFirebase(final String paymentGroupId,
                                    String recipient,
                                    final TransferResponse transferResponse,
                                    List<User> invitedMembers, final String description) {

        HashMap<String, Object> updatedData = new HashMap<>();

        HashMap<String, Object> timestampModified = new HashMap<>();
        timestampModified.put(Constants.FIREBASE_TIMESTAMP_PROPERTY,
                ServerValue.TIMESTAMP);

        String userEncodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL,"");
        final int amountTransfer = StringUtils.getNumberOfAccountBalance(transferResponse.amount);

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
                            + Constants.FIREBASE_BUCKET_PROPERTY, amountTransfer);

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
                        + Constants.FIREBASE_BUCKET_PROPERTY, amountTransfer);

        rootRef.updateChildren(updatedData, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    updateAccountBalance(amountTransfer);
                    view.onTransfering(false);
                    view.successTransfer();

                    sendMessagePush(paymentGroupId, transferResponse.amount, description);
                }
            }
        });

    }

    private void sendMessagePush(String paymentGroupId, String amount, String description) {
        String alert = sharedPreferences.getString(Constants.MSISDN, "")
                + " telah mengirimkan uang sebesar " + amount
                + " untuk " + description;

        Message message = new Message(alert);

        List<String> tags = new ArrayList<>();
        tags.add(paymentGroupId);

        Target target = new Target(tags);

        Call<MessagePushResponse> call = service.sendMessagePush(ServiceConfigs.BLUEMIX_APP_ID,
                new SendMessageBody(message, target, new Settings(new Object(),
                        new Object())));
        call.enqueue(new Callback<MessagePushResponse>() {
            @Override
            public void onResponse(Response<MessagePushResponse> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void updateAccountBalance(int ammountTransfer) {
        String accountBalanceString = sharedPreferences.getString(Constants.ACCOUNT_BALANCE,
                "Rp 0, 00");

        int accountBalanceNum = StringUtils.getNumberOfAccountBalance(accountBalanceString);
        int diffAccountBalanceTransfer = accountBalanceNum - ammountTransfer;

        String diffAccountBalanceTransferString = StringUtils
                .convertToRupiah(diffAccountBalanceTransfer);

        sharedPreferences.edit().putString(Constants.ACCOUNT_BALANCE,
                diffAccountBalanceTransferString).apply();
    }
}
