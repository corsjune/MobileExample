package edu.wgu.dmass13.c196.globals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Helpers {

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    public static Date ConvertStringToDate(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        Date returnValue;

        try {
            returnValue = sdf.parse(input);
        } catch (ParseException ex) {
            returnValue = null;
        }

        return returnValue;
    }

    public static String ConvertDateToString(Date input) {

        if (input != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            return sdf.format(input);
        } else {
            return null;
        }
    }

    public static Long ConvertDateToLong(Date input) {

        Long returnValue;

        if (input != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(input);
            returnValue = calendar.getTimeInMillis();
            return returnValue;
        } else {
            return null;
        }
    }

}
