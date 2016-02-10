package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.paymentgroupedit.EditPaymentGroupActivity;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DialogNewPaymentGroup extends DialogFragment implements DialogNewPaymentGroupView {

    @Bind(R.id.payment_group_name) EditText paymentGroupNameEdit;

    @Inject DialogNewPaymentGroupPresenter presenter;

    private String encodedEmail;

    public DialogNewPaymentGroup() {}

    public static DialogNewPaymentGroup newInstance(String encodedEmail) {
        Bundle args = new Bundle();
        args.putString(Constants.ENCODED_EMAIL, encodedEmail);

        DialogNewPaymentGroup fragment = new DialogNewPaymentGroup();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);
        presenter.init(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        encodedEmail = getArguments().getString(Constants.ENCODED_EMAIL, "");
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                R.style.CustomTheme_Dialog);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_payment_group_edit, null);

        ButterKnife.bind(this, view);

        paymentGroupNameEdit.requestFocus();

        builder.setView(view).setPositiveButton(getActivity()
                .getString(R.string.label_dialog_create), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.addPaymentGroup(encodedEmail);
            }
        }).setNegativeButton(getActivity().getString(R.string.label_dialog_cancel),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public String getPaymentGroupName() {
        return paymentGroupNameEdit.getText().toString();
    }

    @Override
    public void showPaymentGroupNameError() {
        paymentGroupNameEdit.setError(getActivity().getString(R.string.message_error_empty));
    }

    @Override
    public void addPaymentGroupNameDone(Firebase pushId) {
        navigateToEditPaymentGroupDetail(pushId.getKey());
    }

    private void navigateToEditPaymentGroupDetail(String key) {
        Intent intent = new Intent(getActivity(), EditPaymentGroupActivity.class);
        intent.putExtra(Constants.PAYMENT_GROUP_ID, key);
        startActivity(intent);
        dismiss();
    }
}
