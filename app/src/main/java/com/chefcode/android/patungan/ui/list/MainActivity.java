package com.chefcode.android.patungan.ui.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.accounthistory.AccountHistoryActivity;
import com.chefcode.android.patungan.ui.detail.PaymentDetailActivity;
import com.chefcode.android.patungan.utils.Constants;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.header_container) FrameLayout headerContainer;
    @Bind(R.id.content_container) FrameLayout contentContainer;

    private MFPPush push;
    private MFPPushNotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();

        notificationListener = new MFPPushNotificationListener() {
            @Override
            public void onReceive(final MFPSimplePushNotification message) {
                Timber.v("Received push notif");
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Received a Push Notification")
                                .setMessage(message.getAlert())
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                })
                                .show();
                    }
                });
            }
        };

        push = MFPPush.getInstance();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setContent();

        if (push != null) {
            push.listen(notificationListener);
        }
    }

    private void setContent() {
        headerContainer.removeAllViews();
        contentContainer.removeAllViews();

        HeaderViewComponent headerViewComponent = (HeaderViewComponent) LayoutInflater.from(this)
                .inflate(R.layout.view_header_account_balance, headerContainer, false);
        headerContainer.addView(headerViewComponent);

        ContentViewComponent contentViewComponent = (ContentViewComponent) LayoutInflater.from(this)
                .inflate(R.layout.view_content_list, contentContainer, false);
        contentContainer.addView(contentViewComponent);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.button_create_payment_group)
    void onCreatePaymentGroupClick() {
        DialogNewPaymentGroup dialogNewPaymentGroup = DialogNewPaymentGroup
                .newInstance(encodedEmail);
        dialogNewPaymentGroup.show(getSupportFragmentManager(), "ShowDialogNewPaymentGroup");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                navigateToAccountHistory();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToAccountHistory() {
        Intent intent = new Intent(this, AccountHistoryActivity.class);
        startActivity(intent);
    }
}
