package com.fslab.android.patungan;

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
