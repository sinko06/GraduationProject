package com.example.gogoooma.graduationproject;


import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    View v;
    private DBHelper dbHelper;
    ListView listView;
    ArrayAdapter<String> adapter;
    SharedPreferences auto;

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_music, container, false);
        auto = getActivity().getSharedPreferences("savefile", Activity.MODE_PRIVATE);
        TextView tv1 = (TextView) v.findViewById(R.id.music_profile_name);
        TextView tv2 = (TextView) v.findViewById(R.id.music_profile_phone);

        tv1.setText(auto.getString("name", null));
        tv2.setText(auto.getString("phone", null));

        return v;
    }

}
