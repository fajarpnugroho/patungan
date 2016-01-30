package com.chefcode.android.patungan;

import com.chefcode.android.patungan.ui.login.DialogLoginFragment;
import com.chefcode.android.patungan.ui.paymentgrouplist.HeaderViewComponent;

public interface ApplicationGraph {
    void inject(PatunganApp app);

    void inject(DialogLoginFragment dialogLoginFragment);

    void inject(HeaderViewComponent headerViewComponent);
}
