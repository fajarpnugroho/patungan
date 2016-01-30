package com.chefcode.android.patungan.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.widget.LoadingView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLoginFragment extends DialogFragment implements DialogLoginView {

    @Bind(R.id.login_container) LinearLayout loginContainer;
    @Bind(R.id.edit_text_phone) EditText editTextPhone;
    @Bind(R.id.edit_text_password) EditText editTextPassword;
    @Bind(R.id.view_loading) LoadingView loading;

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
    @SuppressLint("InflateParams")
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
    void onBtnLoginClick(View view) {
        // Close keyboard when click login button
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
    public void phoneNumberError() {
        editTextPhone.setError("Phone number is empty!");
        editTextPhone.requestFocus();
    }

    @Override
    public void passwordError() {
        editTextPassword.setError("Password is empty!");
        editTextPassword.requestFocus();
    }

    @Override
    public void onLogin(boolean login) {
        if (login) {
            loginContainer.setVisibility(View.GONE);
            loading.show();
        } else {
            loginContainer.setVisibility(View.VISIBLE);
            loading.hide();
        }
    }

    @Override
    public void dismissDialogLogin() {
        dismiss();
    }

    @Override
    public void showLoginContainer(boolean show) {
        if (show) {
            loginContainer.setVisibility(View.VISIBLE);
        } else {
            loginContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToastError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDialogCancelable(boolean canceled) {
        setCancelable(canceled);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();

        if (activity instanceof DialogOnDismissListener) {
            ((DialogOnDismissListener) activity).handleDialogClose(dialog);
        }
    }
}
