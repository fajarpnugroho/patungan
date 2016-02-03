package com.chefcode.android.patungan.ui.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.chefcode.android.patungan.R;

public class ErrorDialogFragment extends DialogFragment {

    public static final String ARG_MESSAGE = "arg_message";
    private String message;

    public ErrorDialogFragment() {}

    public static ErrorDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);

        ErrorDialogFragment fragment = new ErrorDialogFragment();
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
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.label_button_ok),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
