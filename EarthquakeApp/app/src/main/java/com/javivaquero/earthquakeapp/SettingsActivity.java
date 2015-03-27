package com.javivaquero.earthquakeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.javivaquero.earthquakeapp.fragments.SettingsFragment;

/**
 * Created by javi-vaquero on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //deprecated
        //addPreferencesFromResource(R.xml.userpreferences);

        //Display the fragment as the main content
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
