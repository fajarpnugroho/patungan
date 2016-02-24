package com.chefcode.android.patungan.ui.list;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.StringUtils;
import com.chefcode.android.patungan.utils.TimeUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentGroupViewHolder extends RecyclerView.ViewHolder {

    @Inject SharedPreferences sharedPreferences;

    @Bind(R.id.owner) TextView ownerName;
    @Bind(R.id.payment_group) TextView paymentGroupName;
    @Bind(R.id.invoice) TextView invoiceText;

    private TextAppearanceSpan ownerTextApperance;
    private TextAppearanceSpan dateCreatedTextAppearance;
    private ContentViewAdapter.ItemCLickListener itemClickListener;

    public PaymentGroupViewHolder(View itemView) {
        super(itemView);

        Injector.INSTANCE.getApplicationGraph().inject(this);

        ButterKnife.bind(this, itemView);

        ownerTextApperance = new TextAppearanceSpan(itemView.getContext(),
                R.style.OwnerPaymentGroup);

        dateCreatedTextAppearance = new TextAppearanceSpan(itemView.getContext(),
                R.style.PaymentGroupCreatedDate);
    }

    public void populate(final PaymentGroup paymentGroup, final String groupKey) {
        // TODO change with real encoded email

        String owner = StringUtils.getPhoneNumberFromEncodedEmail(paymentGroup.getOwner())
                .toUpperCase();

        if (owner.equals(sharedPreferences.getString(Constants.MSISDN, ""))) {
            owner = "You";
        }

        String timeCreated = TimeUtils
                .convertTimestamp(paymentGroup.getTimestampCreatedLong());

        SpannableString ownerPaymentGroupString = new SpannableString(owner + "\n" + timeCreated);

        ownerPaymentGroupString.setSpan(ownerTextApperance,
                0, owner.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ownerPaymentGroupString.setSpan(dateCreatedTextAppearance,
                owner.length() + 1, ownerPaymentGroupString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ownerName.setText(ownerPaymentGroupString, TextView.BufferType.SPANNABLE);
        ownerName.setMovementMethod(new LinkMovementMethod());

        paymentGroupName.setText(paymentGroup.getGroupName().toUpperCase());
        invoiceText.setText(StringUtils.convertToRupiah(paymentGroup.getInvoice()));


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener == null) {
                    throw new IllegalStateException("Must set item click listener");
                }
                itemClickListener.openDetail(groupKey);
            }
        });
    }


    public void setItemClickListener(ContentViewAdapter.ItemCLickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
