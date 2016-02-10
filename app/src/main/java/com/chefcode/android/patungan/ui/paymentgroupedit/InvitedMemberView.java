package com.chefcode.android.patungan.ui.paymentgroupedit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InvitedMemberView extends FrameLayout implements Checkable, View.OnClickListener {

    @Bind(R.id.root) FrameLayout root;
    @Bind(R.id.contact) TextView contactText;
    @Bind(R.id.checkbox) CheckBox checkBox;

    private boolean checked;
    private Listener listener;

    public InvitedMemberView(Context context) {
        this(context, null);
    }

    @SuppressLint("InflateParams")
    public InvitedMemberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_contact_item, this);
        ButterKnife.bind(this);
        checked = true;
        checkBox.setChecked(true);
        root.setOnClickListener(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void populate(User user) {
        String phoneNumber = user.getMsisdn();
        contactText.setText(phoneNumber);
        root.setTag(StringUtils.encodedEmail(phoneNumber + "@patungan.com"));
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    public void onClick(View v) {
        toggle();
        checkBox.setChecked(checked);
        listener.onItemClickListener((String) v.getTag());
    }

    public interface Listener {
        void onItemClickListener(String encodedMail);
    }
}
