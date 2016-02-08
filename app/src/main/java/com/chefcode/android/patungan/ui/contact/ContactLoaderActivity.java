package com.chefcode.android.patungan.ui.contact;

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
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.ui.widget.DividerItemDecoration;
import com.chefcode.android.patungan.utils.ContactQuery;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ContactLoaderActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, ContactLoaderAdapter.Listener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.contact_list) RecyclerView contactList;
    @Bind(R.id.edit_text_contact_name) EditText contactNameEdit;

    private ContactLoaderAdapter adapter;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        adapter = new ContactLoaderAdapter(this);

        setToolbar();
        setContent();

        getSupportLoaderManager().initLoader(ContactQuery.QUERY_ID, null, this);
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
        if (invited) {
            Timber.i("INVITE " + phoneNumber);
        } else {
            Timber.i("UNINVITED " + phoneNumber);
        }
    }
}
