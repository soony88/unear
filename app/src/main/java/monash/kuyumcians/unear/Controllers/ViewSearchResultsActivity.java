package monash.kuyumcians.unear.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import monash.kuyumcians.unear.Adapters.EventAdapter;
import monash.kuyumcians.unear.Models.Campus;
import monash.kuyumcians.unear.Models.SearchFilter;
import monash.kuyumcians.unear.Models.UnearEvent;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.DateUtils;

public class ViewSearchResultsActivity extends AppCompatActivity {

    public static final String JSON_DOWNLOAD_LOCATION =
            "https://api.myjson.com/bins/1pi0m";

    // UI components
    SearchFilter searchFilter;
    private ListView searchResultsList;
    private EventAdapter adapter;
    private ArrayList<UnearEvent> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        // Get out intent and pass in the parcelable Monster
        Intent intent = getIntent();
        searchFilter = intent.getParcelableExtra("searchFilter");

        events = new ArrayList<UnearEvent>();

        // Link UI components
        searchResultsList = (ListView) findViewById(R.id.searchResultList);

//        for (int i = 0; i < 3; i++) {
//
//            Date startDate = DateUtils.stringToDate("12:00 16/06/16");
//            Date endDate = DateUtils.stringToDate("14:00 16/06/16");
//            events.add(new UnearEvent("eventTitle", "eventCampus", 10, 10, startDate, endDate, "eventType", "eventDescription"));
//        }

        new SetupArticleDatasetTask().execute(JSON_DOWNLOAD_LOCATION);

        // Link the ArrayList of events with the EventAdapter
        adapter = new EventAdapter(this, events);

        // Associate adapter with ListView
        searchResultsList.setAdapter(adapter);

        // On item click listener for editing reminders
        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int i, long l)
            {
                // Get selected reminder
                UnearEvent event = (UnearEvent) searchResultsList.getAdapter().getItem(i);

                // TODO create event details view
                //Setup Intent and pass the reminder object to the next activity (view reminder)
//                Intent i = new Intent(v.getContext(), ViewEventDetailsActivity.class);
//                i.putExtra("event", event);
//                startActivity(i);
            }
        });
    }

    private class SetupArticleDatasetTask extends AsyncTask<String, Void, String> { // Download JSON resource and return String representation
        protected String doInBackground(String... urls) {
            try {
                // Setup HTTP client and request
                URL downloadUrl = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                InputStream input = connection.getInputStream();

                // Process each line of response data
                String result = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                while ((result = reader.readLine()) != null) {
                    sb.append(result);
                }
                // Return response data as String
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // Add events to events array after processing resource
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    System.out.println("Success");
                    JSONObject resultJson = new JSONObject(result);

                    // Move down the JSON tree to where we want
                    JSONObject responseJson = resultJson.getJSONObject("responseData");

                    System.out.println(responseJson);

                    JSONObject university = responseJson.getJSONObject("monashUniversity");

                    // Grab the array
                    JSONArray campusJsonArray = university.getJSONArray("campuses");

                    for (int i = 0; i < campusJsonArray.length(); i++) {

                        JSONObject campusJson = campusJsonArray.getJSONObject(i);

                        if (campusJson.getString("campusName") == searchFilter.getCampus()) {

                            JSONArray eventsJsonArray = campusJson.getJSONArray("events");

                            for (int j = 0; j < eventsJsonArray.length(); j++) {
                                // Grab the event object
                                JSONObject eventJson = eventsJsonArray.getJSONObject(i);

                                // Pull in the data into variables to call the constructor
                                String eventName = eventJson.getString("eventName");
                                String campus = campusJson.getString("campusName");

                                String stringLatitude = eventJson.getString("eventLatitude");
                                double latitude = Double.parseDouble(stringLatitude);

                                String stringLongitude = eventJson.getString("eventLongitude");
                                double longitude = Double.parseDouble(stringLongitude);


                                String stringStartDate = eventJson.getString("eventStartDate");
                                Date startDate = DateUtils.stringToDate(stringStartDate);

                                String stringEndDate = eventJson.getString("eventEndDate");
                                Date endDate = DateUtils.stringToDate(stringEndDate);

                                String eventType = eventJson.getString("eventType");
                                String eventDesc = eventJson.getString("eventDescription");

                                if (searchFilter.getStartDate().compareTo(endDate) == 1) {
                                    UnearEvent e = new UnearEvent(eventName, campus, latitude, longitude, startDate, endDate, eventType, eventDesc);
                                    events.add(e);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
//                    labelError.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            labelError.setText("Could not retrieve results");
        }
    }
}
