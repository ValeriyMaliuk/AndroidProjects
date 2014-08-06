package com.lol.com.person;

import android.os.Bundle;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Тарас on 03.08.2014.
 */
public class MyFrag extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment, null);
        TextView brand   = (TextView) v.findViewById(R.id.brand);
        TextView version = (TextView) v.findViewById(R.id.version);
        TextView sdk     = (TextView) v.findViewById(R.id.sdk);
        TextView time    = (TextView) v.findViewById(R.id.time);
        brand.setText("BRAND - " + Build.BRAND);
        version.setText("Anroid ver - " + Build.VERSION.RELEASE);
        sdk.setText("SDK ver - " + Build.VERSION.SDK_INT);
        Calendar today = Calendar.getInstance();
        time.setText("Date - " + today.get(Calendar.DAY_OF_MONTH) + "-"
                               + today.get(Calendar.MONTH)        + "-"
                               + today.get(Calendar.YEAR));
        return v;
    }
}
