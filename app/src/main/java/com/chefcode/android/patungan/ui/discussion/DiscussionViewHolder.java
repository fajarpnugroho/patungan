package com.chefcode.android.patungan.ui.discussion;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.Discussion;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.TimeUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscussionViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.rootLinear) LinearLayout root;
    @Bind(R.id.message_container) FrameLayout messageContainer;
    @Bind(R.id.message) TextView message;
    @Bind(R.id.author) TextView authorAndDate;

    @Inject SharedPreferences sharedPreferences;

    private TextAppearanceSpan authorTextAppearance;
    private TextAppearanceSpan dateTextAppearance;

    public DiscussionViewHolder(View itemView) {
        super(itemView);
        Injector.INSTANCE.getApplicationGraph().inject(this);

        ButterKnife.bind(this, itemView);

        itemView.setPadding(32, 32, 32, 32);

        authorTextAppearance = new TextAppearanceSpan(itemView.getContext(), R.style.AuthorText);
        dateTextAppearance = new TextAppearanceSpan(itemView.getContext(), R.style.DateText);
    }

    public void populate(Discussion model) {
        String author = model.author;

        if (author.equals(sharedPreferences.getString(Constants.MSISDN, ""))) {
            author = "You";
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                    messageContainer.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            messageContainer.setLayoutParams(layoutParams);
            authorAndDate.setLayoutParams(layoutParams);

            messageContainer.setBackgroundResource(R.drawable.ic_my_chat_buble);
            message.setTextColor(Color.WHITE);
            
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                    messageContainer.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            messageContainer.setLayoutParams(layoutParams);
            authorAndDate.setLayoutParams(layoutParams);
        }

        message.setText(model.getMessage());

        long timestampCreated = model.getTimestampCreatedLong();



        SpannableString spannableString = new SpannableString(
          author + " - " + TimeUtils.convertTimestamp(timestampCreated));

        spannableString.setSpan(authorTextAppearance, 0, author.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(dateTextAppearance, author.length() + 3,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        authorAndDate.setText(spannableString, TextView.BufferType.SPANNABLE);
        authorAndDate.setMovementMethod(new LinkMovementMethod());
    }
}
