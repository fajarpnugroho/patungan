package com.chefcode.android.patungan.services.request;

public class CreateTagBody {
    public final String name;
    public final String description;

    public CreateTagBody(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
