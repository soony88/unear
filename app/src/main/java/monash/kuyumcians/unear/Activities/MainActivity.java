package monash.kuyumcians.unear.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import monash.kuyumcians.unear.Models.SearchFilter;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.DateUtils;

public class MainActivity extends AppCompatActivity {

    public static final String[] CAMPUS_NAMES = new String[] {
            "Clayton", "Caulfield", "Berwick"
    };

    // UI components (in order of physical location on screen)
    Button buttonSearchUnearby;
    Spinner spinnerCampusSelector;
    TextView labelStartDate;
    TextView labelEndDate;
    Button buttonSearchEvents;

    // Other attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseUiComponents();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }

    private void initialiseUiComponents() {

        // Search Unearby button
        buttonSearchUnearby = (Button) findViewById(R.id.buttonSearchUnearby);

        // Campus selector
        spinnerCampusSelector = (Spinner) findViewById(R.id.spinnerCampus);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                CAMPUS_NAMES
        );
        spinnerCampusSelector.setAdapter(adapter);

        // Date range
        labelStartDate = (TextView) findViewById(R.id.labelStartDate);
        labelEndDate = (TextView) findViewById(R.id.labelEndDate);

        // Search Events button
        buttonSearchEvents = (Button) findViewById(R.id.buttonSearchEvents);
    }

    public void searchUnearby(View view) {
        // TODO grab latitude and longitude from current location
        // TODO create searchfilter with latitude, longitude, and current time as start time up until the end of the day

        Intent i = new Intent(this, ViewSearchResultsActivity.class);
        startActivity(i);
    }

    public void searchEvents(View view) {

        // Read values of search parameters
        String campus = (String) spinnerCampusSelector.getSelectedItem();
        String stringStartDate = labelStartDate.getText().toString();
        String stringEndDate = labelEndDate.getText().toString();

        Date startDate = DateUtils.stringToDate("00:00 " + stringStartDate);
        Date endDate = DateUtils.stringToDate("23:59 " + stringEndDate);

        SearchFilter sf = new SearchFilter(campus, startDate, endDate);

        Intent i = new Intent(this, ViewSearchResultsActivity.class);
        i.putExtra("searchFilter", sf);
        startActivity(i);
    }

    public void pickStartDate(View view) {
        DialogFragment startDatePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", "start");
        startDatePicker.setArguments(bundle);
        startDatePicker.show(getSupportFragmentManager(), "startDatePicker");
    }

    public void pickEndDate(View view) {
        DialogFragment startDatePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", "end");
        startDatePicker.setArguments(bundle);
        startDatePicker.show(getSupportFragmentManager(), "endDatePicker");
    }
}
