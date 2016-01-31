package com.chefcode.android.patungan.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {
    public StringUtils() {
    }

    public static String encodedEmail(String email) {
        return email.replace(".", ",");
    }

    public static String decodeEmail(String email) {
        return email.replace(",", ".");
    }

    public static String convertToRupiah(int invoice) {
        String invoiceString = invoice + "";
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.CANADA);
        return "Rp "
                + rupiahFormat.format(Double.parseDouble(invoiceString)).replace(",", ".")
                + ", 00";
    }
}
