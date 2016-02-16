package com.chefcode.android.patungan.ui.list;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.StringUtils;
import com.chefcode.android.patungan.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PaymentGroupViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.owner) CircleImageView ownerPict;
    @Bind(R.id.payment_group) TextView paymentGroupName;
    @Bind(R.id.invoice) TextView invoiceText;
    @Bind(R.id.member) TextView memberText;
    @Bind(R.id.already_transfer) TextView transferText;

    private TextAppearanceSpan paymentGroupNameTextAppearance;
    private TextAppearanceSpan dateCreatedTextAppearance;

    public PaymentGroupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        paymentGroupNameTextAppearance = new TextAppearanceSpan(itemView.getContext(),
                R.style.PaymentGroupName);

        dateCreatedTextAppearance = new TextAppearanceSpan(itemView.getContext(),
                R.style.PaymentGroupCreatedDate);
    }

    public void populate(PaymentGroup paymentGroup) {
        // TODO change with real encoded email
        List<String> fake = new ArrayList<>();
        fake.add("b70979cdb6958d768e413eb2504ec009");
        fake.add("9cb69afa8d72c24d1ceaf63da11a4f6e");
        fake.add("0bb6883c2b66289c7816c0c6bb9192fe");
        fake.add("d3f1e22a5bff5f426ba960e4bbc7959a");

        String timeCreated = TimeUtils
                .convertTimestamp(paymentGroup.getTimestampCreatedLong());

        SpannableString paymentGroupHeaderText = new SpannableString(
                paymentGroup.getGroupName().toUpperCase() + "\n" + timeCreated);

        paymentGroupHeaderText.setSpan(paymentGroupNameTextAppearance,
                0, paymentGroup.getGroupName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        paymentGroupHeaderText.setSpan(dateCreatedTextAppearance,
                paymentGroup.getGroupName().length() + 1, paymentGroupHeaderText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        paymentGroupName.setText(paymentGroupHeaderText, TextView.BufferType.SPANNABLE);
        paymentGroupName.setMovementMethod(new LinkMovementMethod());

        invoiceText.setText(StringUtils.convertToRupiah(paymentGroup.getInvoice()));

        Picasso.with(itemView.getContext())
                .load(paymentGroup.getAvatarOwner())
                .into(ownerPict);

        memberText.setText(String.valueOf(fake.size()));

        transferText.setText("2");
    }
}
