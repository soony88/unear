package monash.kuyumcians.unear.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import monash.kuyumcians.unear.Utils.DateUtils;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class UnearEvent implements Parcelable, Comparable<UnearEvent> {

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

    @Override
    public int compareTo(UnearEvent anotherEvent) {
        return anotherEvent.getStartDate().compareTo(this.getStartDate());
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

    // Write parcel
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // Write values in the same order as they would be read

        parcel.writeString(this.eventName);
        parcel.writeString(this.campus);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeString(DateUtils.dateToString(this.startDate));
        parcel.writeString(DateUtils.dateToString(this.endDate));
        parcel.writeString(this.eventType);
        parcel.writeString(this.eventDescription);
    }

    // Read parcel
    public UnearEvent(Parcel in) {
        // Read values in same order as they are written

        this.eventName = in.readString();
        this.campus = in.readString();
        this.latitude = in.readLong();
        this.longitude = in.readLong();
        this.startDate = DateUtils.stringToDate(in.readString());
        this.endDate = DateUtils.stringToDate(in.readString());
        this.eventType = in.readString();
        this.eventDescription = in.readString();
    }

    public static final Parcelable.Creator<UnearEvent> CREATOR = new Creator<UnearEvent>() {
        @Override
        public UnearEvent createFromParcel(Parcel in) {
            return new UnearEvent(in);
        }

        @Override
        public UnearEvent[] newArray(int size) {
            return new UnearEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
