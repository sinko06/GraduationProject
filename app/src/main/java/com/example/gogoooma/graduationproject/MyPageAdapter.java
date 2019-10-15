package com.example.gogoooma.graduationproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {

    public MyPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Emotion_Fragment_Total();
            case 1: return new Emotion_Fragment_temp();
            case 2: return new Emotion_Fragment_Circle();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
        case 0: return "Total";
        case 1: return "Line";
        case 2: return "Circle";
        default: return null;
    }
    }
}
