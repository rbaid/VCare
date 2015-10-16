package com.me.i8080.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh.baid on 4/22/2015.
 */
public class UserHistoryDataSource  {
    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_DATE,MySQLiteHelper.COLUMN_DESCRIPTION,
                                    MySQLiteHelper.COLUMN_HAPPY,MySQLiteHelper.COLUMN_SAD,MySQLiteHelper.COLUMN_REMEMBER,
                                        MySQLiteHelper.COLUMN_RATING};

    public UserHistoryDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserHistory createUserHistory(long date, String description, String happy, String sad,
                                         String remember, float rating ) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DATE,date);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION,description);
        values.put(MySQLiteHelper.COLUMN_HAPPY,happy);
        values.put(MySQLiteHelper.COLUMN_SAD,sad);
        values.put(MySQLiteHelper.COLUMN_REMEMBER,remember);
        values.put(MySQLiteHelper.COLUMN_RATING, rating);

        long insertId = db.insert(MySQLiteHelper.TABLE_USERHISTORY, null, values);
        Cursor cursor = db.query(MySQLiteHelper.TABLE_USERHISTORY, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null,null,null,null);
        cursor.moveToFirst();
        UserHistory newUserHistory = cursorToUserHistory(cursor);
        cursor.close();

        return newUserHistory;
    }

    public void updateUserHistory(long id,long date, String description, String happy, String sad,
                                         String remember, float rating ) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DATE,date);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION,description);
        values.put(MySQLiteHelper.COLUMN_HAPPY,happy);
        values.put(MySQLiteHelper.COLUMN_SAD,sad);
        values.put(MySQLiteHelper.COLUMN_REMEMBER,remember);
        values.put(MySQLiteHelper.COLUMN_RATING,rating);

        Log.i("logs", "I am inside updateUserHistory of UserHistoryDataSource" + id);

        long insertId = db.update(MySQLiteHelper.TABLE_USERHISTORY, values, "_id" + "=" + id, null);
//        Cursor cursor = db.query(MySQLiteHelper.TABLE_USERHISTORY, allColumns,
//                MySQLiteHelper.COLUMN_ID + " = " + insertId, null,null,null,null);
//        cursor.moveToFirst();
//        UserHistory newUserHistory = cursorToUserHistory(cursor);
//        cursor.close();

//        return newUserHistory;
    }

    public void deleteUserHistory(long id) {
//        long id = userHistory.getId();
        Log.i("logs", "Deleted ID: " + id);
        db.delete(MySQLiteHelper.TABLE_USERHISTORY, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<UserHistory> getAllUserHistory() {
        List<UserHistory> userHistory = new ArrayList<UserHistory>();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_USERHISTORY,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_DATE + " DESC");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            UserHistory user = cursorToUserHistory(cursor);
            userHistory.add(user);
            cursor.moveToNext();
        }
        cursor.close();

            return userHistory;
    }

    public int[] getRatingDistribution() {
        List<UserHistory> userHistory = new ArrayList<UserHistory>();
        int[] result = new int[5];
        float rating;

        Cursor cursor = db.query(MySQLiteHelper.TABLE_USERHISTORY,
                allColumns,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            UserHistory user = cursorToUserHistory(cursor);
            rating = user.getRating();
            if(rating<2.0) {
                result[0]+=1;
            }
            else if(rating<3.0) {
                result[1]+=1;
            }
            else if(rating<4.0) {
                result[2]+=1;
            }
            else if(rating<5.0) {
                result[3]+=1;
            }
            else {
                result[4]+=1;
            }
            cursor.moveToNext();
        }
             return result;
    }

    private UserHistory cursorToUserHistory(Cursor cursor) {
        UserHistory userHistory = new UserHistory();
        userHistory.setId(cursor.getLong(0));
        userHistory.setDate(cursor.getLong(1));
        userHistory.setDescription(cursor.getString(2));
        userHistory.setHappy(cursor.getString(3));
        userHistory.setSad(cursor.getString(4));
        userHistory.setRemember(cursor.getString(5));
        userHistory.setRating(cursor.getInt(6));

        return userHistory;
    }

}
