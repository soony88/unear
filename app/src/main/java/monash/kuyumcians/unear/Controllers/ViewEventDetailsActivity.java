package monash.kuyumcians.unear.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import monash.kuyumcians.unear.Models.UnearEvent;
import monash.kuyumcians.unear.R;
import monash.kuyumcians.unear.Utils.DateUtils;

public class ViewEventDetailsActivity extends AppCompatActivity {

    private TextView eventName;
    private TextView eventStartDate;
    private TextView eventEndDate;
    private TextView eventDesc;

    private UnearEvent currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);

        InitialiseUiComponents();

        // Grab parcelable event object from previous page
        Intent intent = getIntent();
        currentEvent =intent.getParcelableExtra("event");

        eventName.setText(currentEvent.getEventName());
        eventStartDate.setText(DateUtils.dateToString(currentEvent.getStartDate()) + " -");
        eventEndDate.setText(DateUtils.dateToString(currentEvent.getEndDate()));
        eventDesc.setText(currentEvent.getEventDescription());
    }

    private void InitialiseUiComponents() {
        eventName = (TextView) findViewById(R.id.labelEventName);
        eventStartDate = (TextView) findViewById(R.id.labelStart);
        eventEndDate = (TextView) findViewById(R.id.labelEnd);
        eventDesc = (TextView) findViewById(R.id.labelEventDescription);
    }
}
