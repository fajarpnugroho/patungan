package com.chefcode.android.patungan.ui.list;

import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.PushUtils;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

public class ContentViewAdapter extends FirebaseRecyclerViewAdapter<PaymentGroup, PaymentGroupViewHolder> {


    private ItemCLickListener itemCLickListener;

    public ContentViewAdapter(Class<PaymentGroup> modelClass, int modelLayout,
                              Class<PaymentGroupViewHolder> viewHolderClass, Query ref,
                              ItemCLickListener itemCLickListener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.itemCLickListener = itemCLickListener;
    }

    @Override
    protected void populateViewHolder(PaymentGroupViewHolder viewHolder, PaymentGroup model,
                                      int position) {
        super.populateViewHolder(viewHolder, model, position);
        viewHolder.populate(model, getRef(position).getKey());
        PushUtils.subscribeTags(getRef(position).getKey());
        viewHolder.setItemClickListener(itemCLickListener);
    }

    public interface ItemCLickListener {
        void openDetail(String paymentGroupId);

        void openMemberPaymentGroupDialog(String paymentGroupId);

        void openPaidMemberDialog(String paymentGroupId);
    }
}
