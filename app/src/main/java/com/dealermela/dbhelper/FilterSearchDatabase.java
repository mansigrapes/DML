package com.dealermela.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dealermela.util.AppLogger;

public class FilterSearchDatabase  {

    public static final String DB_SEARCH = "SEARCH_DB";
    public final static String TABLE_FILTER_SEARCH = "SEARCH_TB";
    public final static String FIELD_SEARCH = "search";

    private SQLiteDatabase db;
    private Helper helper;

    public FilterSearchDatabase(Context context){
        helper = new Helper(context, DB_SEARCH,null,1);
        db = helper.getWritableDatabase();
    }

    public void deleteAllRecord() {
        db.delete(TABLE_FILTER_SEARCH, null, null);
    }

    public Cursor getAllValues() {
        String[] columns = {FIELD_SEARCH};
        Cursor c = db.query(true, TABLE_FILTER_SEARCH, columns, null, null, null, null, null, null);
        c.moveToNext();

        return c;
    }

    public long insertSuggestion(String text)
    {
        ContentValues values = new ContentValues();
        values.put(FIELD_SEARCH, text);
        return db.insert(TABLE_FILTER_SEARCH, null, values);
    }

    public Cursor getSuggestions(String text)
    {
//        return db.query(TABLE_FILTER_SEARCH, new String[] {FIELD_SEARCH},
//                FIELD_SEARCH+" LIKE '"+ text +"%'", null, null, null, null);

        return db.query(TABLE_FILTER_SEARCH, new String[] {FIELD_SEARCH},
                FIELD_SEARCH+" LIKE '"+ text +"%'", null, null, null, null);
    }

    private class Helper extends SQLiteOpenHelper{

        public Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TABLE_FILTER_SEARCH+" ("+FIELD_SEARCH+" text);");
            Log.d("Filter_Search", "DB CREATED");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
