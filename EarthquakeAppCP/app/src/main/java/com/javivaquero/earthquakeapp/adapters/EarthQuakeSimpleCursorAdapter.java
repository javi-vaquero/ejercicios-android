package com.javivaquero.earthquakeapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.javivaquero.earthquakeapp.R;
import com.javivaquero.earthquakeapp.model.EarthQuake;
import com.javivaquero.earthquakeapp.providers.EarthQuakesProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javi-vaquero on 16/04/2015.
 */
public class EarthQuakeSimpleCursorAdapter extends SimpleCursorAdapter {

    public EarthQuakeSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);


        //EarthQuake item = new EarthQuake();
        int placeCol = cursor.getColumnIndex(EarthQuakesProvider.Columns.PLACE_KEY);
        String place = cursor.getString(placeCol);
        int magnitudeCol = cursor.getColumnIndex(EarthQuakesProvider.Columns.MAGNITUDE_KEY);
        double magnitude = cursor.getDouble(magnitudeCol);
        int dateCol = cursor.getColumnIndex(EarthQuakesProvider.Columns.TIME_KEY);
        long date = cursor.getLong(dateCol);

        TextView lblMagnitude = (TextView) view.findViewById(R.id.magnitude);
        TextView lblPlace = (TextView) view.findViewById(R.id.place);
        TextView lblDate = (TextView) view.findViewById(R.id.date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        lblMagnitude.setText(String.format("%.2f",magnitude));
        lblPlace.setText(place);
        lblDate.setText(sdf.format(new Date(date)));

        int n = (int) magnitude * 10;
        int color = Color.rgb((255 * n) / 100, (255 * (100 - n)) / 100, 0);
        lblMagnitude.setBackgroundColor(color);

    }
}
