package com.javivaquero.earthquakeapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.javivaquero.earthquakeapp.database.EarthQuakeDB;
import com.javivaquero.earthquakeapp.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    EarthQuakeDB db;
    ArrayList<EarthQuake> earthQuakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        db = new EarthQuakeDB(this);
        earthQuakes = new ArrayList<>();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            double magnitude = new Double(prefs.getString(getString(R.string.PREF_MIN_MAGNITUDE), "0.0"));
            earthQuakes.clear();
            earthQuakes.addAll(db.getAllByMagnitude(magnitude));
            // Check if we were successful in obtaining the map.
            if (mMap != null){
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        for(int i=0;i<earthQuakes.size();i++){
            EarthQuake eq = earthQuakes.get(i);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(eq.getCoords().getLatitude(), eq.getCoords().getLongitude()))
                    .title(eq.getPlace())
                    .snippet(getString(R.string.magnitude ,eq.getMagnitude())));
        }
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
