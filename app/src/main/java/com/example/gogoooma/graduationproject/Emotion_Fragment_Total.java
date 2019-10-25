package com.example.gogoooma.graduationproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;


/**
 * A simple {@link Fragment} subclass.
 */
public class Emotion_Fragment_Total extends Fragment {
    View v;

    public Emotion_Fragment_Total() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion_total, container, false);
        ImageView gifpy = (ImageView) v.findViewById(R.id.imageView);
        Glide.with(getContext())
                .load(R.drawable.giphy)
                .apply(new RequestOptions().placeholder(R.drawable.giphy))
                .into(new DrawableImageViewTarget(gifpy));



        return v;
    }

}

