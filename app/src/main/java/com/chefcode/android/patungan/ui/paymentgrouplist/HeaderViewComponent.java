package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.widget.LoadingView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderViewComponent extends FrameLayout implements HeaderView {

    @Bind(R.id.text_account) TextView account;
    @Bind(R.id.text_account_balance) TextView accountBalance;
    @Bind(R.id.view_loading) LoadingView loadingView;

    @Inject HeaderViewPresenster presenster;

    public HeaderViewComponent(Context context) {
        this(context, null);
    }

    public HeaderViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            Injector.INSTANCE.getApplicationGraph().inject(this);
            ButterKnife.bind(this);
            presenster.init(this);
            presenster.setAccountName();
            presenster.getBalanceInquiryUser();
        }
    }


    @Override
    public void onLoading(boolean loading) {
        if (loading) {
            loadingView.show();
        } else {
            loadingView.hide();
        }
    }

    @Override
    public void showAccountName(String name) {
        account.setText(name);
    }

    @Override
    public void showBalanceInquiryUser(String balance) {
        accountBalance.setVisibility(VISIBLE);
        accountBalance.setText(balance);
    }

    @Override
    public void showErrorMessage(String message) {
        accountBalance.setVisibility(VISIBLE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
