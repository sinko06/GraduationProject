package com.example.gogoooma.graduationproject;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManFragment extends Fragment {
    View v;
    DBUserHelper dbUserHelper;
    ListView friendsListView;
    FriendAdapter adapter;
    EditText findUserText;
    String friendsPhone;
    List<Friend> friends;
    ImageButton findUserButton;
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
        friendsListView = (ListView) v.findViewById(R.id.friendsListView);
        findUserText = (EditText) v.findViewById(R.id.findUserText);
        findUserButton = (ImageButton) v.findViewById(R.id.findUserButton);

        if(dbUserHelper == null)
            dbUserHelper = new DBUserHelper(getContext(),
                    "FRIENDSLIST", null, 1);
        friends = dbUserHelper.getAllFriends();

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

        return v;
    }

}
