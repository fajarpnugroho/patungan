package com.chefcode.android.patungan.ui.paymentgroupedit;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ConfirmDialogFragment extends DialogFragment {

    public static final String ARG_MESSAGE = "arg_message";
    private String message;

    public ConfirmDialogFragment() {}

    public static ConfirmDialogFragment newInstance() {
        Bundle args = new Bundle();

        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        message = getArguments().getString(ARG_MESSAGE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString(ARG_MESSAGE));
        return builder.create();
    }
}
