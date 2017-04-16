package com.animelabs.gopaisasampleproject.StorageHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.animelabs.gopaisasampleproject.Model.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by asheeshsharma on 09/04/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "appListManager";

    // Table Names
    private static final String TABLE_APP_DETAIL = "app_detail_table";

    // Common column names
    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_APP_NAME = "name";
    private static final String KEY_APP_SUMMARY = "summary";
    private static final String KEY_APP_PRICE = "price";
    private static final String KEY_APP_IMAGEURL = "image_url";
    private static final String KEY_APP_CURRENCY = "currency";

    // NOTES Table - column nmaes
    private static final String KEY_USER_ANSWER = "answer";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_APP_LIST = "CREATE TABLE "
            + TABLE_APP_DETAIL + "(" + KEY_APP_ID + " TEXT PRIMARY KEY," + KEY_APP_NAME
            + " TEXT," + KEY_APP_SUMMARY + " TEXT," +  KEY_APP_PRICE + " TEXT," +  KEY_APP_CURRENCY + " TEXT," + KEY_APP_IMAGEURL
            + " TEXT" + ")";

    // Tag table create statement

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_APP_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_DETAIL);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "todos" table methods ----------------//

    /**
     * Creating a todo
     */
    public long createEntry(Entry entryModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_APP_ID, entryModel.getAppId());
        values.put(KEY_APP_NAME, entryModel.getName().getLabel());
        values.put(KEY_APP_SUMMARY, entryModel.getSummary().getLabel());
        values.put(KEY_APP_IMAGEURL, entryModel.getImage().getLabel());
        values.put(KEY_APP_PRICE, entryModel.getPrice().getPriceAttributes().getAmount());
        values.put(KEY_APP_CURRENCY, entryModel.getPrice().getPriceAttributes().getCurrency());

        // insert row
        long todo_id = db.insert(TABLE_APP_DETAIL, null, values);


        return todo_id;
    }

    /**
     * get single todo
     */
    public Entry getEntry(String todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_APP_DETAIL + " WHERE "
                + KEY_APP_ID + " = " + todo_id;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        if(c.getCount() <= 0)
        {
            return null;
        }

        Entry entry = new Entry(c.getString(c.getColumnIndex(KEY_APP_IMAGEURL)),c.getString(c.getColumnIndex(KEY_APP_SUMMARY)),c.getString(c.getColumnIndex(KEY_APP_NAME)),c.getString(c.getColumnIndex(KEY_APP_PRICE)),c.getString(c.getColumnIndex(KEY_APP_CURRENCY)),c.getString(c.getColumnIndex(KEY_APP_ID)));

        return entry;
    }

    /**
     * getting all todos
     * */
    public List<Entry> getAllToDos() {
        List<Entry> todos = new ArrayList<Entry>();
        String selectQuery = "SELECT  * FROM " + TABLE_APP_DETAIL;

        Log.d("Printed","PrintedTodos");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Entry entry = new Entry(c.getString(c.getColumnIndex(KEY_APP_IMAGEURL)),c.getString(c.getColumnIndex(KEY_APP_SUMMARY)),c.getString(c.getColumnIndex(KEY_APP_NAME)),c.getString(c.getColumnIndex(KEY_APP_PRICE)),c.getString(c.getColumnIndex(KEY_APP_CURRENCY)),c.getString(c.getColumnIndex(KEY_APP_ID)));

                // adding to todo list
                todos.add(entry);
            } while (c.moveToNext());
        }

        return todos;
    }


    /**
     * getting todo count
     */
    public int getToDoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APP_DETAIL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a question
     */
/*
 * Updating a todo
 */
    public int updateToDo(Entry entryModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_APP_ID, entryModel.getAppId());
        values.put(KEY_APP_NAME, entryModel.getName().getLabel());
        values.put(KEY_APP_SUMMARY, entryModel.getSummary().getLabel());
        values.put(KEY_APP_IMAGEURL, entryModel.getImage().getLabel());
        values.put(KEY_APP_PRICE, entryModel.getPrice().getPriceAttributes().getAmount());
        values.put(KEY_APP_CURRENCY, entryModel.getPrice().getPriceAttributes().getCurrency());

        // updating row
        return db.update(TABLE_APP_DETAIL, values, KEY_APP_ID + " = ?",
                new String[] { String.valueOf(entryModel.getAppId()) });
    }

    /**
     * Deleting a todo
     */


    // ------------------------ "tags" table methods ----------------//

    /**
     * Creating tag
     */

    /**
     * Updating a tag
     */


    /**
     * Deleting a tag
     */


    // ------------------------ "todo_tags" table methods ----------------//

    /**
     * Creating todo_tag
     */


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
