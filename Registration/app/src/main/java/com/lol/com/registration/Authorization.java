package com.lol.com.registration;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Тарас on 04.08.2014.
 */
public class Authorization extends Activity
{
    private EditText login;
    private EditText pass;
    long id_elem;
    PersonDB db;
    SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        login = (EditText)findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.pass);

        db = new PersonDB(this);
        sqdb = db.getReadableDatabase();
    }
    public void auth(View v)
    {
        if(checkLogin(login.getText().toString(), pass.getText().toString()))
        {
            Intent intent = new Intent(this, EditProfile.class);
            intent.putExtra("id_elem", id_elem);
            startActivity(intent);
        }
        else
        {

        }

    }
    private Boolean checkLogin(String username, String password) throws SQLException
    {
        Cursor c = sqdb.rawQuery("SELECT login, pass FROM  PERSON ", null);
        //Cursor c = sqdb.query("PERSON", null, "login=? AND pass=?", new String[]{username,password}, null, null,null);
//        Log.d("My_Log","login - " + username);
//        Log.d("My_Log","pass - "  + password);
//        Log.d("My_Log","number of rows - "  + c.getCount());
        String login, pass;
        c.moveToFirst();
        int i=0;
        while (c.isAfterLast() == false)
        {
            login = c.getString(c.getColumnIndex("login"));
            pass = c.getString(c.getColumnIndex("pass"));
            Log.d("My_Log","login " + i +  "  " + "|"+login+ "|");
            Log.d("My_Log","pass  " + i + "  "+ "|"+pass+ "|");
            Log.d("My_Log","username " + i +  "  " + "|"+username+ "|");
            Log.d("My_Log","password " + i++ + "  "+ "|"+password+ "|");
            Log.d("My_Log","--------------------------");
            if( login.equals(username) && pass.equals(password))
            {
                Log.d("My_Log","yyyyyyyyyyyyyyyeeeeeah");
                id_elem = c.getPosition()+1;
                return true;
            }
            c.moveToNext();
        }


//        Log.d("My_Log","position " + Integer.toString(c.getPosition()));
//        Log.d("My_Log","index of id " + Integer.toString(c.getColumnIndex("rowid")));
//        Log.d("My_Log","index of login " + Integer.toString(c.getColumnIndex("login")));
//        Log.d("My_Log","index of pass " + Integer.toString(c.getColumnIndex("pass")));
//        Log.d("My_Log","index of nik " + Integer.toString(c.getColumnIndex("nik")));
//        Log.d("My_Log","index of mail " + Integer.toString(c.getColumnIndex("mail")));
//        if (c != null)
//        {
//            if(c.getCount() > 0)
//            {
//               // Log.d("My_Log"," pass - " + c.getString(c.getColumnIndex("pass")));
//                id_elem = c.getPosition();
//                return true;
//            }
//        }
        return false;
    }
}
