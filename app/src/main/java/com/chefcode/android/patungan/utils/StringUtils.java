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
                + ",00";
    }

    public static int getNumberOfAccountBalance(String accountBalance) {
        String[] splitSpace = accountBalance.split(" ");
        String[] splitComma = splitSpace[1].split(",");
        String removeDot = splitComma[0].replace(".", "");
        return Integer.parseInt(removeDot);
    }

    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) { return ""; }
        phoneNumber = phoneNumber.replace("+62", "0");
        return phoneNumber;
    }

    public static String getPhoneNumberFromEncodedEmail(String paymentGroupOwner) {
        String[] tmp = paymentGroupOwner.split("@");
        return tmp[0];
    }
}
