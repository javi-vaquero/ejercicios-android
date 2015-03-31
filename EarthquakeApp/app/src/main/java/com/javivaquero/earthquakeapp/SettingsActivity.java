package com.javivaquero.earthquakeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.javivaquero.earthquakeapp.fragments.SettingsFragment;
import com.javivaquero.earthquakeapp.services.DownloadEarthQuakesService;

/**
 * Created by javi-vaquero on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        AlarmManager alarmManager = (AlarmManager)getSystemService(this.ALARM_SERVICE);
        int	alarmType = AlarmManager.RTC;
        long timeOrLengthofWait = Integer.valueOf(sharedPreferences.getString(getString(R.string.PREF_UPDATE_INTERVAL), "1")) * 60 *1000;
        Intent intentToFire = new Intent(this, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intentToFire, 0);

        if(key.equals(getString(R.string.PREF_AUTO_UPDATE))){
            // Start/Stop auto updates
            if(sharedPreferences.getBoolean(getString(R.string.PREF_AUTO_UPDATE), false)){
                alarmManager.setRepeating(alarmType, 0, timeOrLengthofWait, alarmIntent);
            }
            else{
                alarmManager.cancel(alarmIntent);
            }

        }
        else if(key.equals(getString(R.string.PREF_UPDATE_INTERVAL))){
            // Change auto refresh interval
            alarmManager.setRepeating(alarmType, 0, timeOrLengthofWait, alarmIntent);

        }
    }
}
