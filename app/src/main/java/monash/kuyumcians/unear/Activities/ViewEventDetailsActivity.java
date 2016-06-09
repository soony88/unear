package monash.kuyumcians.unear.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Camera;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import monash.kuyumcians.unear.Models.UnearEvent;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.DateUtils;

public class ViewEventDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView eventName;
    private TextView eventStartDate;
    private TextView eventEndDate;
    private TextView eventDesc;
    public static FragmentManager fragmentManager;

    private GoogleMap map;
    private MapFragment mapFragment;
    LatLng marker;

    private UnearEvent currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);

        initialiseUiComponents();
        populateUiFields();

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initialiseUiComponents() {
        eventName = (TextView) findViewById(R.id.labelEventName);
        eventStartDate = (TextView) findViewById(R.id.labelStart);
        eventEndDate = (TextView) findViewById(R.id.labelEnd);
        eventDesc = (TextView) findViewById(R.id.labelEventDescription);
    }

    private void populateUiFields() {
        // Grab parcelable event object from previous page
        Intent intent = getIntent();
        currentEvent = intent.getParcelableExtra("event");

        eventName.setText(currentEvent.getEventName());
        eventStartDate.setText(DateUtils.dateToString(currentEvent.getStartDate()) + " -");
        eventEndDate.setText(DateUtils.dateToString(currentEvent.getEndDate()));
        String description = currentEvent.getEventDescription();
        eventDesc.setText(Html.fromHtml(description));

        // Map marker for event location
        marker = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions()
                .position(marker)
                .title(currentEvent.getEventName()));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17));

    }
}
