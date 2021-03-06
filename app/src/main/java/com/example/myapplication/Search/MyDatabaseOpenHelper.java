package com.example.myapplication.Search;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String tableName = "container";

    public MyDatabaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tableName + "(name text)";
        try{
            db.execSQL(sql);
        }catch (SQLException e){

        }
    }

    public void insertName(SQLiteDatabase db, String name){
        db.beginTransaction();
        try{
            String sql = "insert into "+tableName + "(name)" + " values('" + name+"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
    }

    public void deleteAll(SQLiteDatabase db){
        db.beginTransaction();
        try{
            String sql = "delete from "+tableName;
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

}
