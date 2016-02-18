package com.chefcode.android.patungan.firebase.model;

import java.util.HashMap;

public class InvitedMember {

    private HashMap<String, User> members;

    public InvitedMember() {}

    public InvitedMember(HashMap<String, User> members) {
        this.members = members;
    }

    public HashMap<String, User> getMembers() {
        return members;
    }
}
