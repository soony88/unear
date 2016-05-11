package monash.kuyumcians.unear.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

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
            db.execSQL(SearchFilter.CREATE_STATEMENT);
        } catch (SQLException e) {
            CustomToast.displayToast(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SearchFilter.TABLE_NAME);

        try {
            db.execSQL(SearchFilter.CREATE_STATEMENT);
        } catch (SQLException e) {
            CustomToast.displayToast(context, "" + e);
        }
    }

    public void addFilter(SearchFilter searchFilter) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchFilter.COLUMN_CAMPUS,searchFilter.getCampus());
        contentValues.put(SearchFilter.COLUMN_STARTDATE, DateUtils.dateToString(searchFilter.getStartDate()));
        contentValues.put(SearchFilter.COLUMN_ENDDATE,DateUtils.dateToString(searchFilter.getEndDate()));

        //Inserting into database
        long id = db.insert(SearchFilter.TABLE_NAME, null, contentValues);
        searchFilter.setId(id);

        db.close();
    }

    public void  updateFilter(SearchFilter searchFilter) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchFilter.COLUMN_CAMPUS,searchFilter.getCampus());
        contentValues.put(SearchFilter.COLUMN_STARTDATE, DateUtils.dateToString(searchFilter.getStartDate()));
        contentValues.put(SearchFilter.COLUMN_ENDDATE,DateUtils.dateToString(searchFilter.getEndDate()));

        // Use the id
        long id = searchFilter.getId();

        db.update(searchFilter.TABLE_NAME, contentValues, searchFilter.COLUMN_ID + " = " + id, null);

        db.close();
        //Toast to let the user know that database has been updated
        CustomToast.displayToast(context, "Preference Saved");
    }

    public SearchFilter getSearchFilter() {

        SearchFilter searchFilter = null;
        SQLiteDatabase db = this.getReadableDatabase();

        // Run select statement and access data via Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + SearchFilter.TABLE_NAME, null);

        //If there is a data source
        if(cursor.moveToFirst()) {
            do {
                // TODO: Grab stuff
                long id = cursor.getLong(0);
                String campus = cursor.getString(1);
                Date startDate = DateUtils.stringToDate(cursor.getString(2));
                Date endDate = DateUtils.stringToDate(cursor.getString(3));
                searchFilter = new SearchFilter(id, campus, startDate, endDate);//, cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
            }
            //While the cursor can move to the next data source
            while(cursor.moveToNext());
        }

        cursor.close();
        // Return contents of table
        return searchFilter;
    }
}
