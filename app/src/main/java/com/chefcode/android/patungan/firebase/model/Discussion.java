package com.chefcode.android.patungan.firebase.model;

import com.chefcode.android.patungan.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class Discussion {
    public String author;
    public String message;
    public HashMap<String, Object> timestampCreated;

    public Discussion() {}

    public Discussion(String author, String message, HashMap<String, Object> timestampCreated) {
        this.author = author;
        this.message = message;
        this.timestampCreated = timestampCreated;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampCreated.get(Constants.FIREBASE_TIMESTAMP_PROPERTY);
    }
}
