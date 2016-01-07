package com.fslab.android.patungan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>>
        extends AppCompatActivity implements ActivityCallbackDelegate<V, P>, BaseView {

    protected ActivityDelegate<V, P> delegate;

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityDelegate().onResume();
    }

    @Override
    protected void onPause() {
        getActivityDelegate().onPause();

        super.onPause();
    }

    @Override
    protected void onStop() {
        getActivityDelegate().onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        getActivityDelegate().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        getActivityDelegate().onRestart();
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getActivityDelegate().onSaveInstanceState(outState);
    }

    public abstract P createPresenter();

    @SuppressWarnings("unchecked")
    public ActivityDelegate<V, P> getActivityDelegate() {
        if (delegate == null) {
            delegate = new ActivityDelegateImpl(this);
        }

        return delegate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void attachView() {
        presenter.attachView((V) this);
    }

    @Override
    public void detachView() {
        presenter.detachView();
    }
}
