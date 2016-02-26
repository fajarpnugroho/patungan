package com.chefcode.android.patungan.utils;

import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;

public final class PushUtils {
    private static MFPPush push;

    private PushUtils() {
    }

    public static void subscribeTags(String tagName) {
        if (tagName == null) {
            return;
        }
        push = MFPPush.getInstance();
        push.subscribe(tagName, new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(MFPPushException exception) {

            }
        });
    }

}
