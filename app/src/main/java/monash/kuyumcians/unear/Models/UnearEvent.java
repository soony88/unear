package monash.kuyumcians.unear.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class UnearEvent implements Serializable {

    // Database table
    public static final String TABLE_NAME = "event";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CAMPUS = "campus"; // TODO: may be able to remove this if I wire up campus and events properly
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_STARTDATE = "startDate";
    public static final String COLUMN_ENDDATE = "endDate";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE" + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_CAMPUS + " TEXT NOT NULL, " +
                    COLUMN_LATITUDE + " DOUBLE NOT NULL, " +
                    COLUMN_LONGITUDE + " DOUBLE NOT NULL, " +
                    COLUMN_STARTDATE + " TEXT NOT NULL, " +
                    COLUMN_ENDDATE + " TEXT NOT NULL, " +
                    COLUMN_TYPE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    ")";
    // TODO: may be able to remove campus if I wire up campus and events properly

    // Class attributes

    // Identification
    private long id;
    private String eventName;

    // Location
    private String campus;
    private double latitude;
    private double longitude;

    // When
    private Date startDate;
    private Date endDate;

    // Description
    private String eventType;
    private String eventDescription;

    // Constructor
    public UnearEvent(String eventName, String campus, double latitude, double longitude, Date startDate, Date endDate, String eventType, String eventDescription) {
        this.eventName = eventName;
        this.campus = campus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    // Database Constructor
    public UnearEvent(long id, String eventName, String campus, double latitude, double longitude, Date startDate, Date endDate, String eventType, String eventDescription) {
        this.id = id;
        this.eventName = eventName;
        this.campus = campus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startTime) {
        this.startDate = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endTime) {
        this.endDate = endTime;
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
