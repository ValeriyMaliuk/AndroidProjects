package com.lol.com.registration;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Тарас on 04.08.2014.
 */
public class Registration extends Activity
{
    private EditText Login;
    private EditText Pass;
    private EditText Nik;
    private EditText Email;
    long id_elem;
    PersonDB db;
    SQLiteDatabase sqdb;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Login = (EditText) findViewById(R.id.login);
        Pass = (EditText) findViewById(R.id.pass);
        Nik = (EditText) findViewById(R.id.nik);
        Email = (EditText) findViewById(R.id.email);

        // Инициализируем наш класс-обёртку
        db = new PersonDB(this);
        sqdb = db.getWritableDatabase();
    }
    public void savetoDB()
    {
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        String login = Login.getText().toString();
        String pass  = Pass.getText().toString();
        String nik   = Nik.getText().toString();
        String email = Email.getText().toString();

        Log.d("My_log", "--- Insert in PERSON: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение
        cv.put("login", login);
        cv.put("pass", pass);
        cv.put("nik", nik);
        cv.put("mail", email);

        id_elem = sqdb.insert("PERSON", null, cv);
        Log.d("My_log", "ID------"+id_elem);
    }
    public void regist(View v)
    {
        Log.d("My_log", "regist pressed");
        savetoDB();
        Log.d("My_lod", "afted saving");
        Intent intent = new Intent(this, EditProfile.class);
        intent.putExtra("id_elem", id_elem);
        startActivity(intent);
    }
    public void clearDB(View v)
    {
        sqdb.delete("PERSON", null, null);
    }

}
