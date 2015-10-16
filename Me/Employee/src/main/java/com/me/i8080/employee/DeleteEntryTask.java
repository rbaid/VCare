package com.me.i8080.employee;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by rishabh.baid on 5/9/2015.
 */
public class DeleteEntryTask extends AsyncTask<String,Void,Void> {
        private UserEntryHistoryFragment mContext;
        private String mName;
        private long id;

        UserHistoryDataSource mDataSource;

        public DeleteEntryTask(UserEntryHistoryFragment context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(String... name) {
            mDataSource = new UserHistoryDataSource(mContext.getActivity());

            mName = name[0];
            id = Long.parseLong(mName);
            Log.i("logs", "I am inside doInBackground of Delete Entry Task and name is " + id);


            try {
                mDataSource.open();
                mDataSource.deleteUserHistory(id);


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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mContext.updateAdapter();
    }


}
