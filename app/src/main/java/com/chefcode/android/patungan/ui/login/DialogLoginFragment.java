package com.chefcode.android.patungan.ui.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLoginFragment extends DialogFragment implements DialogLoginView {

    @Bind(R.id.edit_text_phone) EditText editTextPhone;
    @Bind(R.id.edit_text_password) EditText editTextPassword;

    @Inject DialogLoginPresenter presenter;

    public DialogLoginFragment() {}

    public static DialogLoginFragment newInstance() {
        Bundle args = new Bundle();

        DialogLoginFragment fragment = new DialogLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        presenter.init(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                R.style.CustomTheme_Dialog);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_login, null);
        ButterKnife.bind(this, rootView);
        builder.setView(rootView);

        return builder.create();
    }

    @OnClick(R.id.button_login)
    void onBtnLoginClick() {
        presenter.loginMandiriEcash();
    }

    @Override
    public String getInputPhoneNumber() {
        return editTextPhone.getText().toString().trim();
    }

    @Override
    public String getInputPassword() {
        return editTextPassword.getText().toString().trim();
    }

    @Override
    public boolean isPhoneNumberEmpty() {
        return getInputPhoneNumber().isEmpty() || getInputPhoneNumber().length() < 0;
    }

    @Override
    public boolean isPasswordEmpty() {
        return getInputPassword().isEmpty() || getInputPassword().length() < 0;
    }

    @Override
    public void phoneNumberError() {
        editTextPhone.setError("Phone number is empty!");
        editTextPhone.requestFocus();
    }

    @Override
    public void passwordError() {
        editTextPassword.setError("Password is empty!");
        editTextPassword.requestFocus();
    }
}
