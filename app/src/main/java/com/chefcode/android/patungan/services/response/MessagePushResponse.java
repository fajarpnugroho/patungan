package com.chefcode.android.patungan.services.response;

import com.chefcode.android.patungan.services.request.SendMessageBody;

public class MessagePushResponse {
    public final String messageId;
    public final Message message;

    public MessagePushResponse(String messageId, Message message) {
        this.messageId = messageId;
        this.message = message;
    }

    public class Message {
        public final SendMessageBody message;

        public Message(SendMessageBody message) {
            this.message = message;
        }
    }
}
