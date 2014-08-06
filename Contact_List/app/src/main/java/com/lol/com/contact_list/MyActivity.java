package com.lol.com.contact_list;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity {

    private ListView List_For_Store_Contact_Info;
    ArrayList<String> Contact_Info = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        List_For_Store_Contact_Info=(ListView)findViewById(R.id.list);


// fill your Array List with appropriate values from Contacts List:
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        startManagingCursor(cursor);
        while (cursor.moveToNext())
        {
            Contact_Info.add(" ID "+cursor.getString(0)+" NAME "+cursor.getString(1)+" PHONE "+cursor.getString(2));
        }

        List_For_Store_Contact_Info.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Contact_Info));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)     {
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
}
