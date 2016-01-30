package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HeaderViewComponent headerViewComponent = (HeaderViewComponent) LayoutInflater.from(this)
                .inflate(R.layout.view_header_account_balance, null);

        setContentView(headerViewComponent);
    }
}
