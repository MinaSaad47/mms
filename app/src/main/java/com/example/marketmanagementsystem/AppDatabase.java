package com.example.marketmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class AppDatabase extends SQLiteOpenHelper {

    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String COL_ITEM_ID = "ID";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_ITEM_PRICE = "ITEM_PRICE";
    public static final String COL_ITEM_QUANTITY = "ITEM_QUANTITY";
    private static final String COL_ITEM_IMAGE_URL = "ITEM_IMAGE_URL";

    private static volatile AppDatabase INSTANT = null;

    private AppDatabase(@Nullable Context context) {
        super(context, "items.db", null, 1);
    }

    public static AppDatabase getInstant(Context context) {
        if (INSTANT == null) {
            synchronized (AppDatabase.class) {
                if (INSTANT == null) {
                    INSTANT = new AppDatabase(context);
                }
            }
        }
        return INSTANT;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryString = "CREATE TABLE " + ITEM_TABLE + "(" +
        COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        COL_ITEM_NAME + " TEXT NOT NULL, " +
        COL_ITEM_PRICE + " REAL NOT NULL, " +
        COL_ITEM_QUANTITY + " INT NOT NULL, " +
        COL_ITEM_IMAGE_URL + " TEXT);";

        sqLiteDatabase.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_ITEM_NAME, item.getName());
        cv.put(COL_ITEM_PRICE, item.getPrice());
        cv.put(COL_ITEM_QUANTITY, item.getQuantity());
        cv.put(COL_ITEM_IMAGE_URL, item.getImageURL());

        long insert = db.insert(ITEM_TABLE, null, cv);

        db.close();

        return insert == -1;
    }

    public List<Item> getItemsList(String itemName) {
        List<Item> returnList = new ArrayList<>();

        String queryString = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        if (itemName == null) {
            queryString = "SELECT * FROM " + ITEM_TABLE;
            cursor = db.rawQuery(queryString, null);
        }
        else {
            queryString = "SELECT * FROM " + ITEM_TABLE + " WHERE " +
                    COL_ITEM_NAME + " = ?";
            cursor = db.rawQuery(queryString, new String[] {itemName});
        }



        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getDouble(2), // price
                        cursor.getInt(3), // quantity
                        cursor.getString(4) // image URL
                );

                returnList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM " + ITEM_TABLE + " WHERE " + COL_ITEM_ID + " = " +
                item.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        boolean rc = cursor.moveToFirst();
        cursor.close();
        db.close();

        return rc;
    }

    public boolean updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ITEM_ID, item.getId());
        cv.put(COL_ITEM_NAME, item.getName());
        cv.put(COL_ITEM_PRICE, item.getPrice());
        cv.put(COL_ITEM_QUANTITY, item.getQuantity());
        cv.put(COL_ITEM_IMAGE_URL, item.getImageURL());

        int update = db.update(
                ITEM_TABLE,
                cv,
                "id=?",
                new String[]{String.valueOf(item.getId())});

        db.close();

        return update == 1;
    }
}
