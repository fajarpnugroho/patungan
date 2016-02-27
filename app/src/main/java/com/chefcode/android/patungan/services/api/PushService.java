package com.chefcode.android.patungan.services.api;

import com.chefcode.android.patungan.services.request.CreateTagBody;
import com.chefcode.android.patungan.services.request.SendMessageBody;
import com.chefcode.android.patungan.services.response.MessagePushResponse;
import com.chefcode.android.patungan.services.response.PushnotifResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PushService {
    @Headers({
            "appSecret: 97ddea33-dfb8-4312-ba4c-f50b49037f80",
            "Content-Type: application/json"
    })
    @POST("apps/{application_id}/tags")
    Call<PushnotifResponse> createTag(@Path("application_id") String applicationId,
                   @Body CreateTagBody body);

    @Headers({
            "appSecret: 97ddea33-dfb8-4312-ba4c-f50b49037f80",
            "Content-Type: application/json"
    })
    @POST("apps/{application_id}/messages")
    Call<MessagePushResponse> sendMessagePush(@Path("application_id") String applicationId,
                         @Body SendMessageBody body);
}
