package com.example.gogoooma.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatActivity extends AppCompatActivity {
    private EditText textField;
    private ImageButton sendButton;
    List<MessageFormat> messageList;

    public static String uniqueId;

    public static String sender;
    private String receiver;
    private String type = "text";

    private Boolean hasConnection = false;

    private ListView messageListView;
    private MessageAdapter messageAdapter;

    private Thread thread2;

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            mSocket = IO.socket("http://58.141.187.7:2000");
        } catch (URISyntaxException e) {
        }
        sender = getIntent().getStringExtra("username");

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
        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageList);
        messageListView.setAdapter(messageAdapter);

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
                    String e_type;
                    String e_data;
                    try {
                        e_uniqueID = data.getString("uniqueID");
                        e_sender = data.getString("sender");
                        e_receiver = data.getString("receiver");
                        e_type = data.getString("type");
                        e_data = data.getString("data");

                        MessageFormat format = new MessageFormat(e_uniqueID, e_sender, e_receiver,
                                e_type, e_data);
                        messageAdapter.add(format);

                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };

    public void sendMessage(View view){
        String message = textField.getText().toString().trim();
        if(TextUtils.isEmpty(message)){
            return;
        }
        textField.setText("");
        String m_receiver = "temp";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uniqueID", uniqueId);
            jsonObject.put("sender", sender);
            jsonObject.put("receiver", m_receiver);
            jsonObject.put("type", type);
            jsonObject.put("data", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("chat message", jsonObject);
        messageList.add(new MessageFormat(uniqueId, sender, m_receiver, type, message));
        messageAdapter.notifyDataSetChanged();
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
