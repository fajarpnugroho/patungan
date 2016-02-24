package com.chefcode.android.patungan.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.ui.widget.LoadingView;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.StringUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentGroupDetailViewImp extends FrameLayout implements PaymentGroupDetailView {

    @Inject PaymentGroupPresenter presenter;
    @Inject SharedPreferences sharedPreferences;

    @Bind(R.id.payment_group_name) TextView paymentGroupText;
    @Bind(R.id.bucket) TextView bucketText;
    @Bind(R.id.invoice) TextView invoiceText;
    @Bind(R.id.action_container) LinearLayout actionContainer;
    @Bind(R.id.button_tranfer) TextView buttonTransfer;

    private TextAppearanceSpan labelTextAppearance;
    private TextAppearanceSpan priceNumberTextAppearance;
    private TextAppearanceSpan ownerHighlightTextAppearance;
    private TextAppearanceSpan minPaymentHighlightTextAppearance;
    private String paymentGroupId;
    private List<User> invitedMembers;
    private List<User> paidMembers;

    public PaymentGroupDetailViewImp(Context context) {
        this(context, null);
    }

    public PaymentGroupDetailViewImp(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_card_payment_group_detail_content,
                this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            Injector.INSTANCE.getApplicationGraph().inject(this);
            ButterKnife.bind(this);
            presenter.init(this);

            labelTextAppearance = new TextAppearanceSpan(getContext(), R.style.Label);
            priceNumberTextAppearance = new TextAppearanceSpan(getContext(), R.style.PriceNumber);
            ownerHighlightTextAppearance = new TextAppearanceSpan(getContext(),
                    R.style.HightlightText);
            minPaymentHighlightTextAppearance = new TextAppearanceSpan(getContext(),
                    R.style.HightlightText);
        }
    }

    public void setPaymentGroupId(String paymentGroupId) {
        this.paymentGroupId = paymentGroupId;
    }

    public void setInvitedMembers(List<User> invitedMembers) {
        this.invitedMembers = invitedMembers;
    }

    public void setPaidMembers(List<User> paidMembers) {
        this.paidMembers = paidMembers;
    }

    @SuppressLint("NewApi")
    @Override
    public void populate(final PaymentGroup paymentGroup) {

        paymentGroupText.setText(paymentGroup.getGroupName());

        if (!Objects.equals(paymentGroup.getOwner(),
                sharedPreferences.getString(Constants.ENCODED_EMAIL, ""))) {

            String ownerString = StringUtils
                    .getPhoneNumberFromEncodedEmail(paymentGroup.getOwner());
            String minTransferString = StringUtils
                    .convertToRupiah(paymentGroup.getMinimumPayment());

            SpannableString bucketString = new SpannableString(ownerString
                    + " mengajukan " + minTransferString + countingInvitedMember());

            bucketString.setSpan(ownerHighlightTextAppearance, 0, ownerString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            bucketString.setSpan(minPaymentHighlightTextAppearance, ownerString.length() + 12,
                    ownerString.length() + 12 + minTransferString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            bucketText.setText(bucketString, TextView.BufferType.SPANNABLE);
            bucketText.setMovementMethod(new LinkMovementMethod());

            actionContainer.setVisibility(VISIBLE);

            if (havePaidPaymentGroup()) {
                actionContainer.removeAllViews();
            } else {
                buttonTransfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialog(paymentGroup);
                    }
                });
            }

        } else {
            String bucketString = StringUtils.convertToRupiah(paymentGroup.getBucket());
            bucketText.setText(setAppearanceBucketText("Dana terkumpul\n", bucketString),
                    TextView.BufferType.SPANNABLE);
            bucketText.setMovementMethod(new LinkMovementMethod());

            actionContainer.setVisibility(GONE);
        }

        String invoiceString = StringUtils.convertToRupiah(paymentGroup.getInvoice());

        invoiceText.setText(setAppearanceBucketText("Total biaya\n", invoiceString));
        invoiceText.setMovementMethod(new LinkMovementMethod());
    }

    private boolean havePaidPaymentGroup() {
        String encodedEmail = sharedPreferences.getString(Constants.ENCODED_EMAIL, "");
        for (User user : paidMembers) {
            if (encodedEmail.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private String countingInvitedMember() {
        String temp = " dari kamu";
        if (invitedMembers.size() > 3) {
            temp += " dan " + (invitedMembers.size()-1) + " orang lainnya";
        }
        return temp;
    }

    @Override
    public void errorTransfer(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successTransfer() {
        Toast.makeText(getContext(), "Transfer berhasil dilakukan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransfering(boolean transfering) {
        actionContainer.removeAllViews();
        if (transfering) {
            ProgressBar progressBar = new ProgressBar(getContext());
            actionContainer.addView(progressBar);
        }
    }

    private void showAlertDialog(final PaymentGroup paymentGroup) {
        AlertDialog.Builder passwordBuilder = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_password,
                null);
        passwordBuilder.setTitle("MASUKAN PIN ANDA");
        passwordBuilder.setView(view);
        passwordBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = (EditText) view.findViewById(R.id.edit_text_password);

                String myPassword = editText.getText().toString();
                presenter.transferToOwnerPaymentGroup(paymentGroupId,
                        paymentGroup.getMinimumPayment(),
                        paymentGroup.getOwner(),
                        "Bayar patungan " + paymentGroup.getGroupName(),
                        myPassword,
                        invitedMembers
                );
            }
        });
        passwordBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        passwordBuilder.show();
    }

    private SpannableString setAppearanceBucketText(String label, String number) {
        SpannableString bucket = new SpannableString(label + number);

        bucket.setSpan(labelTextAppearance, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bucket.setSpan(priceNumberTextAppearance, label.length(), bucket.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return bucket;
    }
}
