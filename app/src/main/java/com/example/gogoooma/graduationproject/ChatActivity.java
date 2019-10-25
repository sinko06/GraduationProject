package com.example.gogoooma.graduationproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import java.io.*;
import java.net.*;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatActivity extends AppCompatActivity {
    private Emoji emoji;
    private EditText textField;
    private ImageButton sendButton;
    String real_message;
    List<MessageFormat> messageList;
    java.net.Socket socket;
    public static String uniqueId;
    String nowName;

    public static Friend friend;
    SharedPreferences auto;
    public static String sender;

    private Boolean hasConnection = false;

    private ListView messageListView;
    private MessageAdapter messageAdapter;

    private Socket mSocket;

    private DBHelper dbHelper;
    private DBRoomHelper dbRoomHelper;
    private DBEmotionHelper dbEmotionHelper;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        emoji = new Emoji();
        handler = new Handler();
        auto = getSharedPreferences("savefile", Activity.MODE_PRIVATE);
        sender = auto.getString("phone", null);

        try {
            mSocket = IO.socket("http://192.168.86.193:8080");
            //mSocket = IO.socket("http://" + SettingFragment.ipnum + ":2000");
        } catch (URISyntaxException e) {
        }
        friend = (Friend) getIntent().getSerializableExtra("friend");
        dbHelper = new DBHelper(ChatActivity.this,
                friend.getPhone(), null, 1);
        dbRoomHelper = new DBRoomHelper(getApplicationContext(),
                "TALKLIST", null, 1);
        dbEmotionHelper = new DBEmotionHelper(getApplicationContext(),
                "EMOTIONLIST", null, 1);

        uniqueId = UUID.randomUUID().toString();
        if(savedInstanceState != null){
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }

        if(hasConnection){

        }else {
            mSocket.connect();
            mSocket.on("chat message", onNewMessage);

            JSONObject userId = new JSONObject();
            try {
                userId.put("sender", sender);
                mSocket.emit("connect user", userId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        hasConnection = true;

        textField = findViewById(R.id.textField);
        sendButton = findViewById(R.id.sendButton);
        messageListView = findViewById(R.id.messageListView);

        messageList = new ArrayList<>();
        // 처음 들어왔을 때 DBHelper를 통해 대화목록 불러오기
        try {
            messageList = dbHelper.getAllMsg();
        }catch (Exception e){}
        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageList);
        messageListView.setAdapter(messageAdapter);
        messageListView.setSelection(messageAdapter.getCount() - 1);
        onTypeButtonEnable();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    public void onTypeButtonEnable(){
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String e_uniqueID;
                    String e_sender;
                    String e_receiver;
                    long e_type;
                    String e_data;
                    long e_time;
                    try {
                        e_uniqueID = data.getString("uniqueID");
                        e_sender = data.getString("sender");
                        e_receiver = data.getString("receiver");
                        e_type = data.getLong("type");
                        e_data = data.getString("data");
                        e_time = data.getLong("time");

                        MessageFormat format = new MessageFormat(friend.getName(), e_sender, e_receiver,
                                e_type, e_data, e_time);
                        messageAdapter.add(format);
                        // DBHelper sqlite에 넣기
                        dbHelper.addMessage(e_time, e_sender, e_type, e_data);
                        // 처음이라면 DBUserHelper에도 넣고 아니라면 시간 갱신
                        nowName = friend.getName();
                        ClientThread thread = new ClientThread();
                        thread.start();
                        try{
                            dbRoomHelper.addTalk(e_sender, friend.getName(), e_data, e_time);
                        }catch (Exception e){
                            dbRoomHelper.updateTalk(e_sender, e_data, e_time);
                        }

                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };

    String strScore;
    class ClientThread extends Thread{
        @Override
        public void run() {
            try{
                socket=new java.net.Socket();
                SocketAddress addr = new InetSocketAddress("192.168.86.193",2004);
                socket.connect(addr);

                DataOutputStream dout =new DataOutputStream(socket.getOutputStream());
                DataInputStream din=new DataInputStream(socket.getInputStream());

                dout.writeUTF(real_message);
                dout.flush();

                strScore = din.readUTF();//in.readLine();
                //System.out.println(str);
                // DB에 점수 갱신
                int tempScore = Math.round((Float.valueOf(strScore)));
                try {
                    dbEmotionHelper.addEmotion(nowName, tempScore);
                } catch (Exception e){
                    int score = dbEmotionHelper.getEmotion(nowName);
                    score = Math.round(((score * 8) + (tempScore*10)) / 18);
                    dbEmotionHelper.updateEmotion(nowName, score);
                }

                dout.close();
                din.close();
                socket.close();
            }

            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(View view){
        String message = textField.getText().toString().trim();
        //emoji.checkEmoji(message);
        real_message = emoji.checkEmoji(message);
        nowName = "me";
        ClientThread thread = new ClientThread();
        thread.start();
        ////////////////////////////

        if(TextUtils.isEmpty(message)){
            return;
        }
        textField.setText("");
        String m_receiver = friend.getPhone();
        long time = System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uniqueID", uniqueId);
            jsonObject.put("sender", sender);
            jsonObject.put("receiver", m_receiver);
            jsonObject.put("type", 0);
            jsonObject.put("data", message);
            jsonObject.put("time", time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("chat message", jsonObject);
        messageList.add(new MessageFormat(friend.getName(), sender, friend.getName(), 0, message, 0));
        messageAdapter.notifyDataSetChanged();

        dbHelper.addMessage(time, sender, 0, message);

        try{
            dbRoomHelper.addTalk(m_receiver, friend.getName(), message, time);
        }catch (Exception e){
            dbRoomHelper.updateTalk(m_receiver, message, time);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(isFinishing()){
            JSONObject userId = new JSONObject();
            try {
                userId.put("sender", sender);
                mSocket.emit("disconnect user", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.disconnect();
            mSocket.off("chat message", onNewMessage);
            sender = "";
            messageAdapter.clear();
        }else {
        }

    }


}
