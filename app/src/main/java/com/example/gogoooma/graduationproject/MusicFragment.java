package com.example.gogoooma.graduationproject;


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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    View v;
    private DBHelper dbHelper;
    ListView listView;
    ArrayAdapter<String> adapter;

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_music, container, false);
        Button button1 = (Button) v.findViewById(R.id.createDB);
        Button button2 = (Button) v.findViewById(R.id.button2);
        Button button3 = (Button) v.findViewById(R.id.button3);
        listView = (ListView) v.findViewById(R.id.listview1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(getContext(),
                        "helloDB", null, 1);
                dbHelper.testDB();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) v.findViewById(R.id.edit1);
                String msg = e1.getText().toString();
                if(dbHelper == null)
                    dbHelper = new DBHelper(getContext(),
                            "helloDB", null, 1);
                //dbHelper.addMessage("text", msg);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper == null)
                    dbHelper = new DBHelper(getContext(),
                            "helloDB", null, 1);
                List msgs = dbHelper.getAllMsg();
                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, msgs);
                listView.setAdapter(adapter);
            }
        });

        return v;
    }

}
