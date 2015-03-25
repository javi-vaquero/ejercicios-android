package com.javivaquero.earthquakeapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
public class EarthQuakeFragment extends ListFragment implements DownloadEarthQuakesTask.AddEarthQuakeInterface {

    private final String EARTHQUAKE = "EARTHQUAKE";

    private ArrayList<EarthQuake> earthQuakeArrayList = new ArrayList<>();

    private EarthQuakeArrayAdapter aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this);
        task.execute(getString(R.string.earthquakes_url));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);

        aa= new EarthQuakeArrayAdapter(getActivity(), R.layout.earthquake_item, earthQuakeArrayList);
        setListAdapter(aa);

        return layout;

    }


    @Override
    public void addEarthQuake(EarthQuake earthquake) {
        earthQuakeArrayList.add(0,earthquake);
        aa.notifyDataSetChanged();
    }
}
