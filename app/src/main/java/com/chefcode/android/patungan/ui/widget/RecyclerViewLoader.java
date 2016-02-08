package com.chefcode.android.patungan.ui.widget;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewLoader<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>  {

    private Cursor cursor;

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Cursor cursor = getCursor(position);
        this.onBindViewHolder(holder, cursor);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public Cursor getCursor(int position) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.moveToPosition(position);
        }
        return cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);
}
