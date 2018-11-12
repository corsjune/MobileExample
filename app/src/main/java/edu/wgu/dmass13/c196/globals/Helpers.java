package edu.wgu.dmass13.c196.globals;

import java.text.ParseException;
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

}
