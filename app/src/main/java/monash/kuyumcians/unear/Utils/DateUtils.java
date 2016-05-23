package monash.kuyumcians.unear.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class DateUtils {

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd/MM/yyyy"); // Format of date
        String myString = new String();
        try {
            myString = dateFormat.format(date); // Parse
        }
        catch (Exception e) {
            e.printStackTrace(); //print stack trace for exception diagnosis
        }

        return myString;
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        Date myDate = new Date();
        try {
            myDate = dateFormat.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
}
