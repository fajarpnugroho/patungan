package com.fslab.android.patungan;

import com.fslab.android.patungan.login.LoginPresenterImp;

public interface ApplicationGraph {
    void inject(PatunganApp app);

    void inject(LoginPresenterImp loginPresenterImp);
}
