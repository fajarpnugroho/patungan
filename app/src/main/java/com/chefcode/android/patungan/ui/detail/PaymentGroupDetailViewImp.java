package com.chefcode.android.patungan.ui.detail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.utils.StringUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentGroupDetailViewImp extends FrameLayout implements PaymentGroupDetailView {

    @Inject PaymentGroupPresenter presenter;

    @Bind(R.id.payment_group_name) TextView paymentGroupText;
    @Bind(R.id.bucket) TextView bucketText;
    @Bind(R.id.invoice) TextView invoiceText;

    private TextAppearanceSpan labelTextAppearance;
    private TextAppearanceSpan priceNumberTextAppearance;

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
        }
    }

    public void loadPaymentDetail(String paymentGroupId) {
        presenter.getPaymentDetail(paymentGroupId);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.removeListener();
        super.onDetachedFromWindow();
    }

    @Override
    public void populate(PaymentGroup paymentGroup) {

        paymentGroupText.setText(paymentGroup.getGroupName());

        String bucketString = StringUtils.convertToRupiah(paymentGroup.getBucket());
        String invoiceString = StringUtils.convertToRupiah(paymentGroup.getInvoice());

        bucketText.setText(setAppearanceBucketText("Dana terkumpul\n", bucketString),
                TextView.BufferType.SPANNABLE);
        bucketText.setMovementMethod(new LinkMovementMethod());

        invoiceText.setText(setAppearanceBucketText("Total biaya\n", invoiceString));
        invoiceText.setMovementMethod(new LinkMovementMethod());
    }

    private SpannableString setAppearanceBucketText(String label, String number) {
        SpannableString bucket = new SpannableString(label + number);

        bucket.setSpan(labelTextAppearance, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bucket.setSpan(priceNumberTextAppearance, label.length(), bucket.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return bucket;
    }
}
