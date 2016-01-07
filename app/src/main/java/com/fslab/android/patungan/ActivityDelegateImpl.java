package com.fslab.android.patungan;

import android.os.Bundle;

public class ActivityDelegateImpl<V extends BaseView, P extends BasePresenter<V>>
        implements ActivityDelegate {

    protected ActivityCallbackDelegate<V, P> delegateCallback;

    public ActivityDelegateImpl(ActivityCallbackDelegate<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("Activity must implement "
                    + ActivityCallbackDelegate.class);
        }
        this.delegateCallback = delegateCallback;
    }

    @Override
    public void onCreate(Bundle bundle) {
        delegateCallback.createPresenter();
        delegateCallback.attachView();
    }

    @Override
    public void onDestroy() {
        delegateCallback.detachView();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
