package com.example.fasterpro11;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // Define date format
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Method to convert timestamp to string
    public static String dateToString(long timestamp) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }
}
