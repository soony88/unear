package monash.kuyumcians.unear.Database;

import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Date;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class SearchFilter implements Serializable {

    // Database table
    public static final String TABLE_NAME = "filter";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CAMPUS = "campus";
    public static final String COLUMN_STARTDATE = "startDate";
    public static final String COLUMN_ENDDATE = "endDate";
//    public static final String COLUMN_SEARCH_RADIUS = "searchRadius";
//    public static final String COLUMN_TYPE_TICKETS = "tickets";
//    public static final String COLUMN_TYPE_BBQ = "bbq";
//    public static final String COLUMN_TYPE_OTHER = "other";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE" + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_CAMPUS + " INTEGER NOT NULL, " +
                    COLUMN_STARTDATE + " TEXT NOT NULL, " +
                    COLUMN_ENDDATE + " TEXT NOT NULL, " +
                    ")";

    // Class attributes
    private long id;
    private String campus;
    private Date startDate;
    private Date endDate;

    // Database Constructor
    public SearchFilter(long id, String campus, Date startDate, Date endDate) {
        this.id = id;
        this.campus = campus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
}
