package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentGroupViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.payment_group_name) TextView paymentGroupName;
    @Bind(R.id.invoice) TextView invoiceText;
    @Bind(R.id.member_container) MemberViewContainer memberViewContainer;

    public PaymentGroupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(PaymentGroup paymentGroup) {
        paymentGroupName.setText(paymentGroup.getGroupName());
        invoiceText.setText(StringUtils.convertToRupiah(paymentGroup.getInvoice()));

        // TODO change with real encoded email
        List<String> fake = new ArrayList<>();
        fake.add("b70979cdb6958d768e413eb2504ec009");
        fake.add("9cb69afa8d72c24d1ceaf63da11a4f6e");
        fake.add("0bb6883c2b66289c7816c0c6bb9192fe");
        fake.add("d3f1e22a5bff5f426ba960e4bbc7959a");

        memberViewContainer.populate(fake);
    }
}
