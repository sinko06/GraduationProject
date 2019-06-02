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
        this.dbname = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE " + dbname + " ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TYPE TEXT, ");
        sb.append(" MESSAGE TEXT ) ");

        //SQLite Database로 쿼리실행
        db.execSQL(sb.toString());
    }

    // application의 버전 올라가서 테이블 구조 변경 되었을 때 실행
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }

    public void addMessage(String type, String msg){
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO "+ dbname +" ( ");
        sb.append(" TYPE, MESSAGE ) ");
        sb.append(" VALUES ( ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                        type, msg
                });
    }

    public List getAllMsg(){
        List msgs = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT MESSAGE FROM "+ dbname );
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while( cursor.moveToNext() ) {
            msgs.add(cursor.getString(0));
        }

        return msgs;
    }
}
