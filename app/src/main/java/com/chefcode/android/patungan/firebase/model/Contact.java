package com.chefcode.android.patungan.firebase.model;

public class Contact {
    public String name;
    public String phoneNumber;

    public Contact() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replace("+62", "0");
        this.phoneNumber = phoneNumber;
    }
}
