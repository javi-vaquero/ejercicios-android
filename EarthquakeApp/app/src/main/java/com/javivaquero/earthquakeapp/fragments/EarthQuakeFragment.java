package com.javivaquero.earthquakeapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.javivaquero.earthquakeapp.DetailActivity;
import com.javivaquero.earthquakeapp.R;
import com.javivaquero.earthquakeapp.adapters.EarthQuakeArrayAdapter;
import com.javivaquero.earthquakeapp.model.Coordinate;
import com.javivaquero.earthquakeapp.model.EarthQuake;
import com.javivaquero.earthquakeapp.tasks.DownloadEarthQuakesTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@ link OnFragmentInteractionListener}
 * interface.
 */
public class EarthQuakeFragment extends ListFragment {

    public static final String EQ_ID = "ID";
    public static final String EQ_PLACE = "NAME";
    public static final String EQ_MAGNITUDE = "MAGNITUDE";
    private final String EARTHQUAKE = "EARTHQUAKE";

    private SharedPreferences prefs;

    private ArrayList<EarthQuake> earthQuakeArrayList = new ArrayList<>();
    private EarthQuakeArrayAdapter aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //downloadEarthQuakes();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }


    @Override
    public void onResume() {
        super.onResume();

        earthQuakeArrayList.clear();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);

        aa= new EarthQuakeArrayAdapter(getActivity(), R.layout.earthquake_item, earthQuakeArrayList);
        setListAdapter(aa);

        return layout;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(EQ_ID, earthQuakeArrayList.get(position).get_id());
        detailIntent.putExtra(EQ_PLACE, earthQuakeArrayList.get(position).getPlace());
        detailIntent.putExtra(EQ_MAGNITUDE, earthQuakeArrayList.get(position).getMagnitude());
        startActivity(detailIntent);
    }
}