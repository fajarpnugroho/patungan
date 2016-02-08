package com.chefcode.android.patungan.ui.mycontact;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.chefcode.android.patungan.firebase.model.Contact;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyContactPresenter {
    private MyContactView view;
    private Context context;
    private List<Contact> myListContact;

    @Inject
    public MyContactPresenter(Context context) {
        this.context = context;
    }

    public void init(MyContactView view) {
        this.view = view;
        this.myListContact = new ArrayList<>();
    }

    public void displayContact(@Nullable String keyword) {
        view.onLoading(true);
        new LoadContactAsyncTask().execute(keyword);
    }

    public void filterContact(CharSequence query) {
        if (myListContact == null) {
            return;
        }

        if (query.length() > 0) {
            List<Contact> filteredList = filter(myListContact, query.toString());
            view.showListContact(filteredList);
        } else {
            view.showListContact(myListContact);
        }
    }

    private List<Contact> filter(List<Contact> list, String query) {
        query = query.toLowerCase();

        List<Contact> filteredList = new ArrayList<>();

        for (Contact contact : list) {
            if (contact.name.toLowerCase().contains(query)) {
                filteredList.add(contact);
            }
        }

        return filteredList;
    }

    public class LoadContactAsyncTask extends AsyncTask<String, Void, List<Contact>> {

        @Override
        @SuppressLint("InlinedApi")
        protected List<Contact> doInBackground(String... params) {
            ContentResolver cr = context.getContentResolver();

            String sortOrder = ContactsContract.Contacts.DISPLAY_NAME +
                    " COLLATE LOCALIZED ASC";

            String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1 AND " +
                    ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";

            if (params[0] != null) {
                selection = "AND " + ContactsContract.Contacts.DISPLAY_NAME + "=" + "'%"
                        + params[0] + "%'";
            }

            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, selection, null, sortOrder);

            if (cur == null) {
                return null;
            }

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

                    String name = cur
                            .getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    if (Integer.parseInt(cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);

                        if (pCur == null) {
                            break;
                        }

                        while (pCur.moveToNext()) {

                            String phoneNo = pCur.getString(pCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds
                                            .Phone.NORMALIZED_NUMBER));

                            String phoneName = pCur.getString(pCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds
                                            .Phone.DISPLAY_NAME));

                            Contact contact = new Contact();
                            contact.setName(phoneName);
                            contact.setPhoneNumber(phoneNo);

                            myListContact.add(contact);
                        }

                        pCur.close();
                    }
                }
                cur.close();
            }

            return myListContact;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            super.onPostExecute(contacts);
            if (contacts != null) {
                view.onLoading(false);
                view.showListContact(contacts);
            }
        }
    }
}
