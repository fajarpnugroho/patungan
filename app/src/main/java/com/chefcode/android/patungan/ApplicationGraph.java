package com.chefcode.android.patungan;

import com.chefcode.android.patungan.ui.login.DialogLoginFragment;

public interface ApplicationGraph {
    void inject(PatunganApp app);

    void inject(DialogLoginFragment dialogLoginFragment);
}
