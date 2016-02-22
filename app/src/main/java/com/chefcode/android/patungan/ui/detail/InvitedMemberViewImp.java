package com.chefcode.android.patungan.ui.detail;

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

    @Bind(R.id.content_container) LinearLayout contentContainer;

    @Inject InvitedMemberPresenter presenter;
    private List<User> paidMember;
    private List<User> invitedMembers;

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

    public void loadPaidMember(String paymentGroupId) {
        presenter.loadPaidMember(paymentGroupId);
    }

    @Override
    public void listPaidMember(List<User> paidMember) {
        this.paidMember = paidMember;
        reloadView();
    }

    private void reloadView() {
        contentContainer.removeAllViews();
        if (this.invitedMembers != null && this.paidMember != null) {
            for (User user : invitedMembers) {
                InvitedMemberItemView view = new InvitedMemberItemView(getContext());
                view.populate(
                        user.getProfilePict(),
                        user.getMsisdn(),
                        alreadyPaid(user.getEmail()));
                contentContainer.addView(view);
            }
        }
    }

    private boolean alreadyPaid(String email) {
        for (User user : paidMember) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showListInvitedMember(List<User> invitedMembers) {
        this.invitedMembers = invitedMembers;
        reloadView();
    }



}
