package com.example.gogoooma.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Talk> {
    TextView name, msg, time;
    public ChatListAdapter(Context context, int resource, List<Talk> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Talk talk = getItem(position);

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.chatlist_row, parent, false);
        name = (TextView) convertView.findViewById(R.id.chatlist_name);
        msg = (TextView) convertView.findViewById(R.id.chatlist_msg);
        time = (TextView) convertView.findViewById(R.id.chatlist_time);

        name.setText(talk.getFriendName());

        String lastMsg = talk.getLastMsg();
        if(lastMsg.length() > 10){
            lastMsg = lastMsg.substring(0, 10) + "...";
        }
        msg.setText(lastMsg);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 HH시 mm분");
        String timeText = dateFormat.format(new Date(talk.getTime()));

        time.setText(timeText);

        return convertView;
    }
}
