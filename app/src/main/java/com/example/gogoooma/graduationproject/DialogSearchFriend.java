package com.example.gogoooma.graduationproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogSearchFriend {
    Context context;
    Button yes, no;
    EditText editText;
    String friendsPhone;
    DBUserHelper dbUserHelper;

    public DialogSearchFriend(Context context) {
        this.context = context;
    }

    public void callFunction() {
        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.activity_dialog_search_friend);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        yes = (Button) dlg.findViewById(R.id.man_search_yes);
        no = (Button) dlg.findViewById(R.id.man_search_no);
        editText = (EditText) dlg.findViewById(R.id.man_search_edit);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 친구 찾기 소스코드
                friendsPhone = editText.getText().toString();
                // firebase에 그 번호가 있다면
                friendsPhone = friendsPhone.replaceAll("-", "");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.orderByKey().equalTo(friendsPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // dbUserHelper에 저장하고
                            String value = dataSnapshot.child(friendsPhone).getValue().toString();
                            value = value.substring(1, value.length() - 1);
                            String[] valueArray = value.split(", ");
                            String _name = null;
                            for (int i = 0; i < valueArray.length; i++) {
                                if (valueArray[i].contains("name=")) {
                                    _name = valueArray[i].substring(5);
                                    break;
                                }
                            }
                            try {
                                SharedPreferences auto;
                                auto = context.getSharedPreferences("savefile", Activity.MODE_PRIVATE);
                                final String sender = auto.getString("phone", null);

                                if(dbUserHelper == null)
                                    dbUserHelper = new DBUserHelper(context,
                                            "FRIENDSLIST", null, 1);

                                if (!sender.equals(friendsPhone))
                                    dbUserHelper.addFriend(_name, friendsPhone);
                            } catch (Exception e) {
                            }
                            ManFragment.friends.add(new Friend(_name, friendsPhone, null));
                            ManFragment.adapter.notifyDataSetChanged();
                            Toast.makeText(context, _name + "님이 추가되었습니다", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(context, "일치하는 사용자가 없습니다", Toast.LENGTH_SHORT).show();
                        dlg.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소했습니다", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

        dlg.show();

    }
}
