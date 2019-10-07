package com.example.gogoooma.graduationproject;


import android.graphics.Color;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Emotion_Fragment extends Fragment {
    View v;
    private LineChart lineChart = null;
    private PieChart pieChart, pieChart2;

    public Emotion_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion_, container, false);
        lineChart = (LineChart)v.findViewById(R.id.chart);

        EmotionMarkerView marker = new EmotionMarkerView(v.getContext(),R.layout.markerviewtext);
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 10));
        entries.add(new Entry(2, 20));
        entries.add(new Entry(3, 0));
        entries.add(new Entry(4, 40));
        entries.add(new Entry(5, 30));

        LineDataSet lineDataSet = new LineDataSet(entries, "기분");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#666666"));
        lineDataSet.setCircleHoleColor(Color.DKGRAY);
        lineDataSet.setColor(Color.parseColor("#666666"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.rgb(97,12,177));

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(2000, Easing.EaseInCubic);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.invalidate();

        pieChart = (PieChart)v.findViewById(R.id.pie_chart);
        pieChart2 = (PieChart)v.findViewById(R.id.pie_chart2);

        PieData pieData, pieData2;
        PieDataSet pieDataSet;
        ArrayList pieEntries;
        ArrayList PieEntryLabels;

        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(2f, "한등희"));
        pieEntries.add(new PieEntry(4f, "최승호"));
        pieEntries.add(new PieEntry(6f, "김영일"));

        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(13f);
        pieDataSet.setSliceSpace(5f);


        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(4f, "한등희"));
        pieEntries.add(new PieEntry(3f, "최승호"));
        pieEntries.add(new PieEntry(6f, "김영일"));

        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(13f);
        pieDataSet.setSliceSpace(5f);
        return v;
    }

}
