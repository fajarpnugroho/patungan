package com.chefcode.android.patungan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chefcode.android.patungan.utils.Constants;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    public String encodedEmail;

    @Inject SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);
        encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
    }
}
