package com.example.gogoooma.graduationproject;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    View v;
    private Button logout_button, update_button, reset_button;
    private FirebaseAuth mfirebaseAuth;
    private DBUserHelper dbUserHelper;
    TextView my_profile_name, my_profile_phone;
    SharedPreferences auto;
    String myPhone, myName;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auto = getActivity().getSharedPreferences("savefile", Activity.MODE_PRIVATE);
        myPhone = auto.getString("phone", null);
        myName = auto.getString("name", null);

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        getPermission();
        init();

        mfirebaseAuth = FirebaseAuth.getInstance();
        logout_button = (Button)v.findViewById(R.id.button_logout);
        update_button = (Button)v.findViewById(R.id.button_update_friendslist);
        reset_button = (Button)v.findViewById(R.id.button_reset);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfirebaseAuth.signOut();
                Log.v("로그아웃","로그아웃 완료");

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전화번호부에 있는 친구 목록
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String[] projection = new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContext().getContentResolver().
                        query(uri, projection, null, null, null);

                // 친구 목록 업데이트
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                dbUserHelper = new DBUserHelper(getContext(),
                        "FRIENDSLIST", null, 1);
//                dbUserHelper.testDB();

                while (cursor.moveToNext()){
                    try {
//                        Log.e("getKey", cursor.getString(0));
                        final String key = cursor.getString(0).
                                replaceAll("-", "").replace("\\+82", "0");
                        myRef.orderByKey().equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String value = dataSnapshot.child(key).getValue().toString();
                                    value = value.substring(1, value.length()-1);
                                    String[] valueArray = value.split(", ");
                                    String _name = null;
                                    for(int i=0; i<valueArray.length; i++){
                                        if(valueArray[i].indexOf("name=") != -1){
                                            _name = valueArray[i].substring(5);
                                            break;
                                        }
                                    }
                                    //key 값을 sqlite로 보내기
                                    try {
                                        Log.e("getPhone", myPhone);
                                        if(!myPhone.equals(key))
                                            dbUserHelper.addFriend(_name, key);
                                    }catch (Exception e){}
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }catch(Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbUserHelper==null)
                    dbUserHelper = new DBUserHelper(getContext(),
                            "FRIENDSLIST", null, 1);
                List<Friend> deleteFriendsList = dbUserHelper.getAllFriends();
                DBHelper dbHelper;
                for(int i=0; i<deleteFriendsList.size(); i++) {
                    dbHelper = new DBHelper(getContext(), deleteFriendsList.get(i).getPhone(),
                            null, 1);
                    dbHelper.deleteFriendsChat();
                    Log.e("deleted", "completed");
                }
                dbUserHelper.resetFriendsList();
            }
        });





        return v;
    }

    public void getPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {

        }
    }

    public void init(){
        my_profile_name = (TextView) v.findViewById(R.id.my_profile_name);
        my_profile_phone = (TextView) v.findViewById(R.id.my_profile_phone);
        my_profile_phone.setText(myPhone);
        my_profile_name.setText(myName);
    }
}
