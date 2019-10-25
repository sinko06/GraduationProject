package com.example.gogoooma.graduationproject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Emotion_Fragment_Total extends Fragment {
    View v;
    TextView scoretv, text2, cardtv1, cardtv2, cardtv3;
    CardView c1,c2,c3;
    private DBEmotionHelper dbEmotionHelper;


    public Emotion_Fragment_Total() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emotion_total, container, false);
        dbEmotionHelper = new DBEmotionHelper(getContext(),
                "EMOTIONLIST", null, 1);
        int score = dbEmotionHelper.getEmotion("me");
        scoretv = (TextView) v.findViewById(R.id.emotion_total_score1);
        scoretv.setText(score+"");
        ImageView gifpy = (ImageView) v.findViewById(R.id.imageView);
        Glide.with(getContext())
                .load(R.drawable.giphy)
                .apply(new RequestOptions().placeholder(R.drawable.giphy))
                .into(new DrawableImageViewTarget(gifpy));
        text2 = (TextView) v.findViewById(R.id.emotion_total_text2);
        cardtv1 = (TextView) v.findViewById(R.id.tvcard1);
        cardtv2 = (TextView) v.findViewById(R.id.tvcard2);
        cardtv3 = (TextView) v.findViewById(R.id.tvcard3);
        c1 = (CardView) v.findViewById(R.id.card1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = cardtv1.getText().toString()+" 노래";
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/results?search_query=" + id));
                startActivity(webIntent);
            }
        });
        c2 = (CardView) v.findViewById(R.id.card2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = cardtv2.getText().toString()+" 노래";
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/results?search_query=" + id));
                startActivity(webIntent);
            }
        });
        c3 = (CardView) v.findViewById(R.id.card3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = cardtv3.getText().toString()+" 노래";
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/results?search_query=" + id));
                startActivity(webIntent);
            }
        });
        if(100 >= score && score > 60) {
            text2.setText("오늘 기분이 좋으시군요!");
            cardtv1.setText("기분 좋을 때");
            cardtv2.setText("신나는");
            cardtv3.setText("밝은");
        }
        else if(60 >= score && score > 40) {
            text2.setText("오늘 기분이 그럭저럭이시군요");
            cardtv1.setText("기분전환용");
            cardtv2.setText("편안한");
            cardtv3.setText("평화로운");
        }
        else {
            text2.setText("오늘 기분이 좋지 않으시군요..");
            cardtv1.setText("우울할 때");
            cardtv2.setText("힘이 나는");
            cardtv3.setText("지치고 힘들 때");
        }

        return v;
    }

}

