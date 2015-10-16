package com.me.i8080.employee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rishabh.baid on 5/8/2015.
 */
public class PeopleListHelper extends SQLiteOpenHelper {

    public static final String TABLE_PEOPLELIST = "peopleList";
    public static final String COLUMN_BUDDYLIST = "buddy";
    public static final String COLUMN_HAPPYCOUNT = "happy";
    public static final String COLUMN_SADCOUNT= "sad";
    public static final String COLUMN_REMEMBERCOUNT = "remember";


    private static final String DATABASE_NAME = "peopleList.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table "
                    + TABLE_PEOPLELIST
                    + "("
                    + " _id integer primary key autoincrement, "
                    + COLUMN_BUDDYLIST + " text, "
                    + COLUMN_HAPPYCOUNT + " int, "
                    + COLUMN_SADCOUNT + " int,"
                    + COLUMN_REMEMBERCOUNT + " int"
                    + "  );";


    public PeopleListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("logs", "I am inside onCreate of PeopleListHelper... " + DATABASE_CREATE );
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLELIST);
        onCreate(db);
    }
}
