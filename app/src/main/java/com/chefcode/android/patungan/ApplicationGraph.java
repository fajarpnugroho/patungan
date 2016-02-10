package com.chefcode.android.patungan;

import com.chefcode.android.patungan.ui.contact.ContactLoaderActivity;
import com.chefcode.android.patungan.ui.login.DialogLoginFragment;
import com.chefcode.android.patungan.ui.paymentgroupedit.EditPaymentGroupActivity;
import com.chefcode.android.patungan.ui.paymentgrouplist.ContentViewComponent;
import com.chefcode.android.patungan.ui.paymentgrouplist.DialogNewPaymentGroup;
import com.chefcode.android.patungan.ui.paymentgrouplist.HeaderViewComponent;

public interface ApplicationGraph {
    void inject(PatunganApp app);

    void inject(DialogLoginFragment dialogLoginFragment);

    void inject(HeaderViewComponent headerViewComponent);

    void inject(ContentViewComponent contentViewComponent);

    void inject(BaseActivity baseActivity);

    void inject(DialogNewPaymentGroup dialogNewPaymentGroup);

    void inject(EditPaymentGroupActivity activity);

    void inject(ContactLoaderActivity activity);
}
