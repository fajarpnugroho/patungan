package com.chefcode.android.patungan.ui.discussion;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.widget.DividerItemDecoration;
import com.chefcode.android.patungan.utils.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscussionActivity extends BaseActivity implements DiscussionView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.contact_list) RecyclerView messageList;
    @Bind(R.id.edit_text_message) EditText messageEditText;

    @Inject DiscussionPresenter presenter;

    private String paymentGroupId;
    private String paymentGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);

        setContentView(R.layout.activity_discuss);
        ButterKnife.bind(this);

        handleExtra();

        presenter.init(this);

        setToolbar();
        setContent();
    }

    private void handleExtra() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            finish();
            return;
        }

        paymentGroupId = bundle.getString(Constants.PAYMENT_GROUP_ID);
        paymentGroupName = bundle.getString(Constants.EXTRA_PAYMENT_GROUP_NAME);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadMessagedthenSetAdater(paymentGroupId);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(paymentGroupName);
    }

    private void setContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageList.setLayoutManager(linearLayoutManager);
        messageList.setAdapter(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return messageList;
    }

    @Override
    public String getMessage() {
        return messageEditText.getText().toString().trim();
    }

    @Override
    public void clearInputMessage() {
        messageEditText.getText().clear();
    }

    @OnClick(R.id.btn_send_message)
    void onBtnSendMessageClick() {
        presenter.sendMessage(paymentGroupId);
    }
}
