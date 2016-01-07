package com.fslab.android.patungan;

import android.os.Bundle;

public interface ActivityDelegate<V extends BaseView, P extends BasePresenter<V>> {
    void onCreate(Bundle bundle);

    void onDestroy();

    void onPause();

    void onResume();

    void onStop();

    void onRestart();

    void onSaveInstanceState(Bundle outState);
}
