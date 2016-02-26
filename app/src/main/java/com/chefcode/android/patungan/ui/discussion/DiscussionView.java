package com.chefcode.android.patungan.ui.discussion;

import android.support.v7.widget.RecyclerView;

public interface DiscussionView {
    RecyclerView getRecyclerView();

    String getMessage();

    void clearInputMessage();
}
