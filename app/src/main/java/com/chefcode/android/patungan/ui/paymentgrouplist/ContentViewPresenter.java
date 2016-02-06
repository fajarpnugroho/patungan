package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.SharedPreferences;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;

import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

public class ContentViewPresenter {
    private ContentView view;
    private SharedPreferences sharedPreferences;
    private Firebase paymentGroupRef;
    private String encodedMail;

    @Inject
    public ContentViewPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void init(ContentView view) {
        this.view = view;
        this.paymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL);
        this.encodedMail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
    }

    public void loadContentThenSetAdapter() {
        Firebase userPaymentGroupRef = paymentGroupRef.child(encodedMail);
        Query orderedUserPaymentGroupRef = userPaymentGroupRef.orderByKey();

        ContentViewAdapter adapter =
                new ContentViewAdapter(PaymentGroup.class,
                        R.layout.view_content_list_item, PaymentGroupViewHolder.class,
                        orderedUserPaymentGroupRef);

        view.getRecyclerView().setAdapter(adapter);
    }
}
