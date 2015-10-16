package com.me.i8080.employee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.sql.SQLException;

import java.util.List;


/**
 * Created by rishabh.baid on 4/28/2015.
 */
public class UserEntryHistoryFragment extends Fragment implements View.OnClickListener {

    private UserHistoryDataSource datasource;
    private UserHistory mUserHistory;
    private OnUserEntryClickListener mListener;
    private List<UserHistory> values;
    private ListView listView;
    private View view;
    private UserEntryHistoryAdapter adapter;
    private OnScreenRoatationListener mOnScreenRoatationListener;

    public UserEntryHistoryFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnUserEntryClickListener) activity;
        mOnScreenRoatationListener = (OnScreenRoatationListener)activity;

        Log.i("log", "I am onAttach of UserEntryHistoryFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("log", "I am onCreate of UserEntryHistoryFragment");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("logs", "I am onActivityCreated of UserEntryHistoryFragment");

        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        mOnScreenRoatationListener.onScreenRotate();

        try {
            datasource = new UserHistoryDataSource(getActivity());
            datasource.open();
            values = datasource.getAllUserHistory();
            adapter = new UserEntryHistoryAdapter(getActivity(),values);
            listView.setAdapter(adapter);


            if(adapter.isEmpty()){
                Log.i("logs","Adater is empty!");
                TextView textView = (TextView) view.findViewById(R.id.helpText);
                textView.setVisibility(View.VISIBLE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onUserItemClick(values.get(position));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("log", "I am onCreateView of UserEntryHistoryFragment");
        view = inflater.inflate(R.layout.home_screen,container,false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                Log.i("log", "onItemLongClick....");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Click Confirm to Delete this entry!")
                        .setTitle("Delete");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        long _id = values.get(position).getId();
                        DeleteEntryTask deleteEntryTask = new DeleteEntryTask(UserEntryHistoryFragment.this);
                        deleteEntryTask.execute(String.valueOf(_id));
                        values.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("log", "I am onResume of UserEntryHistoryFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("log", "I am onDestroy of UserEntryHistoryFragment");
        if(datasource!=null) {
            datasource.close();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log", "I am onDestroyView of UserEntryHistoryFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("log", "I am onDestroy of UserEntryHistoryFragment");
    }

    public void updateAdapter() {
        Log.i("logs","Inside updateAdapter");
        adapter.notifyDataSetChanged();
    }

    public void setListener(OnUserEntryClickListener listener){
        mListener = listener;
    }

    public void setScreenRotateListener(OnScreenRoatationListener listener){
        mOnScreenRoatationListener = listener;
    }

    public interface OnUserEntryClickListener {
       public void onUserItemClick(UserHistory userHistory);
    }

    public interface OnScreenRoatationListener {
        public void onScreenRotate();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          case  R.id.listView:
            Bundle bundle = new Bundle();
            bundle.putSerializable("userHistory",mUserHistory);
            Fragment fragment = new QuestionnaireFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container,fragment)
                    .addToBackStack("Fragment")
                    .commit();
            break;

        }
    }
}
