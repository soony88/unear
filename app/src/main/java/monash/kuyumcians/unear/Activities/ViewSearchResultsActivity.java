package monash.kuyumcians.unear.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import monash.kuyumcians.unear.Adapters.EventAdapter;
import monash.kuyumcians.unear.Models.SearchFilter;
import monash.kuyumcians.unear.Models.UnearEvent;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.EventUtils;

public class ViewSearchResultsActivity extends AppCompatActivity {

    // UI components
    SearchFilter searchFilter;
    private ListView searchResultsList;
    private EventAdapter adapter;
    private ArrayList<UnearEvent> events;

    // Firebase
    FirebaseAuth auth;
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
        auth = FirebaseAuth.getInstance();


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Convert the added object
                // Need to define a GenericTypeIndicator in order to retrieve the Firebase object correctly
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> addedEvent = dataSnapshot.getValue(genericTypeIndicator);
                UnearEvent event = EventUtils.convertMapToEvent(addedEvent); // Convert it to event object

                if (EventUtils.withinFilter(event, searchFilter)) {
                    events.add(event);
                    Collections.sort(events);
                    adapter.notifyDataSetChanged();
                }
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
}
