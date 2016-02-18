package com.chefcode.android.patungan.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.User;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InvitedMemberViewImp extends FrameLayout implements InvitedMemberView {

    @Inject InvitedMemberPresenter presenter;

    @Bind(R.id.content_container) LinearLayout contentContainer;

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
            Injector.INSTANCE.getApplicationGraph().inject(this);
            ButterKnife.bind(this);
            presenter.init(this);
        }
    }

    public void loadInvitedMember(String paymentGroupId) {
        presenter.getListInvitedMember(paymentGroupId);
    }

    @Override
    public void showListInvitedMember(List<User> invitedMembers) {
        for (User user : invitedMembers) {
            InvitedMemberItemView view = new InvitedMemberItemView(getContext());
            view.populate(user.getProfilePict(), user.getMsisdn(), false);
            contentContainer.addView(view);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.removeValueEventListener();
        super.onDetachedFromWindow();
    }
}
