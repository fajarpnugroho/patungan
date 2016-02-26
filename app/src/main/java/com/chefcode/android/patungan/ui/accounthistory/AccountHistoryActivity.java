package com.chefcode.android.patungan.ui.accounthistory;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.services.response.HistoryResponse;
import com.chefcode.android.patungan.ui.widget.ListScrollListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountHistoryActivity extends BaseActivity implements AccountHistoryView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.contact_list) RecyclerView historyList;
    @Bind(R.id.swipe_to_refresh) SwipeRefreshLayout swipeRefreshLayout;

    @Inject AccountHistoryPresenter presenter;

    private AccountHistoryAdapter adapter;
    private String page = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);

        setContentView(R.layout.activity_account_history);
        ButterKnife.bind(this);

        setToolbar();
        setContent();

        presenter.initView(this);
        presenter.loadAccountHistory(page);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_account_history);
    }

    private void setContent() {
        adapter = new AccountHistoryAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historyList.setLayoutManager(linearLayoutManager);
        historyList.setAdapter(adapter);

        historyList.addOnScrollListener(new ListScrollListener() {
            @Override
            public void loadMore(int page) {
                presenter.loadAccountHistory(String.valueOf(page));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadAccountHistory(page);
            }
        });
    }

    @Override
    public void showListHistory(HistoryResponse historyResponse) {
        adapter.initData(historyResponse.accountHistoryDetails);
    }

    @Override
    public void HandleError() {

    }

    @Override
    public void loading(boolean loading) {
        if (loading) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
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
}
