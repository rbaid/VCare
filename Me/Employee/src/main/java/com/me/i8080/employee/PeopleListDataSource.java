package com.me.i8080.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rishabh.baid on 5/8/2015.
 */
public class PeopleListDataSource {

    private SQLiteDatabase db;
    private PeopleListHelper dbHelper;
    private String[] allColumns = {PeopleListHelper.COLUMN_BUDDYLIST };

    public PeopleListDataSource(Context context) {
        dbHelper = new PeopleListHelper(context);
    }

    public void open() throws SQLException {
        Log.i("logs", "I am inside open method of PeopleListDataSource");
        db = dbHelper.getWritableDatabase();
    }
    public void read() throws SQLException {
        db = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPeopleList(String name, int happy, int sad, int remember ) {
        Log.i("logs", "I am inside createPeopleList of PeopleListDataSource and name is " + name);

        int happyCount = happy;
        int sadCount = sad;
        int rememberCount = remember;

        ContentValues values = new ContentValues();
        values.put(PeopleListHelper.COLUMN_BUDDYLIST,name);
        values.put(PeopleListHelper.COLUMN_HAPPYCOUNT,happyCount);
        values.put(PeopleListHelper.COLUMN_SADCOUNT,sadCount);
        values.put(PeopleListHelper.COLUMN_REMEMBERCOUNT,rememberCount);


        long insertId = db.insert(PeopleListHelper.TABLE_PEOPLELIST,null,values);
//        Cursor cursor = db.query(PeopleListHelper.TABLE_PEOPLELIST, allColumns,
//                MySQLiteHelper.COLUMN_ID + " = " + insertId, null,null,null,null);
//        cursor.moveToFirst();
//        PeopleList newPeopleList = cursorToPeopleList(cursor);
//        cursor.close();

        return ;
    }

    public void updatePeopleList(String name, int happy, int sad, int remember ) {
        Log.i("logs", "I am inside createPeopleList of PeopleListDataSource and name is " + name);

        int happyCount = happy;
        int sadCount = sad;
        int rememberCount = remember;

        Cursor cursor = db.query(PeopleListHelper.TABLE_PEOPLELIST,new String[]{PeopleListHelper.COLUMN_HAPPYCOUNT,
                PeopleListHelper.COLUMN_SADCOUNT,
        PeopleListHelper.COLUMN_REMEMBERCOUNT},"buddy" + " = '" + name + "'",null,null,null,null);
        cursor.moveToFirst();
        ContentValues values = new ContentValues();
        //values.put(PeopleListHelper.COLUMN_BUDDYLIST,name);
        values.put(PeopleListHelper.COLUMN_HAPPYCOUNT,happyCount+cursor.getInt(
                cursor.getColumnIndexOrThrow(PeopleListHelper.COLUMN_HAPPYCOUNT)));

        values.put(PeopleListHelper.COLUMN_SADCOUNT,sadCount+cursor.getInt(
                cursor.getColumnIndexOrThrow(PeopleListHelper.COLUMN_SADCOUNT)));

        values.put(PeopleListHelper.COLUMN_REMEMBERCOUNT,rememberCount+cursor.getInt(
                cursor.getColumnIndexOrThrow(PeopleListHelper.COLUMN_REMEMBERCOUNT)));

        db.update(PeopleListHelper.TABLE_PEOPLELIST,values,PeopleListHelper.COLUMN_BUDDYLIST + " = '" + name + "'",null);
    }

    public List<PeopleList> getAllPeopleList() {

        List<PeopleList> PeopleList = new ArrayList<PeopleList>();

        Cursor cursor = db.query(PeopleListHelper.TABLE_PEOPLELIST,
                allColumns,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            PeopleList user = cursorToPeopleList(cursor);
            PeopleList.add(user);
            cursor.moveToNext();
        }
        cursor.close();

        return PeopleList;
    }

    public Map<String,Integer> getHappy() {

        Map<String,Integer> result = new LinkedHashMap<>();
        Cursor cursor = db.query(PeopleListHelper.TABLE_PEOPLELIST,new String[]{PeopleListHelper.COLUMN_BUDDYLIST,
                PeopleListHelper.COLUMN_HAPPYCOUNT},null,null,null,null,PeopleListHelper.COLUMN_HAPPYCOUNT + " DESC","6");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            result.put(cursor.getString(0),cursor.getInt(1));
            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }
    public Map<String,Integer> getSad() {

        Map<String,Integer> result = new LinkedHashMap<>();
        Cursor cursor = db.query(PeopleListHelper.TABLE_PEOPLELIST,new String[]{PeopleListHelper.COLUMN_BUDDYLIST,
                PeopleListHelper.COLUMN_SADCOUNT},null,null,null,null,PeopleListHelper.COLUMN_SADCOUNT + " DESC","6");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            result.put(cursor.getString(0),cursor.getInt(1));
            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }
    public Map<String,Integer> getRemember() {

        Map<String,Integer> result = new LinkedHashMap<>();
        Cursor cursor = db.query(PeopleListHelper.TABLE_PEOPLELIST,new String[]{PeopleListHelper.COLUMN_BUDDYLIST,
                PeopleListHelper.COLUMN_REMEMBERCOUNT},null,null,null,null,PeopleListHelper.COLUMN_REMEMBERCOUNT + " DESC","6");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            result.put(cursor.getString(0),cursor.getInt(1));
            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }

    private PeopleList cursorToPeopleList(Cursor cursor) {
        PeopleList PeopleList = new PeopleList();
        PeopleList.setName(cursor.getString(0));

        return PeopleList;
    }

    public  boolean isDataInDB(String name) {

        boolean flag = true;

        Log.i("logs", "I am inside isDataInDB of PeopleListDataSource and name is " + name);

        String Query = "Select * from peopleList where buddy"
                + " = ?";
        Log.e("logs",Query);
        Cursor cursor = db.rawQuery(Query, new String[] {name});
        if(cursor==null || cursor.getCount() <= 0){
            flag = false;
        }
        cursor.close();

        return flag;
    }
}
