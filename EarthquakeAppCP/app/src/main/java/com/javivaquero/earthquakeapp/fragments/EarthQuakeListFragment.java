package com.javivaquero.earthquakeapp.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.javivaquero.earthquakeapp.DetailActivity;
import com.javivaquero.earthquakeapp.R;
import com.javivaquero.earthquakeapp.adapters.EarthQuakeArrayAdapter;
import com.javivaquero.earthquakeapp.adapters.EarthQuakeSimpleCursorAdapter;
import com.javivaquero.earthquakeapp.providers.EarthQuakeDB;
import com.javivaquero.earthquakeapp.model.EarthQuake;
import com.javivaquero.earthquakeapp.providers.EarthQuakesProvider;
import com.javivaquero.earthquakeapp.services.DownloadEarthQuakesService;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@ link OnFragmentInteractionListener}
 * interface.
 */
public class EarthQuakeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String EQ_ID = "ID";

    private static final int LOADER_ID = 1;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    private String[] from = { EarthQuakesProvider.Columns.TIME_KEY,
            EarthQuakesProvider.Columns.MAGNITUDE_KEY,
            EarthQuakesProvider.Columns.PLACE_KEY,
            EarthQuakesProvider.Columns._ID };
    private int[] to = { R.id.date, R.id.magnitude, R.id.place };
    private SimpleCursorAdapter adapter;


    private SharedPreferences prefs;
    private EarthQuakeDB earthquakeDB;

    private ArrayList<EarthQuake> earthQuakeArrayList;// = new ArrayList<>();
    private EarthQuakeArrayAdapter aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        earthquakeDB = new EarthQuakeDB(getActivity());
        //earthQuakeArrayList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);

       // aa= new EarthQuakeArrayAdapter(getActivity(), R.layout.earthquake_item, earthQuakeArrayList);
       // setListAdapter(aa);

        //adapter = new SimpleCursorAdapter(getActivity(), R.layout.earthquake_item, null, from, to, 0);
        adapter = new EarthQuakeSimpleCursorAdapter(getActivity(), R.layout.earthquake_item, null, from, to, 0);
        setListAdapter(adapter);

        mCallbacks = this;

        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID, null, mCallbacks);

        return layout;

    }

    @Override
    public void onResume() {
        super.onResume();

        /*double magnitude = new Double(prefs.getString(getString(R.string.PREF_MIN_MAGNITUDE), "0.0"));
        earthQuakeArrayList.clear();
        earthQuakeArrayList.addAll(earthquakeDB.getAllByMagnitude(magnitude));
        aa.notifyDataSetChanged();*/

        getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(EQ_ID, earthQuakeArrayList.get(position).get_id());
        startActivity(detailIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_refresh){
            Intent download = new Intent(getActivity(), DownloadEarthQuakesService.class);
            getActivity().startService(download);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String minMag = prefs.getString(
                getResources().getString(R.string.PREF_MIN_MAGNITUDE), "0");

        String where = EarthQuakesProvider.Columns.MAGNITUDE_KEY + " >= ? ";
        String[] whereArgs = { minMag };
        String order = EarthQuakesProvider.Columns.TIME_KEY + " DESC";

        CursorLoader loader = new CursorLoader(getActivity(),
                EarthQuakesProvider.CONTENT_URI, from, where, whereArgs, order);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }
}
