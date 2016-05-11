package monash.kuyumcians.unear.Models;

import java.util.ArrayList;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class Campus {

    private String campusName;
    private ArrayList<UnearEvent> events;

    public Campus(String campusName) {
        events = new ArrayList<UnearEvent>();
        this.campusName = campusName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public ArrayList<UnearEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<UnearEvent> events) {
        this.events = events;
    }

    public void addEvent(UnearEvent event) {
        this.events.add(event);
    }
}
