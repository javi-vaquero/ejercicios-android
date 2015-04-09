package com.javivaquero.geolocationgoogleplayservices;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    Location location;
    private boolean connected;
    private boolean listening;
    private TextView status;
    private TextView lastLocation;
    private Button btnGetLocation;
    private Button btnLaunchListener;
    private LinearLayout positionLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connected = false;
        listening = false;
        status = (TextView) findViewById(R.id.status);
        lastLocation = (TextView) findViewById(R.id.lastLocation);
        btnGetLocation = (Button) findViewById(R.id.btnGetLocation);
        positionLayout = (LinearLayout) findViewById(R.id.layoutLocations);
        btnLaunchListener = (Button) findViewById(R.id.btnStartListener);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
        btnLaunchListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchListener();
            }
        });
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
        if(!listening) {
            if (connected) {
                LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(10000);
                mLocationRequest.setFastestInterval(3000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationServices.FusedLocationApi.requestLocationUpdates(
                        googleApiClient, mLocationRequest, this);

                listening=true;
                btnLaunchListener.setText(R.string.stop_listener);
            }
        }
        else{
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            listening = false;
            btnLaunchListener.setText(R.string.start_listener);

        }
    }



    @Override
    public void onConnected(Bundle bundle) {
        connected = true;
        status.setText("Connected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        connected = false;
        status.setText("Connection Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        connected = false;
        status.setText("Connection Failed");

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation.setText("Lat: " + String.valueOf(location.getLatitude()) + ", " + "Lon: " + String.valueOf(location.getLongitude()));
        TextView newTV = new TextView(this);
        newTV.setText("Lat: " + String.valueOf(location.getLatitude()) + ", " + "Lon: " + String.valueOf(location.getLongitude()));

        positionLayout.addView(newTV);
    }
}
