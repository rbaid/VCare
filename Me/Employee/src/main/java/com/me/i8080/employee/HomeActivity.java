package com.me.i8080.employee;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.sql.SQLException;



public class HomeActivity extends Activity implements DatePickerFragment.setDate,
         UserEntryHistoryFragment.OnUserEntryClickListener, UserEntryHistoryFragment.OnScreenRoatationListener {

    MenuItem mMenuItem;
    static Menu mMenu;
    private boolean flag = false;
    private boolean onScreenRotateFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("logs", "onCreate of HomeActivity");
        invalidateOptionsMenu();
        setContentView(R.layout.activity_home);

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);

        UserEntryHistoryFragment fragment = (UserEntryHistoryFragment)getFragmentManager().
                findFragmentByTag("UserEntryHistoryFragment");
        if(fragment!=null) {
                onScreenRotateFlag = true;

            fragment.setListener(this);
        }
        else{
            UserEntryHistoryFragment frag = new UserEntryHistoryFragment();
            frag.setListener(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, "UserEntryHistoryFragment")
                    .commit();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//
////        UserEntryHistoryFragment fragment = (UserEntryHistoryFragment)getFragmentManager().
////                findFragmentByTag("UserEntryHistoryFragment");
////        if(fragment!=null &&( onScreenRotateFlag || fragment.isVisible())) {
////            flag = true;
////        }
////        else{
////            flag = false;
////        }
////        Log.i("logs","onCreateOptionMenu" + flag + "onScreenRotate" + onScreenRotateFlag);
////
////        if(!flag ) {
////            MenuItem item = menu.findItem(R.id.action_add);
////            item.setVisible(false);
////            item = menu.findItem(R.id.action_stats);
////            item.setVisible(false);
////        }
////         mMenu = menu;
//
//         return true;
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        QuestionnaireFragment questionnaireFragment = (QuestionnaireFragment)getFragmentManager().
                findFragmentByTag("QuestionnaireFragment");
        PieChartFragment pieChartFragment = (PieChartFragment) getFragmentManager().
                findFragmentByTag("PieChartFragment");

        if(questionnaireFragment==null && pieChartFragment==null ) {
            onScreenRotateFlag = true;
        }
        else {
            onScreenRotateFlag = false;
        }
        UserEntryHistoryFragment fragment = (UserEntryHistoryFragment)getFragmentManager().
                findFragmentByTag("UserEntryHistoryFragment");
        if(fragment!=null  &&( onScreenRotateFlag || fragment.isVisible())) {
            flag = true;
            Log.i("logs","onPrepareOptionMenu " + flag + "onScreenRotate" + onScreenRotateFlag);
        }
        else{
            flag = false;
            Log.i("logs","onPrepareOptionMenu " + flag);
        }
        if(!flag) {
            MenuItem item = menu.findItem(R.id.action_add);
            item.setVisible(false);
            item = menu.findItem(R.id.action_stats);
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_stats) {
            onScreenRotateFlag = false;
            FetchRatingTask fetchRatingTask = new FetchRatingTask();
            fetchRatingTask.execute();

            return true;
        }
        if(id == R.id.action_add) {
            onScreenRotateFlag = false;
            mMenuItem = item;

            QuestionnaireFragment fragment = new QuestionnaireFragment();

            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.setListener(this);

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, "QuestionnaireFragment")
                    .addToBackStack("AddQuestionnaireFragment")
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPieChartFragment(int[] rating) {
        PieChartFragment pieChartFragment = new PieChartFragment();
        Bundle b = new Bundle();
        b.putIntArray("Rating",rating);
        pieChartFragment.setArguments(b);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, pieChartFragment, "PieChartFragment")
                .addToBackStack("AddPieChartFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setDate(int year, int month, int day) {
        QuestionnaireFragment questionnaireFragment = (QuestionnaireFragment) getFragmentManager().
                findFragmentByTag("QuestionnaireFragment");
        if(questionnaireFragment==null) {

            questionnaireFragment = (QuestionnaireFragment) getFragmentManager().
                    findFragmentByTag("AddQuestionnaireFragment");
        }
        if(questionnaireFragment!=null) {
            Log.i("logs","I am setDate of HomeActivity and I am not null!");
            questionnaireFragment.setDateView(year,month,day);
        }
        else {
            Log.e("logs","I am setDate of HomeActivity and I am null");
        }
    }

    @Override
    public void onUserItemClick(UserHistory userHistory) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("userHistory",userHistory);
        Fragment fragment = new QuestionnaireFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container,fragment,"QuestionnaireFragment")
                .addToBackStack("QuestionnaireFragmentStack")
                .commit();
    }

    @Override
    public void onScreenRotate() {

//        onScreenRotateFlag = true;
        Log.i("logs", "onScreenRotate method " + onScreenRotateFlag);
    }


    public class FetchRatingTask extends AsyncTask<Void,Void,int[]> {
        ProgressDialog progressDialog;
        AnalyticData analyticData;

        @Override
        protected int[] doInBackground(Void... params) {
            int [] rating = new int[5];
            Log.i("logs","I am doInBackground of FetchRatingTask...");
            UserHistoryDataSource userHistoryDataSource = new UserHistoryDataSource(HomeActivity.this);
            try {
                userHistoryDataSource.open();
                rating = userHistoryDataSource.getRatingDistribution();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            analyticData = AnalyticData.getInstance();
            analyticData.setRating(rating);
            getAnalysis();

            return rating;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setTitle(" ");
            progressDialog.setMessage("Serving Pies...");
            progressDialog.setIcon(R.drawable.ic_stats);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(int[] ints) {
            Log.i("logs","I am onPostExecute of FetchRatingTask...");
            if(progressDialog!=null) {
                progressDialog.dismiss();
            }
            int[] rating = ints;

            showPieChartFragment(rating);
        }
        PeopleListDataSource mDataSource;
        private Void getAnalysis() {
            Context mContext = HomeActivity.this;
            mDataSource = new PeopleListDataSource(mContext);

            File dbFile = mContext.getDatabasePath("peopleList.db");
            if(dbFile.exists()) {
                try {
                    Log.i("logs", "I am inside onCreate of doInBackground of FetchAnalysisTask... ");
                    mDataSource.read();

                    analyticData.setHappyMap(mDataSource.getHappy());
                    analyticData.setSadMap(mDataSource.getSad());
                    analyticData.setRememberMap(mDataSource.getRemember());
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
            return null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }
}