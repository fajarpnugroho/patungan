package com.chefcode.android.patungan.ui.paymentgroupedit;

import android.os.Bundle;
import android.widget.TextView;

import com.chefcode.android.patungan.BaseActivity;

public class EditPaymentGroupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("hi, " + encodedEmail);

        setContentView(textView);
    }
}
