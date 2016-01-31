package com.chefcode.android.patungan.firebase.model;

import java.util.HashMap;

public class PaymentGroup {
    public String groupName;
    public String owner;
    public int Invoice;
    public int bucket;
    public HashMap<String, Object> timestampJoined;
    public HashMap<String, Object> timestampModified;
    public HashMap<String, Object> member;

    public PaymentGroup(String groupName, String owner, int invoice, int bucket,
                        HashMap<String, Object> timestampJoined,
                        HashMap<String, Object> timestampModified,
                        HashMap<String, Object> member) {
        this.groupName = groupName;
        this.owner = owner;
        Invoice = invoice;
        this.bucket = bucket;
        this.timestampJoined = timestampJoined;
        this.timestampModified = timestampModified;
        this.member = member;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getOwner() {
        return owner;
    }

    public int getInvoice() {
        return Invoice;
    }

    public int getBucket() {
        return bucket;
    }

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }

    public HashMap<String, Object> getTimestampModified() {
        return timestampModified;
    }

    public HashMap<String, Object> getMember() {
        return member;
    }
}
