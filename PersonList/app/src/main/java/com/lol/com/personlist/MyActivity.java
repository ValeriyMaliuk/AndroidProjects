package com.lol.com.personlist;

import android.app.ListActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyActivity extends ListActivity {

    private ArrayAdapter<String> adapter;
    PersonDB db;
    SQLiteDatabase sqdb;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new PersonDB(this);
        sqdb = db.getWritableDatabase();

        fillDB();
        initList();
    }
    private void initList()
    {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,makePersonList());
        setListAdapter(adapter);
        AdapterView.OnItemClickListener itemListener =
                new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(MyActivity.this, info.class);
                int row_id = Integer.parseInt(getId(((TextView) v).getText().toString()));
                intent.putExtra("row_id", row_id);
                startActivity(intent);
            }
        };
        getListView().setOnItemClickListener(itemListener);
    }
    private  ArrayList makePersonList()
    {
        ArrayList<String> Persons = new ArrayList();
        Cursor c = sqdb.query("PERSON", null, null, null, null, null, null);

        if (c.moveToFirst())
        {
            int fnameColIndex = c.getColumnIndex("fname");
            int lnameColIndex = c.getColumnIndex("lname");
            int idColIndex = c.getColumnIndex("id");
            do{
                Persons.add("id="+c.getInt(idColIndex)+" "+ c.getString(fnameColIndex)+" "+c.getString(lnameColIndex));
            } while (c.moveToNext());
        } else
            Log.d("My_tag", "N0 rows");
        c.close();
        return Persons;
    }
    public void fillDB()
    {
        if(a<1) {
            sqdb.delete("PERSON", null, null);
            for (int i = 0; i < 20; i++) {
                Person p = new Person(i, "fname"+i, "lname"+i, "bday"+i);
                insertPerson(p);
            }
            a++;
        }
    }
    private void insertPerson(Person p){
        ContentValues cv = new ContentValues();

        cv.put("id",    p.getId());
        cv.put("fname", p.getFname());
        cv.put("lname", p.getLname());
        cv.put("bday",  p.getBday());

        sqdb.insert("PERSON", null, cv);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        sqdb.close();
        db.close();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        initList();
        super.onRestart();
    }
    private String getId(String str)
    {
        int first = str.indexOf("id=");
        str = str.substring(first + 3, str.indexOf(" "));
        return str;
    }
}

