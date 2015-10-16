package com.me.i8080.employee;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by rishabh.baid on 5/8/2015.
 */
public class UpdatePeopleListTask extends AsyncTask<String,Void,Void> {
        private Context mContext;
        private String mName;
        PeopleListDataSource mDataSource;

        public UpdatePeopleListTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(String... count) {
            mDataSource = new PeopleListDataSource(mContext);

            String Happy[] = parse(count[0]);
            String Sad[] = parse(count[1]);
            String Remember[] = parse(count[2]);

            Log.i("logs", "I am inside doInBackground of UpdatePeopleListTask and name is " + count );

                try {
                    mDataSource.open();

                      for(String iterator : Happy) {
                            if(!mDataSource.isDataInDB(iterator.trim())) {
                                mDataSource.createPeopleList(iterator.trim(),1,0,0);
                            }
                            else {
                                mDataSource.updatePeopleList(iterator.trim(),1,0,0);
                            }
                     }

                    for(String iterator : Sad) {
                        if(!mDataSource.isDataInDB(iterator.trim())) {
                            mDataSource.createPeopleList(iterator.trim(),0,1,0);
                        }
                        else {
                            mDataSource.updatePeopleList(iterator.trim(),0,1,0);
                        }
                    }

                    for(String iterator : Remember) {
                        if(!mDataSource.isDataInDB(iterator.trim())) {
                            mDataSource.createPeopleList(iterator.trim(),0,0,1);
                        }
                        else {
                            mDataSource.updatePeopleList(iterator.trim(),0,0,1);
                        }
                    }

                }  catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if(mDataSource!=null) {
                    mDataSource.close();
                }
            }
            return null;
        }

        private String[] parse(String name) {
            Log.i("logs", "I am inside parse and name is " + name);
            String result[] = name.split(",");

            return result;
        }
    }
