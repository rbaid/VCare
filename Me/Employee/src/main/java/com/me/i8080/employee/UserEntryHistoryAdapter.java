package com.me.i8080.employee;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rishabh.baid on 4/28/2015.
 */
public class UserEntryHistoryAdapter extends ArrayAdapter {
    private final Context context;
    private final List<UserHistory> values;

    public  UserEntryHistoryAdapter(Context context,List<UserHistory> values) {
        super(context,R.layout.row,values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView textView2_1 = (TextView) rowView.findViewById(R.id.v2_1);
        TextView textView2_2 = (TextView) rowView.findViewById(R.id.v2_2);

        long epochTime = values.get(position).getDate();
        Log.i("log"," "+  epochTime +" in getView of UserEntryHistoryAdapter ");
        Date date = new Date(epochTime);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        textView2_1.setText(sdf.format(date));
        textView2_2.setText(values.get(position).getDescription());

        return rowView;
    }


}

