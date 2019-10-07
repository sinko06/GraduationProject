package com.example.gogoooma.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private String dbname;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        this.dbname = "DB"+name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE " + dbname + " ( ");
        sb.append(" TIME INTEGER PRIMARY KEY, ");
        sb.append(" SENDER TEXT, ");
        sb.append(" TYPE INTEGER, ");
        sb.append(" MESSAGE TEXT ) ");

        //SQLite Database로 쿼리실행
        db.execSQL(sb.toString());
    }

    public void deleteFriendsChat(){
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(" DROP TABLE IF EXISTS " + dbname + " ");
        } catch (Exception e){}
    }

    // application의 버전 올라가서 테이블 구조 변경 되었을 때 실행
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }

    public void addMessage(long time, String sender, long type, String msg){
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO "+ dbname +" ( ");
        sb.append(" TIME, SENDER, TYPE, MESSAGE ) ");
        sb.append(" VALUES ( ?, ?, ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                        time, sender, type, msg
                });
    }

    public List<MessageFormat> getAllMsg(){
        List<MessageFormat> msgs = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT TIME, SENDER, TYPE, MESSAGE FROM "+ dbname );
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while( cursor.moveToNext() ) {
            msgs.add(new MessageFormat("", cursor.getString(1), "",
                    cursor.getLong(2), cursor.getString(3), cursor.getLong(0)));
        }
        cursor.moveToFirst();
        cursor.close();

        return msgs;
    }
}
