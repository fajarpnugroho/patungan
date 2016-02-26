package com.chefcode.android.patungan.ui.discussion;

import android.content.SharedPreferences;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.Discussion;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import javax.inject.Inject;

public class DiscussionPresenter {
    private DiscussionView view;

    private Firebase rootRef;
    private Firebase discussionRef;
    private Firebase paymentGroupDiscussinRef;
    private ValueEventListener paymentGroupDiscussionListener;

    private SharedPreferences sharedPreferences;

    @Inject
    public DiscussionPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void init(DiscussionView view) {
        this.view = view;
        this.rootRef = new Firebase(Constants.FIREBASE_BASE_URL);
        this.discussionRef = rootRef.child(Constants.FIREBASE_PAYMENT_GROUP_DISCUSSION_LOCATION);
    }

    public void loadMessagedthenSetAdater(String paymentGroupId) {
        this.paymentGroupDiscussinRef = discussionRef.child(paymentGroupId);
        Query paymentGroupDiscussionQuery = paymentGroupDiscussinRef.orderByKey();

        final DiscussionViewAdapter adapter = new DiscussionViewAdapter(Discussion.class,
                R.layout.view_discussion_item, DiscussionViewHolder.class,
                paymentGroupDiscussionQuery);
        view.getRecyclerView().setAdapter(adapter);

        this.paymentGroupDiscussionListener =
                paymentGroupDiscussinRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        view.getRecyclerView().smoothScrollToPosition(adapter.getItemCount());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    public void unregisterValueListener() {
        this.paymentGroupDiscussinRef.removeEventListener(paymentGroupDiscussionListener);
    }

    public void sendMessage(String paymentGroupId) {
        if (view.getMessage().isEmpty()) {
            return;
        }

        this.paymentGroupDiscussinRef = discussionRef.child(paymentGroupId);

        String phoneNumber = sharedPreferences.getString(Constants.MSISDN, "");

        HashMap<String, Object> timestamp = new HashMap<>();
        timestamp.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);

        Discussion discussion = new Discussion(phoneNumber, view.getMessage(), timestamp);
        this.paymentGroupDiscussinRef.push().setValue(discussion);

        countMessageIncrement();

        view.clearInputMessage();
    }

    private void countMessageIncrement() {
        int count = sharedPreferences.getInt(Constants.READ_MESSAGE, 0);
        sharedPreferences.edit().putInt(Constants.READ_MESSAGE, count+1).apply();
    }
}
