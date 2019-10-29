package com.example.gogoooma.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBEmotionHelper extends SQLiteOpenHelper {
    private Context context;;

    public DBEmotionHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE IF NOT EXISTS EMOTIONLIST ( ");
        sb.append(" NAME TEXT PRIMARY KEY, ");
        sb.append(" SCORE INTEGER )");

        //SQLite Database로 쿼리실행
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addEmotion(String title, int happy){
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO EMOTIONLIST " +" ( ");
        sb.append(" NAME, SCORE ) ");
        sb.append(" VALUES ( ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                        title, happy
                });
    }

    public int getEmotion(String name){
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT SCORE FROM EMOTIONLIST");
        sb.append(" WHERE NAME = '" + name + "'");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        int score = 0;
        while( cursor.moveToNext() ) {
            score = cursor.getInt(0);
        }

        cursor.moveToFirst();
        cursor.close();
        return score;
    }

    public List<Emotion> getAllEmotion(){
        List<Emotion> emotions = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT NAME, SCORE FROM EMOTIONLIST");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while( cursor.moveToNext() ) {
            emotions.add(new Emotion(cursor.getString(0), cursor.getInt(1)));
        }
        cursor.moveToFirst();
        cursor.close();

        return emotions;
    }

    public void updateEmotion(String name, int score){
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE EMOTIONLIST SET SCORE = " + score);
        sb.append(" WHERE NAME = '" + name + "'");
        db.execSQL(sb.toString());
    }

}

