package monash.kuyumcians.unear.Controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;

import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.CustomToast;
import monash.kuyumcians.unear.Utils.DateUtils;

/**
 * Created by kuyumcians on 3/06/2016.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    String date;
    TextView labelStartDate;
    TextView labelEndDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        this.date = bundle.getString("date");
        labelStartDate = ((TextView) getActivity().findViewById(R.id.labelStartDate));
        labelEndDate = (TextView) getActivity().findViewById(R.id.labelEndDate);

        String startDate = labelStartDate.getText().toString();
        String endDate = labelEndDate.getText().toString();

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (this.date.equals("start")) {
            // Use the current date as the default date in the picker
            if (!startDate.equals("Select Date")) {
                String[] split = startDate.split("/");
                day = Integer.parseInt(split[0]);
                month = Integer.parseInt(split[1]);
                year = Integer.parseInt(split[2]);
            }
        } else {
            if (!startDate.equals("Select Date")) {
                String[] split = startDate.split("/");
                day = Integer.parseInt(split[0]);
                month = Integer.parseInt(split[1]);
                year = Integer.parseInt(split[2]);
            }
            if (!endDate.equals("Select Date")) {
                String[] split = endDate.split("/");
                day = Integer.parseInt(split[0]);
                month = Integer.parseInt(split[1]);
                year = Integer.parseInt(split[2]);
            }
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String stringDate = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

        if (this.date.equals("start")) {
            // Start date must be before the end date

            // If end date has been set, perform the check
            if (!labelEndDate.getText().toString().equals("Select Date")) {
                Date startDate = DateUtils.stringToDate("00:00 " + stringDate);
                Date endDate = DateUtils.stringToDate("23:59 " + labelEndDate.getText().toString());
                // The 'compareTo' function returns 1 if endDate comes before the selected date for startDate
                if (startDate.compareTo(endDate) == 1) {
                    CustomToast.displayToast(getActivity(), "Start date must be before the end date");
                } else {
                    // Otherwise set the date
                    labelStartDate.setText(stringDate);
                }
            } else {
                // Otherwise set the date
                labelStartDate.setText(stringDate);
            }
        } else {
            // And vice versa for setting the end date

            // If start date has been set
            if (!labelStartDate.getText().toString().equals("Select Date")) {
                Date startDate = DateUtils.stringToDate("00:00 " + labelStartDate.getText().toString());
                Date endDate = DateUtils.stringToDate("23:59 " + stringDate);

                // Compare dates, display toast if endDate is before startDate
                if (startDate.compareTo(endDate) == 1) {
                    CustomToast.displayToast(getActivity(), "End date must be set after the start date");
                } else {
                    labelEndDate.setText(stringDate);
                }
            } else {
                labelEndDate.setText(stringDate);
            }
        }
    }
}
