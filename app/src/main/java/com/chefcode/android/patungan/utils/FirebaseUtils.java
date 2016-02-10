package com.chefcode.android.patungan.utils;

import com.chefcode.android.patungan.firebase.model.User;

import java.util.HashMap;

public class FirebaseUtils {

    public FirebaseUtils() {}

    public static final HashMap<String, Object> generatedUpdatedMap(
            HashMap<String, User> invitedMember,
            HashMap<String, Object> mapToUpdate,
            String paymentGroupId,
            String encodedEmailOwner,
            String propertyToUpdate,
            Object value) {

        mapToUpdate.put("/"
                + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                + encodedEmailOwner + "/"
                + paymentGroupId + "/"
                + propertyToUpdate, value);

        if (invitedMember != null) {

            for (User user : invitedMember.values()) {
                mapToUpdate.put("/"
                        + Constants.FIREBASE_PAYMENT_GROUP_LOCATION + "/"
                        + user.getEmail() + "/"
                        + paymentGroupId + "/"
                        + propertyToUpdate, value);
            }
        }
        return mapToUpdate;
    }
}
