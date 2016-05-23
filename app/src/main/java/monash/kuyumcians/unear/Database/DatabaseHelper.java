package monash.kuyumcians.unear.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import monash.kuyumcians.unear.Models.UnearEvent;
import monash.kuyumcians.unear.Models.SearchFilter;
import monash.kuyumcians.unear.Utils.CustomToast;
import monash.kuyumcians.unear.Utils.DateUtils;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Set database properties
    public static final String DATABASE_NAME = "UnearDatabase";
    public static final int DATABASE_VERSION = 7;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(UnearEvent.CREATE_STATEMENT);
        } catch (SQLException e) {
            CustomToast.displayToast(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UnearEvent.TABLE_NAME);
        try {
//            db.execSQL(SearchFilter.CREATE_STATEMENT);
        } catch (SQLException e) {
            CustomToast.displayToast(context, "" + e);
        }
    }

    public void addEvent(UnearEvent event) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(event.COLUMN_NAME, event.getEventName());
        contentValues.put(event.COLUMN_CAMPUS, event.getCampus());
        contentValues.put(event.COLUMN_LATITUDE, event.getLatitude());
        contentValues.put(event.COLUMN_LONGITUDE, event.getLongitude());

        String stringStartDate = DateUtils.dateToString(event.getStartDate());
        contentValues.put(event.COLUMN_STARTDATE, stringStartDate);

        String stringEndDate = DateUtils.dateToString(event.getEndDate());
        contentValues.put(event.COLUMN_ENDDATE, stringEndDate);

        contentValues.put(event.COLUMN_TYPE, event.getEventType());
        contentValues.put(event.COLUMN_DESCRIPTION, event.getEventDescription());

        //Inserting into database
        long id = db.insert(event.TABLE_NAME, null, contentValues);
        event.setId(id);

        CustomToast.displayToast(context, "Added to saved events");

        db.close();
    }

    public void  updateEvent(UnearEvent event) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(event.COLUMN_NAME, event.getEventName());
        contentValues.put(event.COLUMN_CAMPUS, event.getCampus());
        contentValues.put(event.COLUMN_LATITUDE, event.getLatitude());
        contentValues.put(event.COLUMN_LONGITUDE, event.getLongitude());

        String stringStartDate = DateUtils.dateToString(event.getStartDate());
        contentValues.put(event.COLUMN_STARTDATE, stringStartDate);

        String stringEndDate = DateUtils.dateToString(event.getEndDate());
        contentValues.put(event.COLUMN_ENDDATE, stringEndDate);

        contentValues.put(event.COLUMN_TYPE, event.getEventType());
        contentValues.put(event.COLUMN_DESCRIPTION, event.getEventDescription());

        // Use the id
        long id = event.getId();

        db.update(event.TABLE_NAME, contentValues, event.COLUMN_ID + " = " + id, null);

        db.close();
        //Toast to let the user know that database has been updated
        CustomToast.displayToast(context, "Changes Saved");
    }

    public ArrayList<UnearEvent> getSavedEvents() {

        ArrayList<UnearEvent> events = null;
        SQLiteDatabase db = this.getReadableDatabase();

        // Run select statement and access data via Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + UnearEvent.TABLE_NAME, null);

        // If there is a data source
        if(cursor.moveToFirst()) {
            do {
                // Read values into parameters to call event constructor
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String campus = cursor.getString(2);
                double latitude = cursor.getDouble(3);
                double longitude = cursor.getDouble(4);
                Date startDate = DateUtils.stringToDate(cursor.getString(5));
                Date endDate = DateUtils.stringToDate(cursor.getString(6));
                String type = cursor.getString(7);
                String description = cursor.getString(8);

                // Create the event and add to the ArrayList
                UnearEvent event = new UnearEvent(id, name, campus, latitude, longitude,
                        startDate, endDate, type, description);
                events.add(event);
            }
            while(cursor.moveToNext()); // Repeat if there's another data entry
        }

        cursor.close();

        return events;
    }
}
