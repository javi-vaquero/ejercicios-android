package com.javivaquero.earthquakeapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by javi-vaquero on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);
    }
}
