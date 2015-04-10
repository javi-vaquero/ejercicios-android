package com.javivaquero.earthquakeapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.javivaquero.earthquakeapp.database.EarthQuakeDB;
import com.javivaquero.earthquakeapp.fragments.EarthQuakeListFragment;
import com.javivaquero.earthquakeapp.fragments.EarthQuakesMapFragment;
import com.javivaquero.earthquakeapp.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends Activity {

    public static final String URL = "URL";

    TextView lblPlace;
    TextView lblMagnitude;
    TextView lblCoordinates;
    TextView lblDate;
    TextView lblUrl;
    //ImageView imgMap;

    private EarthQuakesMapFragment mapFragment;
    EarthQuakeDB earthquakeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        earthquakeDB = new EarthQuakeDB(this);

        mapFragment = ((EarthQuakesMapFragment) getFragmentManager().findFragmentById(R.id.mapFragment));

        Intent detailIntent = getIntent();
        String id = detailIntent.getStringExtra(EarthQuakeListFragment.EQ_ID);
        EarthQuake earthquake = earthquakeDB.getById(id);



        lblPlace = (TextView) findViewById(R.id.eqPlace);
        lblMagnitude = (TextView) findViewById(R.id.eqMagnitude);
        lblCoordinates = (TextView) findViewById(R.id.eqCoordinates);
        lblDate = (TextView) findViewById(R.id.eqDate);
        lblUrl = (TextView) findViewById(R.id.eqUrl);


        lblPlace.setText(earthquake.getPlace());
        lblMagnitude.setText(String.format("%.2f", earthquake.getMagnitude()));
        lblDate.setText(String.valueOf(earthquake.getDate()));
        lblCoordinates.setText(getString(R.string.depth, String.format("%.2f", earthquake.getCoords().getDepth())));
        lblUrl.setText(earthquake.getUrl());
        lblUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(DetailActivity.this, WebBrowserActivity.class);
                web.putExtra(URL, lblUrl.getText().toString());
                startActivity(web);
            }

        });
        showMap(earthquake);
    }

    private void showMap(EarthQuake earthquake) {
        List<EarthQuake> earthquakes = new ArrayList<>();
        earthquakes.add(earthquake);
        mapFragment.setEarthQuakes(earthquakes);
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

}
