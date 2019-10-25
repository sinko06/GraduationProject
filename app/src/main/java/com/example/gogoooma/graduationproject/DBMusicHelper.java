package com.example.gogoooma.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBMusicHelper extends SQLiteOpenHelper {
    private Context context;;

    public DBMusicHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE IF NOT EXISTS MUSICLIST ( ");
        sb.append(" TITLE TEXT PRIMARY KEY, ");
        sb.append(" HAPPY FLOAT)");

        //SQLite Database로 쿼리실행
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addMusic(String title, float happy){
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO MUSICLIST" +" ( ");
        sb.append(" TITLE, HAPPY) ");
        sb.append(" VALUES ( ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                        title, happy
                });
    }

    public List<Music> getAllMusic(){
        List<Music> musicList = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT TITLE, HAPPY FROM MUSICLIST " );
        sb.append(" ORDER BY TITLE ASC " );
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while( cursor.moveToNext() ) {
            musicList.add(new Music(cursor.getString(0), cursor.getFloat(1)));
        }
        cursor.moveToFirst();
        cursor.close();

        return musicList;
    }

}

