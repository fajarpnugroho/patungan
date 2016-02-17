package com.chefcode.android.patungan.ui.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chefcode.android.patungan.R;

import butterknife.ButterKnife;

public class InvitedMemberViewImp extends FrameLayout implements InvitedMemberView {
    public InvitedMemberViewImp(Context context) {
        this(context, null);
    }

    public InvitedMemberViewImp(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_card_invited_member, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }
}
