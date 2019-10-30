package com.example.gogoooma.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private PieChart pieChart;
    private DBEmotionHelper dbEmotionHelper;

    public Emotion_Fragment_Circle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion__fragment__circle, container, false);

        pieChart = (PieChart)v.findViewById(R.id.pie_chart);
        dbEmotionHelper = new DBEmotionHelper(getContext(),
                "EMOTIONLIST", null, 1);
        List<Emotion> emotions;
        emotions = dbEmotionHelper.getAllEmotion();


        PieData pieData;
        PieDataSet pieDataSet;
        ArrayList pieEntries;

        pieEntries = new ArrayList<>();
        for(int i=0; i<emotions.size(); i++) {
            if(!emotions.get(i).getName().equals("me")) {
                pieEntries.add(new PieEntry((float)emotions.get(i).getScore(), emotions.get(i).getName()));
            }
        }

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
