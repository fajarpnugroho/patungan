package com.fslab.android.patungan;

import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>>
        extends AppCompatActivity {

    protected P presenter;

    public abstract P createPresenter();
}
