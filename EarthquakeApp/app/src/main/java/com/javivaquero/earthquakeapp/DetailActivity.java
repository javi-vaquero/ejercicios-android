package com.javivaquero.earthquakeapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.javivaquero.earthquakeapp.fragments.EarthQuakeFragment;


public class DetailActivity extends ActionBarActivity {

    TextView lblID;
    TextView lblPlace;
    TextView lblMagnitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent detailIntent = getIntent();

        lblID = (TextView) findViewById(R.id.eqID);
        lblPlace = (TextView) findViewById(R.id.eqPlace);
        lblMagnitude = (TextView) findViewById(R.id.eqMagnitude);

        lblID.setText(detailIntent.getStringExtra(EarthQuakeFragment.EQ_ID));
        lblPlace.setText(detailIntent.getStringExtra(EarthQuakeFragment.EQ_PLACE));
        double mag = detailIntent.getDoubleExtra(EarthQuakeFragment.EQ_MAGNITUDE,-100);
        lblMagnitude.setText(String.valueOf(mag));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
