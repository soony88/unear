package monash.kuyumcians.unear.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class DateUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd/MM/yyyy");

    public static String dateToString(Date date) {
        String myString = new String();
        try {
            myString = sdf.format(date); // Parse
        }
        catch (Exception e) {
            e.printStackTrace(); //print stack trace for exception diagnosis
        }
        myString = alterMonth(myString, 1);
        return myString;
    }

    /**
     * Converts a date stored as a String into a Date object
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        Date myDate = new Date();
        try {
            date = alterMonth(date, -1);
            myDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    public static String alterMonth(String date, int amt) {
        // Split the date, alter the month by the amount, and then build the string again and return it
        String[] stringArray = date.split("/");
        int month = Integer.parseInt(stringArray[1]);
        month += amt;
        String newMonth = Integer.toString(month);

        date = stringArray[0] + "/" + newMonth + "/" + stringArray[2];
        return date;
    }
}
