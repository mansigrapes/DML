package com.dealermela.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dealermela.util.AppLogger;

public class SuggestionsDatabase {
    public static final String DB_SUGGESTION = "SUGGESTION_DB";
    public final static String TABLE_SUGGESTION = "SUGGESTION_TB";
    public final static String FIELD_ID = "_id";
    public final static String FIELD_SUGGESTION = "suggestion";

    private SQLiteDatabase db;
    private Helper helper;

    public SuggestionsDatabase(Context context) {
        helper = new Helper(context, DB_SUGGESTION, null, 1);
        db = helper.getWritableDatabase();
    }

    public long insertSuggestion(String text)
    {
        ContentValues values = new ContentValues();
        values.put(FIELD_SUGGESTION, text);
        return db.insert(TABLE_SUGGESTION, null, values);
    }

    public Cursor getSuggestions(String text)
    {
        return db.query(TABLE_SUGGESTION, new String[] {FIELD_ID, FIELD_SUGGESTION},
                FIELD_SUGGESTION+" LIKE '%"+ text +"%'", null, null, null, null);
    }

    public Cursor compareSearch(String text)
    {
//        return db.query(TABLE_SUGGESTION, new String[] {FIELD_ID, FIELD_SUGGESTION},
//                FIELD_SUGGESTION+" LIKE " + text,null,null,null,null);

        Cursor cursor;
        String sql = "SELECT _id FROM " + TABLE_SUGGESTION + " WHERE suggestion = '"+text+"'";
        cursor = db.rawQuery(sql,null);
//        cursor.close();

        if(cursor.moveToFirst()){
            AppLogger.e("Compare Query cursor result ","----" + cursor.getString(cursor.getColumnIndex(FIELD_ID)));
        }else {
        }
        return cursor;
    }

    public boolean deleteItem(String id) {
        db.delete(TABLE_SUGGESTION, id + "=" + FIELD_ID, null);
        return true;
    }

    public Cursor getAllValues() {
        String[] columns = {FIELD_ID, FIELD_SUGGESTION};
        Cursor c = db.query(true, TABLE_SUGGESTION, columns, null, null, FIELD_ID, null, null, null);
        c.moveToNext();

        return c;
    }

    private class Helper extends SQLiteOpenHelper
    {
        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TABLE_SUGGESTION+" ("+
                    FIELD_ID+" integer primary key autoincrement, "+FIELD_SUGGESTION+" text);");
            Log.d("SUGGESTION", "DB CREATED");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
