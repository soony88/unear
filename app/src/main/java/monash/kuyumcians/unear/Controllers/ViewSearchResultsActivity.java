package monash.kuyumcians.unear.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import monash.kuyumcians.unear.Adapters.EventAdapter;
import monash.kuyumcians.unear.Models.SearchFilter;
import monash.kuyumcians.unear.Models.UnearEvent;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.DateUtils;
import monash.kuyumcians.unear.Utils.EventUtils;

public class ViewSearchResultsActivity extends AppCompatActivity {

    public static final String JSON_DOWNLOAD_LOCATION =
            "https://api.myjson.com/bins/134mg";

    // UI components
    SearchFilter searchFilter;
    private ListView searchResultsList;
    private EventAdapter adapter;
    private ArrayList<UnearEvent> events;

    // Firebase
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        // Link UI components
        searchResultsList = (ListView) findViewById(R.id.searchResultList);

        // Firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Convert the added object
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> addedEvent = dataSnapshot.getValue(genericTypeIndicator);

                UnearEvent event = EventUtils.convertMapToEvent(addedEvent);
                events.add(event);
                EventUtils.updateEventsList(events, searchFilter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Grab parcelable SearchFilter from previous activity
        Intent intent = getIntent();
        searchFilter = intent.getParcelableExtra("searchFilter");

        // Link the ArrayList of events with the EventAdapter
        events = new ArrayList<>();
        adapter = new EventAdapter(this, events);
        searchResultsList.setAdapter(adapter);

        // Retrieve JSON data
        new SetupArticleDatasetTask().execute(JSON_DOWNLOAD_LOCATION);

        // Allow user to tap on event to view event details
        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int i, long l)
            {
                // Get selected event
                UnearEvent event = (UnearEvent) searchResultsList.getAdapter().getItem(i);

                //Setup Intent and pass the event object to the next activity (view event)
                Intent intent = new Intent(getApplicationContext(), ViewEventDetailsActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
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
                    JSONObject university = responseJson.getJSONObject("monashUniversity");
                    // Grab the array
                    JSONArray campusJsonArray = university.getJSONArray("campuses");

                    for (int i = 0; i < campusJsonArray.length(); i++) {

                        JSONObject campusJson = campusJsonArray.getJSONObject(i);

                        if (campusJson.getString("campusName").equals(searchFilter.getCampus())) {

                            JSONArray eventsJsonArray = campusJson.getJSONArray("events");

                            for (int j = 0; j < eventsJsonArray.length(); j++) {
                                // Grab the event object
                                JSONObject eventJson = eventsJsonArray.getJSONObject(j);

                                // Pull in the data into variables to call the constructor
                                String name = eventJson.getString("eventName");
                                String campus = campusJson.getString("campusName");
                                double latitude = Double.parseDouble(eventJson.getString("eventLatitude"));
                                double longitude = Double.parseDouble(eventJson.getString("eventLongitude"));
                                Date startDate = DateUtils.stringToDate(eventJson.getString("eventStartDate"));
                                Date endDate = DateUtils.stringToDate(eventJson.getString("eventEndDate"));
                                String type = eventJson.getString("eventType");
                                String desc = eventJson.getString("eventDescription");

                                if (endDate.compareTo(searchFilter.getStartDate()) == 1) {
//                                    UnearEvent e = new UnearEvent(name, campus, latitude, longitude, startDate, endDate, type, desc);
//                                    events.add(e);
                                }
                            }

                            // Update listview
                            adapter.notifyDataSetChanged();
                        }
                    }
                    // Remove label
//                    labelError.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Notify user of error
//            labelError.setText("Could not retrieve results");
        }
    }
}
