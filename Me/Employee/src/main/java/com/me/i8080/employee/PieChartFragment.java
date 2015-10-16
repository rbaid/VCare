package com.me.i8080.employee;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import java.util.Map;


/**
 * Created by rishabh on 9/5/15.
 */
public class PieChartFragment extends SimpleFragment {

    public static Fragment newInstance() {
        return new PieChartFragment();
    }

    private PieChart mChart;
    private PieChart mChartHappy;
    private Typeface tf;

    AnalyticData analyticData = AnalyticData.getInstance();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        mChart = (PieChart) v.findViewById(R.id.pieChart1);
        mChart.setDescriptionTextSize(14f);

        mChart.setDescription("");
//      Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mChart.setCenterTextTypeface(tf);
        mChart.setCenterText("Rating \n Distribution");
        mChart.setCenterTextSize(18f);
        mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTextSize(12f);

        mChart.setData(generatePieData());

        LinearLayout happyview = (LinearLayout) v.findViewById(R.id.AnalysisHappy);
        LinearLayout sadview = (LinearLayout) v.findViewById(R.id.AnalysisSad);
        LinearLayout rememberview = (LinearLayout) v.findViewById(R.id.AnalysisRemember);


        Map<String,Integer> mapHappy = analyticData.getHappyMap();
        Map<String,Integer> mapSad = analyticData.getSadMap();
        Map<String,Integer> mapRemember = analyticData.getRememberMap();


        int count = 1;
        char ch = ' ' ;

        if(mapHappy!=null) {
            for (Map.Entry<String, Integer> entry : mapHappy.entrySet()) {
                TextView textView = new TextView(getActivity());
                if(entry.getValue()>0 && entry.getKey().trim().length()>0) {
                    if(entry.getValue()>1) {
                        ch = 's';
                    }
                    textView.setText(count + "." + " " + entry.getKey() + " :: " + entry.getValue() + " Time" + ch );
                    textView.setTextSize(15f);
                    textView.setVisibility(View.VISIBLE);
                    textView.setPadding(20,20,20,20);
                    happyview.addView(textView);
                    ch=' ';
                    count++;
                }
            }
        }
        ch = ' ';
        count = 1;
        if(mapSad!=null) {
            for (Map.Entry<String, Integer> entry : mapSad.entrySet()) {
                TextView textView = new TextView(getActivity());
                if(entry.getValue()>0 && entry.getKey().trim().length()>0) {
                    if(entry.getValue()>1) {
                        ch = 's';
                    }
                    textView.setText(count + "." + " " + entry.getKey() + " :: " + entry.getValue() + " Time" + ch );
                    textView.setTextSize(15f);
                    textView.setVisibility(View.VISIBLE);
                    textView.setPadding(20,20,20,20);
                    sadview.addView(textView);
                    ch = ' ';
                    count++;
                }
            }
        }

        ch = ' ';
        count=1;
        if(mapRemember!=null) {
            for (Map.Entry<String, Integer> entry : mapRemember.entrySet()) {
                TextView textView = new TextView(getActivity());
                if(entry.getValue()>0 && entry.getKey().trim().length()>0) {
                    if(entry.getValue()>1) {
                        ch = 's';
                    }
                    textView.setText(count + "." + " " + entry.getKey() + " :: " + entry.getValue() + " Time" + ch );
                    textView.setTextSize(15f);
                    textView.setVisibility(View.VISIBLE);
                    textView.setPadding(20,20,20,20);;
                    rememberview.addView(textView);
                    count++;
                    ch = ' ';
                }
            }
        }


        return v;
    }
}
