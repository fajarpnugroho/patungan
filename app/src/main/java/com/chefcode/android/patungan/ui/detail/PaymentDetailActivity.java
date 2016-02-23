package com.chefcode.android.patungan.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentDetailActivity extends BaseActivity implements PaymentDetailView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.payment_group_container) PaymentGroupDetailViewImp paymentGroupDetailView;
    @Bind(R.id.invite_member_container) InvitedMemberViewImp invitedMemberView;

    @Inject PaymentDetailPresenter presenter;

    private String paymentGroupId;
    private PaymentGroup paymentGroup;
    private List<User> invitedMembers;
    private List<User> paidMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        presenter.init(this);

        setupToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupContent();
    }

    @Override
    protected void onPause() {
        presenter.removeValueEventListener();
        super.onPause();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    private void setupContent() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            finish();
            return;
        }

        paymentGroupId = bundle.getString(Constants.PAYMENT_GROUP_ID);
        presenter.getPaymentDetail(paymentGroupId);
        presenter.getListInvitedMember(paymentGroupId);
        presenter.getPaidMember(paymentGroupId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void listInvitedMember(List<User> invitedMembers) {
        this.invitedMembers = invitedMembers;
        refreshContent();
    }

    @Override
    public void listPaidMember(List<User> paidMember) {
        this.paidMember = paidMember;
        refreshContent();
    }

    @Override
    public void paymentGroupDetail(PaymentGroup paymentGroup) {
        this.paymentGroup = paymentGroup;
        refreshContent();
    }

    private void refreshContent() {
        if (isNotNull(paymentGroup) && isNotNull(invitedMembers) && isNotNull(paidMember)) {
            paymentGroupDetailView.setPaymentGroupId(paymentGroupId);
            paymentGroupDetailView.setInvitedMembers(invitedMembers);
            paymentGroupDetailView.setPaidMembers(paidMember);
            paymentGroupDetailView.populate(paymentGroup);
        }

        if (isNotNull(invitedMembers) && isNotNull(paidMember)) {
            invitedMemberView.showListInvitedMember(invitedMembers);
            invitedMemberView.listPaidMember(paidMember);
        }
    }

    private boolean isNotNull(Object object) {
        return object != null;
    }
}
