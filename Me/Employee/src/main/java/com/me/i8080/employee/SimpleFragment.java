package com.me.i8080.employee;

        import android.app.Activity;
        import android.app.Fragment;
        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.data.PieData;
        import com.github.mikephil.charting.data.PieDataSet;
        import com.github.mikephil.charting.utils.ColorTemplate;

        import java.util.ArrayList;

public abstract class SimpleFragment extends Fragment{

    private Typeface tf;
    private Context mContext;
    private AnalyticData analyticData = AnalyticData.getInstance();
    private int[] rating;

    public SimpleFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        Log.i("logs", "I am onCreateView of SimpleFragment...");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected PieData generatePieData() {

        int count = 5;

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> entries1 = new ArrayList<Entry>();

        xVals.add("*****");
        xVals.add("****");
        xVals.add("***");
        xVals.add("**");
        xVals.add("*");

        int totalRating = 0;
        rating = analyticData.getRating();

        Log.i("logs","Rating in PieData " );
        for(int r:rating) {
            Log.i("logs","Rating in PieData " + r);
            totalRating += r;
        }
        if(totalRating==0){
            totalRating =1;
        }

        for(int i = count-1; i >= 0; i--) {
            xVals.add("entry" + (i+1));
            entries1.add(new Entry(((rating[i]*1.0f)/totalRating)*100, i));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.COLORFUL_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(xVals, ds1);
        d.setValueTypeface(tf);

        return d;
    }

}

