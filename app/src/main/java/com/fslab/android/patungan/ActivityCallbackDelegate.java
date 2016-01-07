package com.fslab.android.patungan;

public interface ActivityCallbackDelegate<V extends BaseView, P extends BasePresenter<V>> {
    P createPresenter();

    void attachView();

    void detachView();
}
