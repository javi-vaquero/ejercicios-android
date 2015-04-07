package com.javivaquero.geolocationgoogleplayservices;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    Location location;
    private boolean connected;
    private TextView status;
    private TextView lastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connected = false;
        status = (TextView) findViewById(R.id.status);
        lastLocation = (TextView) findViewById(R.id.lastLocation);
        configure();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public void configure(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void getLocation(){
        if(connected) {
            location = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
            if (location != null) {
                lastLocation.setText("Lat: " + String.valueOf(location.getLatitude()) + ", " + "Lon: " + String.valueOf(location.getLongitude()));
            }
        }
    }

    public void launchListener(){
    }



    @Override
    public void onConnected(Bundle bundle) {
        connected = true;

    }

    @Override
    public void onConnectionSuspended(int i) {
        connected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        connected = false;

    }
}
