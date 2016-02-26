package com.chefcode.android.patungan.ui.accounthistory;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.services.response.AccountHistoryDetail;
import com.chefcode.android.patungan.utils.StringUtils;
import com.chefcode.android.patungan.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountHistoryAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<AccountHistoryDetail> historyResponseList = new ArrayList<>();

    public void initData(List<AccountHistoryDetail> historyResponseList) {
        this.historyResponseList.addAll(historyResponseList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof HistoryViewHolder) {
            HistoryViewHolder historyViewHolder = (HistoryViewHolder) holder;
            AccountHistoryDetail accountHistoryDetail = historyResponseList.get(position);
            boolean isSend = StringUtils.getNumberOfAccountBalance(accountHistoryDetail.amount) < 0;
            historyViewHolder.bind(accountHistoryDetail, isSend);
        }
    }

    @Override
    public int getItemCount() {
        return historyResponseList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public Holder(@LayoutRes int resId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        }
    }

    public static class HistoryViewHolder extends Holder {

        @Bind(R.id.date) TextView dateText;
        @Bind(R.id.image_status) ImageView imageStatus;
        @Bind(R.id.description) TextView desctiptionText;
        @Bind(R.id.author_status) TextView authorStatus;
        @Bind(R.id.amount) TextView amountText;

        private TextAppearanceSpan authorNameTextAppearance ;

        public HistoryViewHolder(ViewGroup parent) {
            super(R.layout.view_history_item, parent);
            ButterKnife.bind(this, itemView);

            authorNameTextAppearance = new TextAppearanceSpan(itemView.getContext(), R.style.AuthorText);
        }

        public void bind(AccountHistoryDetail accountHistoryDetail, boolean isSend) {
            String text = "";
            String author = accountHistoryDetail.username;

            dateText.setText(accountHistoryDetail.transactionDate);

            if (isSend) {
                text = "Kirim e-cash ke";
                // sending
                imageStatus.setImageResource(R.drawable.ic_send);
            } else {
                text = "Terima e-cash dari";
                // received
                imageStatus.setImageResource(R.drawable.ic_receive);
            }

            desctiptionText.setText(accountHistoryDetail.description);

            SpannableString spannableString = new SpannableString(text
                    + "\n" + author);

            spannableString.setSpan(authorNameTextAppearance, text.length(),
                    spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            authorStatus.setText(spannableString, TextView.BufferType.SPANNABLE);
            authorStatus.setMovementMethod(new LinkMovementMethod());

            amountText.setText(accountHistoryDetail.amount.replace("-",""));

        }
    }
}
