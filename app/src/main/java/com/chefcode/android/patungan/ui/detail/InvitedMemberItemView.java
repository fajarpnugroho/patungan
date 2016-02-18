package com.chefcode.android.patungan.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class InvitedMemberItemView extends LinearLayout {

    @Inject SharedPreferences sharedPreferences;

    @Bind(R.id.profile_image) CircleImageView profileImages;
    @Bind(R.id.phone_number_info) TextView phoneNumberInfo;

    private TextAppearanceSpan phoneNumberTextAppearance;
    private TextAppearanceSpan transferInfoTextAppearance;

    public InvitedMemberItemView(Context context) {
        this(context, null);
    }

    public InvitedMemberItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_invited_member_item, this);
        if (!isInEditMode()) {
            Injector.INSTANCE.getApplicationGraph().inject(this);
            ButterKnife.bind(this);

            phoneNumberTextAppearance = new TextAppearanceSpan(getContext(),
                    R.style.InvitedMemberPhone);
            transferInfoTextAppearance = new TextAppearanceSpan(getContext(),
                    R.style.TransferInfo);
        }
    }

    @SuppressLint("NewApi")
    public void populate(String profileImageUrl, String phoneNumber, boolean paid) {
        Picasso.with(getContext()).load(profileImageUrl).into(profileImages);

        if (Objects.equals(phoneNumber,
                sharedPreferences.getString(Constants.MSISDN, ""))) {
            phoneNumber = "You";
        }

        String info = "belum transfer";

        if (paid) {
            info = "sudah transfer";
        }

        SpannableString sb = new SpannableString(phoneNumber + "\n" + info);

        sb.setSpan(phoneNumberTextAppearance, 0, phoneNumber.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(transferInfoTextAppearance, phoneNumber.length(), sb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        phoneNumberInfo.setText(sb, TextView.BufferType.SPANNABLE);
        phoneNumberInfo.setMovementMethod(new LinkMovementMethod());
    }
}
