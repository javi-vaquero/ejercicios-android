package com.javivaquero.geolocation;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.javivaquero.geolocation.listeners.LocationListener;


public class MainActivity extends ActionBarActivity implements LocationListener.AddLocationInterface {

    TextView lblLatitude;
    TextView lblLongitude;
    TextView lblAltitude;
    TextView lblSpeed;

    private String provider;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLatitude = (TextView) findViewById(R.id.Latitude);
        lblLongitude = (TextView) findViewById(R.id.Longitude);
        lblAltitude = (TextView) findViewById(R.id.Altitude);
        lblSpeed = (TextView) findViewById(R.id.Speed);

        getLocationProvider();
        listenLocationChanges();

    }

    private void listenLocationChanges() {
        int t = 5000;       //ms
        int distance = 5;   //m

        LocationListener locationListener = new LocationListener(this);
        locationManager.requestLocationUpdates(provider, t, distance, locationListener);
    }

    private void getLocationProvider(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);


        provider = locationManager.getBestProvider(criteria, true);
        Log.d("GEO", provider);
    }


    @Override
    public void addLocation(Location location) {
        lblLatitude.setText(String.valueOf(location.getLatitude()));
        lblLongitude.setText(String.valueOf(location.getLongitude()));
        lblAltitude.setText(String.valueOf(location.getAltitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));
    }
}
