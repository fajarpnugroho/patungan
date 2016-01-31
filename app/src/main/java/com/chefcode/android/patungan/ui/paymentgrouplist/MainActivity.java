package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;

import java.util.HashMap;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // HeaderViewComponent headerViewComponent = (HeaderViewComponent) LayoutInflater.from(this)
        //        .inflate(R.layout.view_header_account_balance, null);

        ContentViewComponent contentViewComponent = (ContentViewComponent) LayoutInflater
                .from(this).inflate(R.layout.view_content_list, null);
        setContentView(contentViewComponent);
    }
}
