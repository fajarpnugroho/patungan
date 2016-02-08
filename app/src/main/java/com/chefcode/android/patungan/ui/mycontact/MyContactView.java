package com.chefcode.android.patungan.ui.mycontact;

import com.chefcode.android.patungan.firebase.model.Contact;

import java.util.List;

public interface MyContactView {
    void onLoading(boolean loading);

    void showListContact(List<Contact> contacts);
}
