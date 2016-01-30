package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.os.Bundle;
import android.widget.TextView;

import com.chefcode.android.patungan.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        text.setText("Hi");
        setContentView(text);
    }
}
