package com.javivaquero.earthquakeapp.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.javivaquero.earthquakeapp.model.Coordinate;
import com.javivaquero.earthquakeapp.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by javi-vaquero on 27/03/15.
 */
public class EarthQuakeDB {

    private final String DATABASE = "DATABASE";

    private Context context;


    public static final String[] ALL_COLUMNS = {
                             EarthQuakesProvider.Columns._ID,
                             EarthQuakesProvider.Columns.PLACE_KEY,
                             EarthQuakesProvider.Columns.MAGNITUDE_KEY,
                             EarthQuakesProvider.Columns.LATITUDE_KEY,
                             EarthQuakesProvider.Columns.LONGITUDE_KEY,
                             EarthQuakesProvider.Columns.DEPTH_KEY,
                             EarthQuakesProvider.Columns.URL_KEY,
                             EarthQuakesProvider.Columns.TIME_KEY
    };

    public EarthQuakeDB(Context context) {
        this.context = context;
    }

    public void insertEarthQuake(EarthQuake earthquake){

        ContentValues insertValues = new ContentValues();
        insertValues.put(EarthQuakesProvider.Columns._ID, earthquake.get_id());
        insertValues.put(EarthQuakesProvider.Columns.PLACE_KEY, earthquake.getPlace());
        insertValues.put(EarthQuakesProvider.Columns.MAGNITUDE_KEY, earthquake.getMagnitude());
        insertValues.put(EarthQuakesProvider.Columns.LATITUDE_KEY, earthquake.getCoords().getLatitude());
        insertValues.put(EarthQuakesProvider.Columns.LONGITUDE_KEY, earthquake.getCoords().getLongitude());
        insertValues.put(EarthQuakesProvider.Columns.DEPTH_KEY, earthquake.getCoords().getDepth());
        insertValues.put(EarthQuakesProvider.Columns.URL_KEY, earthquake.getUrl());
        insertValues.put(EarthQuakesProvider.Columns.TIME_KEY, earthquake.getDate().getTime());

        ContentResolver cr = context.getContentResolver();
        cr.insert(EarthQuakesProvider.CONTENT_URI, insertValues);



    }

    public List<EarthQuake> getAllByMagnitude(double minMagnitude) {
        String where = EarthQuakesProvider.Columns.MAGNITUDE_KEY + ">=?";
        String whereArgs[] = {String.valueOf(minMagnitude)};

        return query(where, whereArgs);

    }

    public EarthQuake getById(String id){
        EarthQuake eq = null;

        String where = EarthQuakesProvider.Columns._ID + "=?";
        String whereArgs[] = {id};
        List <EarthQuake> q = query(where, whereArgs);
        if (q.size()>0){
            eq =  q.get(0);
        }
        return eq;

    }

    public List<EarthQuake> query(String where, String[] whereArgs){

        ContentResolver cr = context.getContentResolver();
        String orderBy = EarthQuakesProvider.Columns.TIME_KEY + " DESC";
        Cursor cursor = cr.query(EarthQuakesProvider.CONTENT_URI,ALL_COLUMNS,where,whereArgs, orderBy);

        HashMap<String,Integer> indexes = new HashMap<>();
        for (int i=0;i < ALL_COLUMNS.length;i++ ){
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
        while(cursor.moveToNext()){
            EarthQuake eq = new EarthQuake();
            eq.set_id(cursor.getString(indexes.get(EarthQuakesProvider.Columns._ID)));
            eq.setPlace(cursor.getString(indexes.get(EarthQuakesProvider.Columns.PLACE_KEY)));
            eq.setMagnitude(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.MAGNITUDE_KEY)));
            eq.setCoords(new Coordinate(cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.LATITUDE_KEY)),
                                        cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.LONGITUDE_KEY)),
                                        cursor.getDouble(indexes.get(EarthQuakesProvider.Columns.DEPTH_KEY))));
            eq.setUrl(cursor.getString(indexes.get(EarthQuakesProvider.Columns.URL_KEY)));
            eq.setDate(cursor.getLong(indexes.get(EarthQuakesProvider.Columns.TIME_KEY)));

            earthquakes.add(eq);
        }

        cursor.close();
        return earthquakes;
    }
}
