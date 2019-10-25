package com.example.gogoooma.graduationproject;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Emotion_Fragment extends Fragment {
    View v;
    TextView hiNameText;
    SharedPreferences auto;

    public Emotion_Fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion_, container, false);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        MyPageAdapter myPagerAdapter = new MyPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        hiNameText = (TextView) v.findViewById(R.id.textHiName);
        auto = getActivity().getSharedPreferences("savefile", Activity.MODE_PRIVATE);
        String myName = auto.getString("name", null);
        hiNameText.setText(myName + "님, 안녕하세요");


        TabLayout tablayout = (TabLayout) v.findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewPager);

        return v;
    }

}