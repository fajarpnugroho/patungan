package com.chefcode.android.patungan.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Holder extends RecyclerView.ViewHolder {
    public Holder(int layoutResId, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false));
    }
}
