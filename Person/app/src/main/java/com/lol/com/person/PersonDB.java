package com.lol.com.person;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class PersonDB extends SQLiteOpenHelper {

    public PersonDB(Context context) {
        // конструктор суперкласса
        super(context, "PersonDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG_TAG", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table PERSON ("
                + "id integer AUTO_INCREMENT"
                + "age integer,"
                + "bday text,"
                + "fname text,"
                + "lname text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}