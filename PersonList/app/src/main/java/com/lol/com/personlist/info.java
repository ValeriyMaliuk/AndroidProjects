package com.lol.com.personlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class info extends FragmentActivity
{
    private EditText Fname;
    private EditText Lname;
    private EditText Bday;
    int id_elem;

    PersonDB db;
    SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Fname = (EditText) findViewById(R.id.fname);
        Lname = (EditText) findViewById(R.id.lname);
        Bday  = (EditText) findViewById(R.id.bday);

        // Инициализируем наш класс-обёртку
        db = new PersonDB(this);
        sqdb = db.getReadableDatabase();
        loadfromBD();
    }
    private void loadfromBD()
    {
        id_elem = getIntent().getExtras().getInt("row_id");

        Cursor c = sqdb.query("PERSON", null, null, null, null, null, null);

       if (c.moveToFirst())
       {
            for(int i=1; i<=id_elem; i++)
                c.moveToNext();

            Fname.setText(c.getString(c.getColumnIndex("fname")));
            Lname.setText(c.getString(c.getColumnIndex("lname")));
            Bday.setText(c.getString(c.getColumnIndex("bday")));
        } else
            Log.d("My_Log", "N0 rows");
        c.close();
    }
    public void saveEdit(View view)
    {
        Log.d("mylog", "saving");
        ContentValues cv = new ContentValues();
        cv.put("fname", Fname.getText().toString());
        cv.put("lname", Lname.getText().toString());
        sqdb.update("PERSON", cv, "id = ?",new String[] { Integer.toString(id_elem) });
        sqdb.close();
    }
}