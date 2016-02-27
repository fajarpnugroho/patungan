package com.chefcode.android.patungan.utils;

import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;

import timber.log.Timber;

public final class PushUtils {

    private PushUtils() {
    }

    public static void subscribeTags(String tagName) {
        if (tagName == null) {
            return;
        }
        MFPPush push = MFPPush.getInstance();
        push.subscribe(tagName, new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                Timber.v("Success subrcibe tags");
            }

            @Override
            public void onFailure(MFPPushException exception) {
                Timber.v("Failed subrcibe tags");
            }
        });
    }

}
