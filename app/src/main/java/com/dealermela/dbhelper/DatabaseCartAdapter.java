package com.dealermela.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dealermela.util.AppLogger;

import java.util.ArrayList;

public class DatabaseCartAdapter {
    private static final String DATABASE_NAME = "cart_db";
    private static final int DATABASE_VERSION = 5;

    //Stud table
    private static final String TABLE_NAME = "cart_table";
    public static final String ID = "id";
    public static final String PRODUCT_ID = "product_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String PRODUCT_TYPE = "category_type";
    public static final String SKU = "sku";
    public static final String RING_SIZE = "ring_size";
    public static final String RING_OPTION_ID = "option_id";
    public static final String RING_OPTION_TYPE_ID = "option_type_id";
    public static final String BANGLE_SIZE = "bangle_size";
    public static final String BRACELET_SIZE = "bracelet_size";
    public static final String PENDENT_SET_TYPE = "pendent_set_type";
    public static final String METAL_DETAIL = "metal_detail";
    public static final String STONE_DETAIL = "stone_detail";
    public static final String STONE_OPTION_ID = "stone_option_id";
    public static final String STONE_OPTION_TYPE_ID = "stone_option_type_id";
    public static final String PRICE = "price";
    public static final String QTY = "qty";
    public static final String PRODUCT_IMAGE = "pro_image";
    public static final String METAL_QUALITY_COLOR = "metalqualitycolor";
    public static final String METAL_CARAT = "metalcarat";

    private SQLiteDatabase mydb;
    private DHelper helper;

    public DatabaseCartAdapter(Context context) {
        helper = new DHelper(context);
    }

    public DatabaseCartAdapter openDatabase() {
        mydb = helper.getWritableDatabase();
        return this;
    }

    public void closeDatabase() {
        helper.close();
    }

    //find record
    public Cursor findRecordCheck(String productId) {
        Cursor cursor = null;
        String sql = "SELECT product_id FROM " + TABLE_NAME + " WHERE product_id=" + productId;
        cursor = mydb.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            //PID Found
        } else {
            //PID Not Found
        }
        cursor.close();
        return cursor;
    }


    //count like
    public Cursor countLike(String studid) {
        Cursor c = mydb.rawQuery("select count(*) from stud_like where studid=" + studid, null);
        return c;
    }

    //Insert Record stud
    public void addValues(String productId, String categoryId, String productType, String sku, String ringSize, String bangle, String bracelet, String pendent_set, String metalDetail, String stoneDetail, String price, String qty, String image, String ringOptionId, String ringOptionTypeId, String stoneOptionId, String stoneOptionTypeId, String metalqualitycolor, String metalcarat) {
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, productId);
        values.put(CATEGORY_ID, categoryId);
        values.put(PRODUCT_TYPE, productType);
        values.put(SKU, sku);
        values.put(RING_SIZE, ringSize);
        values.put(BANGLE_SIZE, bangle);
        values.put(BRACELET_SIZE, bracelet);
        values.put(PENDENT_SET_TYPE, pendent_set);
        values.put(METAL_DETAIL, metalDetail);
        values.put(STONE_DETAIL, stoneDetail);
        values.put(PRICE, price);
        values.put(QTY, qty);
        values.put(PRODUCT_IMAGE, image);
        values.put(RING_OPTION_ID, ringOptionId);
        values.put(RING_OPTION_TYPE_ID, ringOptionTypeId);
        values.put(STONE_OPTION_ID, stoneOptionId);
        values.put(STONE_OPTION_TYPE_ID, stoneOptionTypeId);
        values.put(METAL_QUALITY_COLOR,  metalqualitycolor);
        values.put(METAL_CARAT,  metalcarat);

//        mydb.insert(TABLE_NAME, null, values);
        long rowInserted = mydb.insert(TABLE_NAME, null, values);
        if (rowInserted != -1)
            AppLogger.e("row", "----------new row add");
        else
            AppLogger.e("wrong", "----------new row not add");
    }

    //View all stud data
    public Cursor getAllValues() {
        String[] columns = {ID, PRODUCT_ID, CATEGORY_ID, PRODUCT_TYPE, SKU, RING_SIZE, BANGLE_SIZE, BRACELET_SIZE, PENDENT_SET_TYPE, METAL_DETAIL, STONE_DETAIL, PRICE, QTY, PRODUCT_IMAGE, RING_OPTION_ID, RING_OPTION_TYPE_ID, STONE_OPTION_ID, STONE_OPTION_TYPE_ID, METAL_QUALITY_COLOR, METAL_CARAT};
        Cursor c = mydb.query(true, TABLE_NAME, columns, null, null, ID, null, null, null);
        c.moveToNext();

       /* if(c!=null && c.moveToNext())
        {
            Log.e("ID", c.getString(c.getColumnIndex("id")));
            Log.e("FName", c.getString(c.getColumnIndex("firstname")));
            Log.e("Lname", c.getString(c.getColumnIndex("lastname")));
            Log.e("Phone", c.getString(c.getColumnIndex("phone_no")));
        }else{
            Log.e("ELSE", "ELSE");
        }*/
       return c;

    }

    public void deleteItem(String id) {
        mydb.delete(TABLE_NAME, id + "=" + ID, null);
    }

    public void deleteAllRecord() {
        mydb.delete(TABLE_NAME, null, null);
    }

    public void updateQTY(String id, String qty) {
        ContentValues values = new ContentValues();
        values.put(QTY, qty);
        mydb.update(TABLE_NAME, values, id + "=" + ID, null);
    }

    class DHelper extends SQLiteOpenHelper {

        public DHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                //stud table
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PRODUCT_ID + " TEXT, "
                        + CATEGORY_ID + " TEXT, "
                        + PRODUCT_TYPE + " TEXT, "
                        + SKU + " TEXT, "
                        + RING_SIZE + " TEXT, "
                        + BANGLE_SIZE + " TEXT, "
                        + BRACELET_SIZE + " TEXT, "
                        + PENDENT_SET_TYPE + " TEXT, "
                        + METAL_DETAIL + " TEXT, "
                        + STONE_DETAIL + " TEXT, "
                        + PRICE + " TEXT, "
                        + QTY + " TEXT, "
                        + PRODUCT_IMAGE + " TEXT, "
                        + RING_OPTION_ID + " TEXT, "
                        + RING_OPTION_TYPE_ID + " TEXT, "
                        + STONE_OPTION_ID + " TEXT, "
                        + STONE_OPTION_TYPE_ID + " TEXT, "
                        + METAL_QUALITY_COLOR + " TEXT,"
                        + METAL_CARAT + " TEXT ); ");

            } catch (SQLException se) {
                Log.e("Sql Exception", se.toString());
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            // create new tables
            onCreate(db);
        }
    }

}
