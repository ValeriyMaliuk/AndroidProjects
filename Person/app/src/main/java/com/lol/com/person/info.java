package com.lol.com.person;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

public class info extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        EditText fn = (EditText)findViewById(R.id.Fname);
        EditText ln = (EditText)findViewById(R.id.Lname);
        EditText bd = (EditText)findViewById(R.id.date);
        EditText age = (EditText)findViewById(R.id.age);

        fn.setText(getIntent().getExtras().getString("username"));
        ln.setText(getIntent().getExtras().getString("userlname"));
        bd.setText(getIntent().getExtras().getString("userbd"));
        age.setText(getIntent().getExtras().getString("age"));
    }
}