package com.chefcode.android.patungan.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public final class TimeUtils {
    private static final long TIME_MILISECOND = 1000;

    private TimeUtils() {}

    public static String convertTimestamp(long timestamp) {
        /*if (timestamp > 0) {
            long currentTimestamp = System.currentTimeMillis();
            timestamp = timestamp * TIME_MILISECOND;
            return String.valueOf(DateUtils.getRelativeTimeSpanString(timestamp, currentTimestamp,
                DateUtils.DAY_IN_MILLIS));
        }
        return "";*/
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        return DateFormat.format("dd/MMM/yyyy, hh:mm:ss a", cal).toString();
    }
}
