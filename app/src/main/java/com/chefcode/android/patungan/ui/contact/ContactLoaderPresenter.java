package com.chefcode.android.patungan.ui.contact;

import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.MD5Utils;
import com.chefcode.android.patungan.utils.StringUtils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class ContactLoaderPresenter {

    private Firebase userRef;
    private Firebase paymentGroupRef;

    @Inject
    public ContactLoaderPresenter() {
    }

    public void init() {
        userRef = new Firebase(Constants.FIREBASE_USER_URL);
        paymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL);
    }

    public void createNewUser(final String emailOwnerPaymentGroup, final String paymentGroupId,
                              final String phoneNumber) {
        final String phoneMail = phoneNumber + "@patungan.com";
        final String encodedEmail = StringUtils.encodedEmail(phoneMail);

        final Firebase newUserRef = userRef.child(encodedEmail);
        newUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user;

                if (dataSnapshot == null) {

                    String generatedProfilePict = String.format(Constants.DEFAULT_PROFILE_IMAGES,
                            MD5Utils.md5Hex(phoneMail));

                    HashMap<String, Object> timestampJoin = new HashMap<>();
                    timestampJoin.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);

                    user = new User(encodedEmail,
                            phoneNumber,
                            Constants.DEFAULT_ACCOUNT_BALANCE,
                            generatedProfilePict,
                            timestampJoin);

                    newUserRef.setValue(user);
                } else {
                    user = dataSnapshot.getValue(User.class);
                }

                if (user == null) {
                    Timber.e("Failed create new user when invited member");
                    return;
                }

                inviteMember(emailOwnerPaymentGroup, paymentGroupId, user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Timber.e(firebaseError.getMessage());
            }
        });
    }

    public void inviteMember(String emailOwnerPaymentGroup, String paymentGroupId, User user) {


    }

    public void uninvitedMember(String paymentGroupId, String phoneNumber) {

    }
}
