package com.chefcode.android.patungan.ui.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class ListScrollListener extends RecyclerView.OnScrollListener {

    private boolean loading = false;
    private int previousTotal = 0;
    private int current_page = 1;

    public ListScrollListener() {
        super();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        int visibleThreshold = 5;

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {

            current_page++;
            loadMore(current_page);
            loading = true;
        }

    }

    public abstract void loadMore(int page);
}
