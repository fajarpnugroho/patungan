package com.chefcode.android.patungan.ui.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class ProgressDialogFragment extends DialogFragment {
    private static final String ARG_MESSAGE = "message";

    public static ProgressDialogFragment newInstance(CharSequence message) {
        Bundle args = new Bundle();
        args.putCharSequence(ARG_MESSAGE, message);

        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    public static void show(CharSequence message, FragmentManager fragmentManager, String tag) {
        ProgressDialogFragment progressDialogFragment = ProgressDialogFragment.newInstance(message);
        progressDialogFragment.show(fragmentManager, tag);
    }

    public static void dismiss(FragmentManager fragmentManager, String tag) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            return;
        }
        DialogFragment dialogFragment = (DialogFragment) fragmentManager.findFragmentByTag(tag);
        dialogFragment.dismissAllowingStateLoss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence message = getArguments().getCharSequence(ARG_MESSAGE);

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(message);
        dialog.setCancelable(false);

        return dialog;
    }
}
