package monash.kuyumcians.unear.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import monash.kuyumcians.unear.Models.SearchFilter;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.DateUtils;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    public static final String[] CAMPUS_NAMES = new String[] {
            "Clayton", "Caulfield", "Berwick"
    };

    // UI components (in order of physical location on screen)
    Button buttonSearchUnearby;
    Spinner spinnerCampusSelector;
    EditText textStartDate;
    EditText textEndDate;
    Button buttonSearchEvents;

    // Other attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Facebook API
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        initialiseUiComponents();
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
        textStartDate = (EditText) findViewById(R.id.textStartDate);
        textEndDate = (EditText) findViewById(R.id.textEndDate);

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
        String stringStartDate = textStartDate.getText().toString();
        String stringEndDate = textEndDate.getText().toString();

        Date startDate = DateUtils.stringToDate(stringStartDate);
        Date endDate = DateUtils.stringToDate(stringEndDate);

        SearchFilter sf = new SearchFilter(campus, startDate, endDate);

        Intent i = new Intent(this, ViewSearchResultsActivity.class);
        i.putExtra("searchFilter", sf);
        startActivity(i);
    }
}
