package com.chefcode.android.patungan.services.request;

public class SendMessageBody {
    public final Message message;
    public final Target target;
    public final Settings settings;

    public SendMessageBody(Message message, Target target, Settings settings) {
        this.message = message;
        this.target = target;
        this.settings = settings;
    }
}
