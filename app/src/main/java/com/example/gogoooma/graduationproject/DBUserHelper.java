package com.example.gogoooma.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.bumptech.glide.util.ExceptionCatchingInputStream;

import java.util.ArrayList;
import java.util.List;

public class DBUserHelper extends SQLiteOpenHelper {
    private Context context;


    public DBUserHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE FRIENDSLIST ( ");
        sb.append(" PHONE TEXT PRIMARY KEY, ");
        sb.append(" NAME TEXT ) ");

        //SQLite Database로 쿼리실행
        db.execSQL(sb.toString());
    }

    // application의 버전 올라가서 테이블 구조 변경 되었을 때 실행
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void resetFriendsList(){
        SQLiteDatabase db = getWritableDatabase();
        String clearDBQuery = " DELETE FROM FRIENDSLIST ";
        db.execSQL(clearDBQuery);
    }

    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }

    public void addFriend(String phone, String name) {
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO FRIENDSLIST ( ");
        sb.append(" PHONE, NAME ) ");
        sb.append(" VALUES ( ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                        phone, name
                });
    }

    public List<Friend> getAllFriends(){
        List<Friend> friends = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT PHONE, NAME FROM FRIENDSLIST " );
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while( cursor.moveToNext() ) {
            friends.add(new Friend(cursor.getString(0), cursor.getString(1), null));
        }
        cursor.moveToFirst();
        cursor.close();
        return friends;
    }
}
