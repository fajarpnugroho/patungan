package com.chefcode.android.patungan.ui.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.detail.PaymentDetailActivity;
import com.chefcode.android.patungan.utils.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContentViewComponent extends FrameLayout implements ContentView {
    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    @Inject
    ContentViewPresenter presenter;

    public ContentViewComponent(Context context) {
        this(context, null);
    }

    public ContentViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            Injector.INSTANCE.getApplicationGraph().inject(this);
            ButterKnife.bind(this);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            presenter.init(this);
            presenter.loadContentThenSetAdapter(new ContentViewAdapter.ItemCLickListener() {
                @Override
                public void openDetail(String paymentGroupId) {
                    Intent intent = new Intent(getContext(), PaymentDetailActivity.class);
                    intent.putExtra(Constants.PAYMENT_GROUP_ID, paymentGroupId);
                    getContext().startActivity(intent);
                }

                @Override
                public void openMemberPaymentGroupDialog(String paymentGroupId) {
                    Toast.makeText(getContext(), "Member of " + paymentGroupId,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void openPaidMemberDialog(String paymentGroupId) {
                    Toast.makeText(getContext(), "Paid for " + paymentGroupId,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}