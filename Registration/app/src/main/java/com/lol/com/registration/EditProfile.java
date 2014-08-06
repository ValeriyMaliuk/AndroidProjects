package com.lol.com.registration;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Тарас on 04.08.2014.
 */
public class EditProfile extends Activity
{
    private EditText Nik;
    private EditText Email;

    PersonDB db;
    SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Nik= (EditText) findViewById(R.id.login);
        Email = (EditText) findViewById(R.id.email);

        // Инициализируем наш класс-обёртку
        db = new PersonDB(this);
        sqdb = db.getReadableDatabase();
        loadfromBD();
    }
    public void loadfromBD()
    {
        long id_elem = getIntent().getExtras().getLong("id_elem");
        //Cursor c = sqdb.rawQuery("SELECT * FROM " + "PERSON" + " WHERE rowid =" + "'"+10+"'",null);

//        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = sqdb.query("PERSON", null, null, null, null, null, null);


//        // ставим позицию курсора на первую строку выборки
        if (c.moveToFirst())
        {
            for(int i=1; i<id_elem; i++)
                c.moveToNext();

            int NikColIndex = c.getColumnIndex("nik");
            int EmailColIndex = c.getColumnIndex("mail");

            Log.d("My_Log","login - " + c.getString(0));
            Log.d("My_Log","pass - "  + c.getString(1));
            Log.d("My_Log","nik - "   + c.getString(2));
            Log.d("My_Log","mail - "  + c.getString(3));
            Nik.setText(c.getString(NikColIndex));
            Email.setText(c.getString(EmailColIndex));
        } else
            Log.d("My_Log", "N0 rows");
        c.close();
    }
}
