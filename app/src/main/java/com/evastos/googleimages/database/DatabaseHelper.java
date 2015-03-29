package com.evastos.googleimages.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.evastos.googleimages.database.DatabaseHelper.Searches.COLUMN_ID;
import static com.evastos.googleimages.database.DatabaseHelper.Searches.COLUMN_SEARCH_QUERY;
import static com.evastos.googleimages.database.DatabaseHelper.Searches.COLUMN_SEARCH_TIME;
import static com.evastos.googleimages.database.DatabaseHelper.Searches.TABLE_SEARCH_HISTORY;

/**
 * Created by Eva Stos on 8.3.2015..
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();

    private static final String DATABASE_NAME = "google_images.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SEARCH_HISTORY + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SEARCH_QUERY
            + " text not null, " + COLUMN_SEARCH_TIME + " long);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_HISTORY);
        onCreate(db);
    }

    public interface Searches {
        public static final String TABLE_SEARCH_HISTORY = "search_history";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_SEARCH_QUERY = "search_query";
        public static final String COLUMN_SEARCH_TIME = "search_time";
    }

}
