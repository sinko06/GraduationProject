package com.example.gogoooma.graduationproject;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Emotion_Fragment_temp extends Fragment {
    View v;
    private LineChart lineChart = null;
    private PieChart pieChart, pieChart2;

    public Emotion_Fragment_temp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion_chartline, container, false);
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

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            lineChart.setHardwareAccelerationEnabled(false);
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "기분");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(3);
        lineDataSet.setCircleColor(Color.parseColor("#b386f4"));
        lineDataSet.setCircleHoleColor(Color.WHITE);
        lineDataSet.setColor(Color.parseColor("#b386f4"));//선
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        //lineDataSet.setGradientColor(R.color.graph_gradient, Color.WHITE);
        //lineDataSet.setFillDrawable(drawable);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.gradient_emotion_graph);
            lineDataSet.setFillDrawable(drawable);
        }
        else {
            lineDataSet.setFillColor(getResources().getColor(R.color.sexy));
        }
        //lineDataSet.setFillColor(getResources().getColor(R.color.sexy));



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

        return v;
    }

}
