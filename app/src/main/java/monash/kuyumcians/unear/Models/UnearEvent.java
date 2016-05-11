package monash.kuyumcians.unear.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class UnearEvent implements Serializable {

    private String eventName;
    private double latitude;
    private double longitude;

    private Date startTime;
    private Date endTime;

    private String eventType;
    private String eventDescription;

    public UnearEvent(String eventName, double latitude, double longitude, Date startTime, Date endTime, String eventType, String eventDescription) {
        this.eventName = eventName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
