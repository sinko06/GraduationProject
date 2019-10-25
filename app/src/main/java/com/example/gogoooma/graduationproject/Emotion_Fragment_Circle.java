package com.example.gogoooma.graduationproject;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Emotion_Fragment_Circle extends Fragment {
    View v;
    private LineChart lineChart = null;
    private PieChart pieChart, pieChart2;

    public Emotion_Fragment_Circle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion__fragment__circle, container, false);

        pieChart = (PieChart)v.findViewById(R.id.pie_chart);
        pieChart2 = (PieChart)v.findViewById(R.id.pie_chart2);

        PieData pieData, pieData2;
        PieDataSet pieDataSet;
        ArrayList pieEntries;
        ArrayList PieEntryLabels;

        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(5f, "송교희"));
        pieEntries.add(new PieEntry(6f, "최승호"));
        //pieEntries.add(new PieEntry(6f, "김영일"));

        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(13f);
        pieDataSet.setSliceSpace(5f);


        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(5f, "송교희"));
        pieEntries.add(new PieEntry(6f, "최승호"));
        //pieEntries.add(new PieEntry(6f, "김영일"));

        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(13f);
        pieDataSet.setSliceSpace(5f);

        return v;
    }

}
