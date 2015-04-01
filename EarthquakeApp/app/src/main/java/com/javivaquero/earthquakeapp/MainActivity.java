package com.javivaquero.earthquakeapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.javivaquero.earthquakeapp.managers.EarthQuakeAlarmManager;
import com.javivaquero.earthquakeapp.services.DownloadEarthQuakesService;
import com.javivaquero.earthquakeapp.tasks.DownloadEarthQuakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarthQuakesTask.AddEarthQuakeInterface {

    private final int PREFS_ACTIVITY = 0;
    private final String EARTHQUAKE_PREFS = "EARTHQUAKE_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //downloadEarthQuakes();

        checkToSetAlarm();
    }

    private void checkToSetAlarm() {
        String KEY = "LAUNCHED_BEFORE";
        SharedPreferences prefs = getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);
        if(!prefs.getBoolean(KEY, false)){
            //set alarm
            long interval =  getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            EarthQuakeAlarmManager.setAlarm(this,interval);
            //set LAUNCHED_BEFORE = true
            prefs.edit().putBoolean(KEY, true).apply();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivity(prefsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    private void  downloadEarthQuakes(){
        //DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this, this);
        //task.execute(getString(R.string.earthquakes_url));

       Intent download = new Intent(this, DownloadEarthQuakesService.class);
       startService(download);


    }
*/
    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t =  Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
