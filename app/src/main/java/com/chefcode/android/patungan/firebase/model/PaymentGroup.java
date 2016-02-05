package com.chefcode.android.patungan.firebase.model;

import com.chefcode.android.patungan.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;

public class PaymentGroup {
    public String groupName;
    public String owner;
    public int invoice;
    public int bucket;
    public HashMap<String, Object> timestampCreated;
    public HashMap<String, Object> timestampModified;
    public HashMap<String, User> member;

    // Dont forget this thing, its REQUIRED!
    public PaymentGroup() {
    }

    public PaymentGroup(String groupName, String owner, int invoice, int bucket,
                        HashMap<String, Object> timestampCreated) {
        this.groupName = groupName;
        this.owner = owner;
        this.invoice = invoice;
        this.bucket = bucket;
        this.timestampCreated = timestampCreated;

        HashMap<String, Object> timestampNowObject = new HashMap<>();
        timestampNowObject.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);

        this.timestampModified = timestampNowObject;
        this.member = new HashMap<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public String getOwner() {
        return owner;
    }

    public int getInvoice() {
        return invoice;
    }

    public int getBucket() {
        return bucket;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    public HashMap<String, Object> getTimestampModified() {
        return timestampModified;
    }

    public HashMap getMember() {
        return member;
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampCreated.get(Constants.FIREBASE_TIMESTAMP_PROPERTY);
    }

    @JsonIgnore
    public long getTimestampModifiedLong() {
        return (long) timestampModified.get(Constants.FIREBASE_TIMESTAMP_PROPERTY);
    }

    public void setTimestampLastChangedToNow() {
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);
        this.timestampModified = timestampNowObject;
    }
}
