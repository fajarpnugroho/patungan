package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.SharedPreferences;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.Constants;
import com.firebase.client.Firebase;
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

    public void addPaymentGroup() {
        Firebase userPaymentGroupRef = paymentGroupRef.child(encodedMail);

        HashMap<String, Object> timestampCreated = new HashMap<>();
        timestampCreated.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);

        Random random = new Random();

        PaymentGroup paymentGroup = new PaymentGroup("Kado Ulang Tahun Ibu",
                "082143207721@patungan,com",
                random.nextInt(30000000),
                50000,
                timestampCreated);

        userPaymentGroupRef.push().setValue(paymentGroup);
    }

    public void loadContentThenSetAdapter() {
        Firebase userPaymentGroupRef = paymentGroupRef.child(encodedMail);

        ContentViewAdapter adapter =
                new ContentViewAdapter(PaymentGroup.class,
                        R.layout.view_content_list_item, PaymentGroupViewHolder.class,
                        userPaymentGroupRef);

        view.getRecyclerView().setAdapter(adapter);
    }
}
