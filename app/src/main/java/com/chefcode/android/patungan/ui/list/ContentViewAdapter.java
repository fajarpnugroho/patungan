package com.chefcode.android.patungan.ui.list;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

public class ContentViewAdapter extends FirebaseRecyclerViewAdapter<PaymentGroup, PaymentGroupViewHolder> {


    public ContentViewAdapter(Class<PaymentGroup> modelClass, int modelLayout,
                              Class<PaymentGroupViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(PaymentGroupViewHolder viewHolder, PaymentGroup model) {
        super.populateViewHolder(viewHolder, model);
        viewHolder.populate(model);
    }
}
