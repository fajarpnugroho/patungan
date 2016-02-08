package com.chefcode.android.patungan.ui.mycontact;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.Contact;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Contact> contactList;

    public MyContactAdapter() {}

    public void init(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContactViewHolder) {
            ContactViewHolder viewHolder = (ContactViewHolder) holder;
            viewHolder.populate(contactList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 :contactList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public Holder(@LayoutRes int resId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        }
    }

    public class ContactViewHolder extends Holder implements Checkable {

        @Bind(R.id.contact) TextView contactTextView;
        @Bind(R.id.checkbox) CheckBox checkBox;

        private TextAppearanceSpan contactNameTextAppearance;
        private TextAppearanceSpan contactNumberTextAppearance;

        private boolean checked;

        public ContactViewHolder(ViewGroup parent) {
            super(R.layout.view_contact_item, parent);

            ButterKnife.bind(this, itemView);

            contactNameTextAppearance = new TextAppearanceSpan(itemView.getContext(),
                    R.style.ContactName);
            contactNumberTextAppearance = new TextAppearanceSpan(itemView.getContext(),
                    R.style.ContactNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle();
                    checkBox.setChecked(checked);
                }
            });
        }


        public void populate(Contact contact) {
            SpannableString spannableString = new SpannableString(contact.name + "\n"
                    + contact.phoneNumber);

            spannableString.setSpan(contactNameTextAppearance, 0, contact.name.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(contactNumberTextAppearance, contact.name.length()+1,
                    spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            contactTextView.setText(spannableString, TextView.BufferType.SPANNABLE);
            contactTextView.setMovementMethod(new LinkMovementMethod());
        }

        @Override
        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        @Override
        public boolean isChecked() {
            return checked;
        }

        @Override
        public void toggle() {
            setChecked(!checked);
        }
    }
}
