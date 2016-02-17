package com.chefcode.android.patungan;

import com.chefcode.android.patungan.ui.contact.ContactLoaderActivity;
import com.chefcode.android.patungan.ui.detail.PaymentGroupDetailViewImp;
import com.chefcode.android.patungan.ui.login.DialogLoginFragment;
import com.chefcode.android.patungan.ui.edit.EditPaymentGroupActivity;
import com.chefcode.android.patungan.ui.list.ContentViewComponent;
import com.chefcode.android.patungan.ui.list.DialogNewPaymentGroup;
import com.chefcode.android.patungan.ui.list.HeaderViewComponent;

public interface ApplicationGraph {
    void inject(PatunganApp app);

    void inject(DialogLoginFragment dialogLoginFragment);

    void inject(HeaderViewComponent headerViewComponent);

    void inject(ContentViewComponent contentViewComponent);

    void inject(BaseActivity baseActivity);

    void inject(DialogNewPaymentGroup dialogNewPaymentGroup);

    void inject(EditPaymentGroupActivity activity);

    void inject(ContactLoaderActivity activity);

    void inject(PaymentGroupDetailViewImp paymentGroupDetailViewImp);
}
