package com.chefcode.android.patungan.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberViewContainer extends LinearLayout {

    public MemberViewContainer(Context context) {
        this(context, null);
    }

    public MemberViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void populate(List<String> listMember) {
        removeAllViews();

        if (listMember.size() <= 0) {
            return;
        }

        for (int i = 0; i < listMember.size(); i++) {
            if (i >= 5 ) {
                break;
            }
            addedCircleImageview(listMember.get(i));
        }
    }

    @SuppressLint("InflateParams")
    private void addedCircleImageview(String member) {

        CircleImageView circleImageView = (CircleImageView) LayoutInflater.from(getContext())
                .inflate(R.layout.view_circleimageview, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 10, 0);
        circleImageView.setLayoutParams(layoutParams);

        String imageUrl = String.format(Constants.DEFAULT_PROFILE_IMAGES, member);

        Picasso.with(getContext())
                .load(imageUrl)
                .into(circleImageView);

        addView(circleImageView);
    }
}
