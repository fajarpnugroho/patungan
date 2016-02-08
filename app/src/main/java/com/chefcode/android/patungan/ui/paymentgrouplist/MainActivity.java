package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.mycontact.ContactLoaderActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.header_container) FrameLayout headerContainer;
    @Bind(R.id.content_container) FrameLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();
        setContent();
    }

    private void setContent() {
        headerContainer.removeAllViews();
        contentContainer.removeAllViews();

        HeaderViewComponent headerViewComponent = (HeaderViewComponent) LayoutInflater.from(this)
                .inflate(R.layout.view_header_account_balance, headerContainer, false);
        headerContainer.addView(headerViewComponent);

        ContentViewComponent contentViewComponent = (ContentViewComponent) LayoutInflater.from(this)
                .inflate(R.layout.view_content_list, contentContainer, false);
        contentContainer.addView(contentViewComponent);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.button_create_payment_group)
    void onCreatePaymentGroupClick() {
        DialogNewPaymentGroup dialogNewPaymentGroup = DialogNewPaymentGroup
                .newInstance(encodedEmail);
        dialogNewPaymentGroup.show(getSupportFragmentManager(), "ShowDialogNewPaymentGroup");
    }

}
