package com.me.i8080.employee;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by rishabh.baid on 5/1/2015.
 */
public class UpdateDatabaseTask extends AsyncTask<UserHistory,Void,Void> {
    private UserHistoryDataSource mDataSource;
    private Context mContext;
    private long mid;
    private long mDate;
    private float mRating;
    private String mDescription;
    private String mHappy;
    private String mSad;
    private String mRemember;
    private String mMode;
    private long _id;

    public UpdateDatabaseTask(Context context, String mode, long id) {

        mContext = context;
        mMode = mode;
        _id = id;
    }

    @Override
    protected Void doInBackground(UserHistory... userHistory) {
        mDataSource = new UserHistoryDataSource(mContext);

        mDate = userHistory[0].getDate();
        mDescription = userHistory[0].getDescription();
        mHappy = userHistory[0].getHappy();
        mSad = userHistory[0].getSad();
        mRemember = userHistory[0].getRemember();
        mRating = userHistory[0].getRating();

        try {
            mDataSource.open();
            if(mMode.equals("Update")) {
                Log.i("UpdateDatabaseTask", "Update in database");
                mDataSource.updateUserHistory(_id,mDate,mDescription,mHappy,mSad,mRemember,mRating);
            }
            else {
                Log.i("UpdateDatabaseTask", "Add to database");
                mDataSource.createUserHistory(mDate,mDescription,mHappy,mSad,mRemember,mRating);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(mDataSource!=null) {
                mDataSource.close();
            }
        }
        return null;
    }
}
