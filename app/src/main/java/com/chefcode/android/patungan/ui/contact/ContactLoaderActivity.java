package com.chefcode.android.patungan.ui.contact;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.PaymentGroup;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.ui.widget.DividerItemDecoration;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.ContactQuery;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ContactLoaderActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, ContactLoaderAdapter.Listener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.contact_list) RecyclerView contactList;
    @Bind(R.id.edit_text_contact_name) EditText contactNameEdit;

    @Inject ContactLoaderPresenter presenter;
    @Inject SharedPreferences preferences;

    private ContactLoaderAdapter adapter;
    private String keyword;
    private String paymentGroupId;

    private Firebase activePaymentGroupRef;
    private ValueEventListener activePaymentGroupListener;
    private PaymentGroup activePaymentGroup;

    private Firebase invitedMemberRef;
    private ValueEventListener invitedMemberListener;
    private HashMap<String, User> invitedMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.INSTANCE.getApplicationGraph().inject(this);

        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        adapter = new ContactLoaderAdapter(this);

        setToolbar();
        setContent();

        getSupportLoaderManager().initLoader(ContactQuery.QUERY_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.init();
        handleIntent();
    }

    private void handleIntent() {
        if (getIntent().getExtras() == null) {
            finish();
            return;
        }

        Bundle bundle = getIntent().getExtras();
        paymentGroupId = bundle.getString(Constants.PAYMENT_GROUP_ID, "");

        // get object payment group
        activePaymentGroupRef = new Firebase(Constants.FIREBASE_PAYMENT_GROUP_URL)
                .child(encodedEmail).child(paymentGroupId);

        activePaymentGroupListener = activePaymentGroupRef
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        activePaymentGroup = dataSnapshot.getValue(PaymentGroup.class);
                        presenter.setActivePaymentGroup(activePaymentGroup);
                        if (activePaymentGroup == null) {
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Timber.e(firebaseError.getMessage());
                    }
                });

        // get list of invited member
        invitedMemberRef = new Firebase(Constants.FIREBASE_INVITED_MEMBER_URL)
                .child(paymentGroupId);
        invitedMemberListener = invitedMemberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                invitedMember = new HashMap<>();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    invitedMember.put(user.getKey(), user.getValue(User.class));
                }
                presenter.setListInvitedMember(invitedMember);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Timber.e(firebaseError.getMessage());
            }
        });
    }

    @Override
    protected void onPause() {
        invitedMemberRef.removeEventListener(invitedMemberListener);
        super.onPause();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Must implement toolbar");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_invite_member);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contactList.setLayoutManager(linearLayoutManager);
        contactList.setAdapter(adapter);
        contactList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        contactNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 ){
                    keyword = s.toString();
                    filterByName();
                } else {
                    keyword = null;
                    filterByName();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterByName() {
        getSupportLoaderManager().restartLoader(ContactQuery.QUERY_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // If this is the loader for finding contacts in the Contacts Provider
        // (the only one supported)
        if (id == ContactQuery.QUERY_ID) {
            Uri contentUri;

            // There are two types of searches, one which displays all contacts and
            // one which filters contacts by a search query. If mSearchTerm is set
            // then a search query has been entered and the latter should be used.

            if (keyword == null) {
                // Since there's no search string, use the content URI that searches the entire
                // Contacts table
                contentUri = ContactQuery.CONTENT_URI;
            } else {
                // Since there's a search string, use the special content Uri that searches the
                // Contacts table. The URI consists of a base Uri and the search string.
                contentUri =
                        Uri.withAppendedPath(ContactQuery.FILTER_URI, Uri.encode(keyword));
            }

            // Returns a new CursorLoader for querying the Contacts table. No arguments are used
            // for the selection clause. The search string is either encoded onto the content URI,
            // or no contacts search string is used. The other search criteria are constants. See
            // the ContactsQuery interface.
            return new CursorLoader(this,
                    contentUri,
                    ContactQuery.PROJECTION,
                    ContactQuery.SELECTION,
                    null,
                    ContactQuery.SORT_ORDER);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == ContactQuery.QUERY_ID) {
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == ContactQuery.QUERY_ID) {
            // When the loader is being reset, clear the cursor from the adapter. This allows the
            // cursor resources to be freed.
            adapter.swapCursor(null);
        }
    }

    @Override
    public void invitedMember(boolean invited, String phoneNumber) {
        presenter.updateUserData(invited, paymentGroupId, phoneNumber);
    }
}
