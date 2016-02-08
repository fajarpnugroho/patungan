package com.chefcode.android.patungan.ui.mycontact;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chefcode.android.patungan.BaseActivity;
import com.chefcode.android.patungan.Injector;
import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.Contact;
import com.chefcode.android.patungan.ui.widget.DividerItemDecoration;
import com.chefcode.android.patungan.ui.widget.LoadingView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyContactActivity extends BaseActivity implements MyContactView {

    @Inject MyContactPresenter presenter;

    @Bind(R.id.contact_list) RecyclerView contactList;
    @Bind(R.id.view_loading) LoadingView loadingView;
    @Bind(R.id.edit_text_contact_name) EditText contactNameEdit;
    private MyContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationGraph().inject(this);

        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        adapter = new MyContactAdapter();
        setContent();

        presenter.init(this);
        presenter.displayContact(null);
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
                filterByName(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterByName(CharSequence s) {
        presenter.filterContact(s);
    }

    @Override
    public void onLoading(boolean loading) {
        if (loading) {
            loadingView.show();
        } else {
            loadingView.hide();
        }
    }

    @Override
    public void showListContact(List<Contact> contacts) {
        adapter.init(contacts);
        adapter.notifyDataSetChanged();
    }

}
