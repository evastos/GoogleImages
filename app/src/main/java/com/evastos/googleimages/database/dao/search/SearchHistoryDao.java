package com.evastos.googleimages.database.dao.search;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.evastos.googleimages.database.DatabaseHelper;
import com.evastos.googleimages.database.model.search.SearchHistoryItem;

import java.util.ArrayList;
import java.util.List;

import static com.evastos.googleimages.database.DatabaseHelper.Searches.COLUMN_ID;
import static com.evastos.googleimages.database.DatabaseHelper.Searches.COLUMN_SEARCH_QUERY;
import static com.evastos.googleimages.database.DatabaseHelper.Searches.COLUMN_SEARCH_TIME;
import static com.evastos.googleimages.database.DatabaseHelper.Searches.TABLE_SEARCH_HISTORY;

/**
 * Created by Eva Stos on 8.3.2015..
 */
public class SearchHistoryDao {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {COLUMN_ID,
            COLUMN_SEARCH_QUERY, COLUMN_SEARCH_TIME};

    public SearchHistoryDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SearchHistoryItem createSearchHistoryItem(String searchQuery) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEARCH_QUERY, searchQuery);
        values.put(COLUMN_SEARCH_TIME, System.currentTimeMillis());
        long insertId = database.insert(TABLE_SEARCH_HISTORY, null,
                values);
        Cursor cursor = database.query(TABLE_SEARCH_HISTORY,
                allColumns, COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        SearchHistoryItem searchHistoryItem = cursorToSearchHistory(cursor);
        cursor.close();
        return searchHistoryItem;
    }

    public List<SearchHistoryItem> getSearchHistory() {
        List<SearchHistoryItem> searchHistory = new ArrayList<SearchHistoryItem>();

        Cursor cursor = database.query(TABLE_SEARCH_HISTORY,
                allColumns, null, null, null, null, COLUMN_SEARCH_TIME + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SearchHistoryItem searchHistoryItem = cursorToSearchHistory(cursor);
            searchHistory.add(searchHistoryItem);
            cursor.moveToNext();
        }
        cursor.close();
        return searchHistory;
    }

    private SearchHistoryItem cursorToSearchHistory(Cursor cursor) {
        SearchHistoryItem searchHistoryItem = new SearchHistoryItem();
        searchHistoryItem.setId(cursor.getLong(0));
        searchHistoryItem.setSearchQuery(cursor.getString(1));
        searchHistoryItem.setTime(cursor.getLong(2));
        return searchHistoryItem;
    }

}
