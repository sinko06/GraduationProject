package com.example.gogoooma.graduationproject;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManFragment extends Fragment implements View.OnClickListener {
    View v;
    Animation fab_open, fab_close, rotate_forward, rotate_backward;
    Boolean isFabOpen = false;
    FloatingActionButton fab, fab1, fab2;
    DBUserHelper dbUserHelper;
    ListView friendsListView;
    LinearLayout man_find_layout;
    EditText man_find_edit;
    Button man_find_button;
    public static FriendAdapter adapter;
    //EditText findUserText;
    String friendsPhone;
    public static List<Friend> friends;
    List<Friend> allFriendList;
    //ImageButton findUserButton;
    SharedPreferences auto;
    String sender;

    public ManFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        auto = getActivity().getSharedPreferences("savefile", Activity.MODE_PRIVATE);
        sender = auto.getString("phone", null);

        v = inflater.inflate(R.layout.fragment_man, container, false);
        initFab();
        friendsListView = (ListView) v.findViewById(R.id.friendsListView);
        if (dbUserHelper == null)
            dbUserHelper = new DBUserHelper(getContext(),
                    "FRIENDSLIST", null, 1);
        friends = dbUserHelper.getAllFriends();
        allFriendList = new ArrayList<>();
        allFriendList.addAll(friends);

        Log.e("getNum", allFriendList.size()+"");

        adapter = new FriendAdapter(getContext(),
                R.layout.my_friends, friends);
        friendsListView.setAdapter(adapter);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Friend friend = friends.get(position);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("friend", friend);
                startActivity(intent);
            }
        });

        return v;
    }

    public void initFab() {
        man_find_layout = (LinearLayout) v.findViewById(R.id.man_find_layout);
        man_find_edit = (EditText) v.findViewById(R.id.man_find_edit);
        man_find_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int s, int i1, int i2) {
                String findWord = man_find_edit.getText().toString();
                friends.clear();
                for(int i=0; i<allFriendList.size(); i++){
                    if(allFriendList.get(i).getPhone().contains(findWord) ||
                            allFriendList.get(i).getName().contains(findWord)) {
                        friends.add(allFriendList.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        man_find_button = (Button) v.findViewById(R.id.man_find_button);
        man_find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friends.clear();
                friends.addAll(allFriendList);
                adapter.notifyDataSetChanged();
                man_find_edit.setText("");
                man_find_layout.setVisibility(View.GONE);
            }
        });

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) v.findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(v.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(v.getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate_backward);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                // add user
                DialogSearchFriend dlg = new DialogSearchFriend(getContext());
                dlg.callFunction();
                break;
            case R.id.fab2:
                // search friend
                man_find_layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            fab1.hide();
            fab2.hide();
            Log.d("Raj", "close");

        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab1.show();
            fab2.show();

            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    /*
        findUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsPhone = findUserText.getText().toString();
                // firebase에 그 번호가 있다면
                friendsPhone = friendsPhone.replaceAll("-","");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.orderByKey().equalTo(friendsPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            // dbUserHelper에 저장하고
                            String value = dataSnapshot.child(friendsPhone).getValue().toString();
                            value = value.substring(1, value.length()-1);
                            String[] valueArray = value.split(", ");
                            String _name = null;
                            for(int i=0; i<valueArray.length; i++){
                                if(valueArray[i].contains("name=")){
                                    _name = valueArray[i].substring(5);
                                    break;
                                }
                            }
                            try {
                                if (!sender.equals(friendsPhone))
                                    dbUserHelper.addFriend(_name, friendsPhone);
                                // friendsListView에 추가
                                friends.add(new Friend(_name, friendsPhone, null));
                                adapter.notifyDataSetChanged();
                            } catch (Exception e){}
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        */


}
