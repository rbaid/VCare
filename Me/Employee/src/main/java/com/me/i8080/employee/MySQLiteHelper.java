package com.me.i8080.employee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rishabh.baid on 4/22/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERHISTORY = "userHistory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_HAPPY = "happy";
    public static final String COLUMN_SAD = "sad";
    public static final String COLUMN_REMEMBER = "remember";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_RATING = "rating";

    private static final String DATABASE_NAME = "userHistory.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table "
            + TABLE_USERHISTORY
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_DATE+ " integer, "
            + COLUMN_DESCRIPTION+ " text, "
            + COLUMN_HAPPY+ " text, "
            + COLUMN_SAD + " text, "
            + COLUMN_REMEMBER + " text, "
            + COLUMN_RATING+ " integer" +
            "  );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERHISTORY);
        onCreate(db);
    }
}
