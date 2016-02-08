package com.chefcode.android.patungan.ui.paymentgroupedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.contact.ContactLoaderActivity;
import com.chefcode.android.patungan.ui.widget.ErrorDialogFragment;
import com.chefcode.android.patungan.utils.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPaymentGroupActivity extends BaseActivity implements EditPaymentGroupView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edit_text_payment_group_name) EditText paymentGroupNameEdit;
    @Bind(R.id.edit_text_total_cost) EditText totalCostEdit;
    @Bind(R.id.invite_member_container) LinearLayout iniviteMemberContainer;

    @Inject EditPaymentGroupPresenter presenter;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);
        setContentView(R.layout.activity_payment_group_edit);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        paymentGroupNameEdit.requestFocus();

        bundle = getIntent().getExtras();
        setToolbar();

        presenter.init(this);
    }

    @OnClick(R.id.button_invite_member)
    void onInviteMemberClick() {
        Intent intent = new Intent(this, ContactLoaderActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bundle == null) {
            return;
        }
        String pushId = bundle.getString(Constants.PUSH_ID, null);
        presenter.valueListenerPaymentGroup(pushId);
    }

    @Override
    protected void onPause() {
        presenter.removeValueListener();
        super.onPause();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_done:
                presenter.saveEditedPaymentGroup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        totalCostEdit.setText("");
        presenter.deleteGroupIfNotComplete();
    }

    @Override
    public void setPaymentGroupName(String value) {
        paymentGroupNameEdit.setText(value);
    }

    @Override
    public void setTotalCost(int value) {
        if (value <= 0) return;
        totalCostEdit.setText(String.valueOf(value));
    }

    @Override
    public String getPaymentGroupName() {
        return paymentGroupNameEdit.getText().toString();
    }

    @Override
    public String getTotalCost() {
        return totalCostEdit.getText().toString();
    }

    @Override
    public void addedInvitedMember(Object invitedMember) {

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPaymentGroupError(String message) {
        paymentGroupNameEdit.requestFocus();
        paymentGroupNameEdit.setError(message);
    }

    @Override
    public void showTotalCostError(String message) {
        totalCostEdit.requestFocus();
        totalCostEdit.setError(message);
    }

    @Override
    public void finishEdit() {
        finish();
    }

    @Override
    public void showErrorDialog(String message) {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(message);
        fragment.show(getSupportFragmentManager(), "ShowErrorDialog");
    }
}
