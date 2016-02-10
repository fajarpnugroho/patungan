package com.chefcode.android.patungan.ui.contact;

import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.MD5Utils;
import com.chefcode.android.patungan.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class ContactLoaderPresenter {

    private Firebase rootRef;
    private Firebase userRef;
    private HashMap<String, User> listInvitedMember;

    @Inject
    public ContactLoaderPresenter() {
    }

    public void init() {
        rootRef = new Firebase(Constants.FIREBASE_BASE_URL);
        userRef = new Firebase(Constants.FIREBASE_USER_URL);
    }

    public void updateUserData(final boolean invited,
                               final String paymentGroupId,
                               final String phoneNumber) {

        final String phoneMail = phoneNumber + "@patungan.com";
        final String encodedEmail = StringUtils.encodedEmail(phoneMail);

        final Firebase newUserRef = userRef.child(encodedEmail);
        newUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user;

                if (dataSnapshot.getValue() == null) {

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

                updateInvitedMember(invited, paymentGroupId, user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Timber.e(firebaseError.getMessage());
            }
        });
    }

    public void updateInvitedMember(boolean invited, String paymentGroupId, User user) {
        HashMap<String, Object> updatedUserData = new HashMap<String, Object>();

        if (invited) {

            /*HashMap<String, Object> paymentGroupForFirebase = (HashMap<String, Object>)
                new ObjectMapper().convertValue(activePaymentGroup, Map.class);*/

            /*updatedUserData.put("/" + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                + user.getEmail() + "/" + paymentGroupId, paymentGroupForFirebase);*/

            HashMap<String, Object> invitedMemberForFirebase = (HashMap<String, Object>)
                    new ObjectMapper().convertValue(user, Map.class);

            updatedUserData.put("/" + Constants.FIREBASE_INVITED_MEMBER_LOCATION + "/"
                    + paymentGroupId + "/" + user.getEmail(), invitedMemberForFirebase);


        } else {
            HashMap<String, User> newInvitedMember = new HashMap<>(listInvitedMember);

            updatedUserData.put("/" + Constants.FIREBASE_INVITED_MEMBER_LOCATION + "/"
                    + paymentGroupId + "/" + user.getEmail(), null);

            newInvitedMember.remove(user.getEmail());
        }

        rootRef.updateChildren(updatedUserData, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Timber.e(firebaseError.getMessage());
                }
            }
        });
    }

    public void setListInvitedMember(HashMap<String, User> listInvitedMember) {
        this.listInvitedMember = listInvitedMember;
    }
}
