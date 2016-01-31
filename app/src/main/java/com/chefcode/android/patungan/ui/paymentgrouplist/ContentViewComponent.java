package com.chefcode.android.patungan.ui.paymentgrouplist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.widget.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentViewComponent extends FrameLayout implements ContentView {
    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    @Inject ContentViewPresenter presenter;

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
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL_LIST));

            presenter.init(this);
            presenter.loadContentThenSetAdapter();
        }
    }

    @OnClick(R.id.button_create_payment_group)
    void onCreatePaymentGroupClick() {
        presenter.addPaymentGroup();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
