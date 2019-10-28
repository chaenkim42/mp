package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    String db_name;
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        db_name = name;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableName = db_name;
        //테이블 생성할때 if not exists 라는 조건문을 넣어줄 수 있다. (존재하지않을때 테이블 생성)
        //_id는 SQLite에서 내부적으로 관리되는 내부 id이다.
        String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, email text, password text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {

    }

    //기존 사용자가 디비를 사용하고있어서 그걸 업데이트(수정)해주는경우
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //여기 예제에서는 그냥 삭제했지만 보통의 Alter로 수정을 한다거나 할 수도 있다.

//        if (newVersion > 1) {
//            String tableName = "user";
//            sqLiteDatabase.execSQL("drop table if exists " + tableName);
//
//            String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, email text, password text)";
//            sqLiteDatabase.execSQL(sql);
//        }
    }

    public ArrayList<String> showAll(){
        ArrayList<String> list = new ArrayList<>();
        String select_all = "select email from " + db_name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_all, null);

        int i=0;
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return list;
    }
}
