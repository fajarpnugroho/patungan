package com.chefcode.android.patungan.ui.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chefcode.android.patungan.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DialogSignupNoticeFragment extends DialogFragment {

    @Bind(R.id.content_container) LinearLayout container;

    public DialogSignupNoticeFragment() {}

    public static DialogSignupNoticeFragment newInstance() {
        Bundle args = new Bundle();

        DialogSignupNoticeFragment fragment = new DialogSignupNoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                R.style.CustomTheme_Dialog);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_normal, null);
        ButterKnife.bind(this, view);

        int[] message = new int[] {
            R.string.notice_sign_up_message_1,
                R.string.notice_sign_up_message_2,
                R.string.notice_sign_up_message_3,
                R.string.notice_sign_up_message_4,
                R.string.notice_sign_up_message_5,
                R.string.notice_sign_up_message_6
        };

        for (int itemMessage : message) {
            TextView textView = new TextView(getActivity());
            textView.setText(itemMessage);
            textView.setPadding(0, 2, 0, 2);
            textView.setTextSize(18);
            container.addView(textView);
        }

        container.getChildAt(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("http://mandiriecash.co.id");
            }
        });

        builder.setView(view);
        builder.setTitle(getString(R.string.notice_sign_up_title));
        builder.setPositiveButton(R.string.label_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(Intent.createChooser(intent,
                getActivity().getString(R.string.label_chooser_open_with)));
    }
}
