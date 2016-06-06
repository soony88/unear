package monash.kuyumcians.unear.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import monash.kuyumcians.unear.Utils.DateUtils;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class SearchFilter implements Parcelable {

    // Class attributes
    private String campus;
    private Date startDate;
    private Date endDate;

    // Constructor

    public SearchFilter(String campus, Date startDate, Date endDate) {
        this.campus = campus;
        this.startDate = startDate;
        this.endDate = endDate;
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
