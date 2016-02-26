package com.chefcode.android.patungan.ui.discussion;

import com.chefcode.android.patungan.firebase.model.Discussion;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

public class DiscussionViewAdapter extends
        FirebaseRecyclerViewAdapter<Discussion, DiscussionViewHolder> {


    public DiscussionViewAdapter(Class<Discussion> modelClass, int modelLayout,
                                 Class<DiscussionViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(DiscussionViewHolder viewHolder, Discussion model,
                                      int position) {
        super.populateViewHolder(viewHolder, model, position);
        viewHolder.populate(model);
    }
}
