package com.chefcode.android.patungan.services.response;

public class PushnotifResponse {
    public final String uri;
    public final String name;
    public final String description;
    public final String createdTime;
    public final String lastUpdatedTime;
    public final String createdMode;
    public final String href;

    public PushnotifResponse(String uri, String name, String description, String createdTime,
                             String lastUpdatedTime, String createdMode, String href) {
        this.uri = uri;
        this.name = name;
        this.description = description;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.createdMode = createdMode;
        this.href = href;
    }
}
