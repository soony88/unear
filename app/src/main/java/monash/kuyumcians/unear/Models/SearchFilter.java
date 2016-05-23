package monash.kuyumcians.unear.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import monash.kuyumcians.unear.Utils.DateUtils;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class SearchFilter implements Parcelable {

    // FIXME: commented out code for if I want to add saved search preferences (campus)

//    // Database table
//    public static final String TABLE_NAME = "filter";
//    public static final String COLUMN_ID = "_id";
//    public static final String COLUMN_CAMPUS = "campus";
//    public static final String COLUMN_STARTDATE = "startDate";
//    public static final String COLUMN_ENDDATE = "endDate";
//    public static final String COLUMN_SEARCH_RADIUS = "searchRadius";
//    public static final String COLUMN_TYPE_TICKETS = "tickets";
//    public static final String COLUMN_TYPE_BBQ = "bbq";
//    public static final String COLUMN_TYPE_OTHER = "other";
//
//    public static final String CREATE_STATEMENT =
//            "CREATE TABLE" + TABLE_NAME + "(" +
//                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    COLUMN_CAMPUS + " INTEGER NOT NULL, " +
//                    COLUMN_STARTDATE + " TEXT NOT NULL, " +
//                    COLUMN_ENDDATE + " TEXT NOT NULL, " +
//                    ")";

    // Class attributes
    private long id;
    private String campus;
    private Date startDate;
    private Date endDate;

//    // Database Constructor
//    public SearchFilter(long id, String campus, Date startDate, Date endDate) {
//        this.id = id;
//        this.campus = campus;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }

    // Regular Constructor

    public SearchFilter(String campus, Date startDate, Date endDate) {
        this.campus = campus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // Write parcel
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // Write values in the same order as they would be read

        // Campus
        parcel.writeString(this.campus);

        // Start Date
        String stringStartDate = DateUtils.dateToString(this.startDate);
        parcel.writeString(stringStartDate);

        // End date
        String stringEndDate = DateUtils.dateToString(this.endDate);
        parcel.writeString(stringEndDate);
    }

    // Read parcel
    public SearchFilter(Parcel in) {
        // Read values in same order as they are written

        // Campus
        this.campus = in.readString();

        // Start Date
        String stringStartDate = in.readString();
        this.startDate = DateUtils.stringToDate(stringStartDate);

        // End Date
        String stringEndDate = in.readString();
        this.endDate = DateUtils.stringToDate(stringEndDate);
    }

    public static final Parcelable.Creator<SearchFilter> CREATOR = new Creator<SearchFilter>() {
        @Override
        public SearchFilter createFromParcel(Parcel in) {
            return new SearchFilter(in);
        }

        @Override
        public SearchFilter[] newArray(int size) {
            return new SearchFilter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
