package com.chefcode.android.patungan.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.payment_group_container) PaymentGroupDetailViewImp paymentGroupDetailView;
    @Bind(R.id.invite_member_container) InvitedMemberViewImp invitedMemberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setupToolbar();
        setupContent();
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
        String paymentGroupId = bundle.getString(Constants.PAYMENT_GROUP_ID);
        paymentGroupDetailView.loadPaymentDetail(paymentGroupId);
        invitedMemberView.loadInvitedMember(paymentGroupId);
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
}
