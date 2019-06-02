package com.example.gogoooma.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend> {
    List<Friend> friends;
    View v;

    public FriendAdapter(Context context, int resource, List<Friend> objects) {
        super(context, resource, objects);
        friends = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.my_friends, null);
        Friend friend = friends.get(position);

        TextView nameText = (TextView) v.findViewById(R.id.profile_name);
        TextView phoneText = (TextView) v.findViewById(R.id.profile_phone);

        nameText.setText(friend.getName());
        phoneText.setText(friend.getPhone());

        return v;
    }
}
