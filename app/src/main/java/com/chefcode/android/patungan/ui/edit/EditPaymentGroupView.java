package com.chefcode.android.patungan.ui.edit;

import com.chefcode.android.patungan.firebase.model.User;

import java.util.HashMap;

public interface EditPaymentGroupView {
    void setPaymentGroupName(String value);

    void setTotalCost(int value);

    String getPaymentGroupName();

    String getTotalCost();

    void addedInvitedMember(Object invitedMember);

    void showErrorMessage(String message);

    void showPaymentGroupError(String message);

    void showTotalCostError(String message);

    void finishEdit();

    void showErrorDialog(String message);

    void showInvitedMember(HashMap<String, User> invitedMember);

    void loading(boolean loading);
}
