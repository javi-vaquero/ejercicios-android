package com.javivaquero.earthquakeapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.javivaquero.earthquakeapp.R;
import com.javivaquero.earthquakeapp.model.EarthQuake;

import java.util.List;

/**
 * Created by javi-vaquero on 9/04/15.
 */
public class EarthQuakesMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    GoogleMap map;
    List<EarthQuake> earthquakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        map = getMap();
        map.setOnMapLoadedCallback(this);

        return layout;

    }
    public void setEarthQuakes(List<EarthQuake> earthquakes) {
        this.earthquakes = earthquakes;
    }

    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (int i = 0; i < earthquakes.size(); i++) {
            LatLng point = new LatLng(earthquakes.get(i).getCoords().getLatitude(),
                    earthquakes.get(i).getCoords().getLongitude());
            MarkerOptions marker = new MarkerOptions()
                    .position(point)
                    .title(earthquakes.get(i).getPlace())
                    .snippet(getString(R.string.magnitude, earthquakes.get(i).getMagnitude()));

            map.addMarker(marker);
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu;
         /*
            LatLng ne = bounds.northeast;
            LatLng sw = bounds.southwest;

            restar para saber que latitud y que longitud se abarca, y si es menor que unos minimos abrandar el bounds
         */

        //Sustituir el anterior por este que solo comprueba que si tenemos un solo punto para hacer zoom
        if(earthquakes.size()==1){
            cu = CameraUpdateFactory.newLatLngZoom(new LatLng(earthquakes.get(0).getCoords().getLatitude(),
                    earthquakes.get(0).getCoords().getLongitude()), 9);
        }
        else {
            cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        }
        map.animateCamera(cu);

    }
}
