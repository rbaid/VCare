package com.me.i8080.employee;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by rishabh.baid on 5/8/2015.
 */
public class FetchPeopleListTask extends AsyncTask<String,Void,String[]> {
    private Context mContext;
    private String mName;
    PeopleListDataSource mDataSource;

    public FetchPeopleListTask(Context context) {

        mContext = context;
    }

    @Override
    protected String[] doInBackground(String... param) {
        mDataSource = new PeopleListDataSource(mContext);
        List<PeopleList> p;
        String[] result={""};

            File dbFile = mContext.getDatabasePath("peopleList.db");
            if(dbFile.exists()) {
                try {
                    Log.i("logs", "I am inside onCreate of doInBackground of FetchPeopleList... " );
                    mDataSource.read();
                    p = mDataSource.getAllPeopleList();
                    int size = p.size();
                    result = new String[size];
                    int count=0;
;                    for(PeopleList i : p) {
                        result[count] = i.toString();
                        count++;
                    }
                }
                catch (SQLException e) {
                    Log.e("logs", "Got it");
                    e.printStackTrace();
                }
                finally {
                    if(mDataSource!=null) {
                        mDataSource.close();
                    }
                }
            }
        Log.i("logs", "I am inside onCreate of doInBackground of FetchPeopleList and going to return... " );
                for(String s: result) {
                    Log.i("logs",s);
                }
          return result;
    }
}
