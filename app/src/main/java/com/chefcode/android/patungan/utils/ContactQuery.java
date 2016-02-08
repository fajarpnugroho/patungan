package com.chefcode.android.patungan.utils;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.provider.ContactsContract;

import com.chefcode.android.patungan.firebase.model.Contact;

public interface ContactQuery {
    // An identifier for the loader
    final static int QUERY_ID = 1;

    // A content URI for the Contacts table
    final static Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    // The search/filter query Uri
    final static Uri FILTER_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI;

    // The selection clause for the CursorLoader query. The search criteria defined here
    // restrict results to contacts that have a display name and are linked to visible groups.
    // Notice that the search on the string provided by the user is implemented by appending
    // the search string to CONTENT_FILTER_URI.
    @SuppressLint("InlinedApi")
    final static String SELECTION =
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "<>''" + " AND "
                    + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";

    // The desired sort order for the returned Cursor. In Android 3.0 and later, the primary
    // sort key allows for localization. In earlier versions. use the display name as the sort
    // key.
    @SuppressLint("InlinedApi")
    final static String SORT_ORDER = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    // The projection for the CursorLoader query. This is a list of columns that the Contacts
    // Provider should return in the Cursor.
    @SuppressLint("InlinedApi")
    final static String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER};

    // The query column numbers which map to each value in the projection
    final static int DISPLAY_NAME = 0;
    final static int NUMBER = 1;
}
