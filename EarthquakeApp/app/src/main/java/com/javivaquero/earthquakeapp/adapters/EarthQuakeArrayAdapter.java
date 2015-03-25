package com.javivaquero.earthquakeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.javivaquero.earthquakeapp.R;
import com.javivaquero.earthquakeapp.model.EarthQuake;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by javi-vaquero on 25/03/15.
 */
public class EarthQuakeArrayAdapter extends ArrayAdapter<EarthQuake>{

    private int resource;

    public EarthQuakeArrayAdapter(Context context, int resource, ArrayList<EarthQuake> objects) {
        super(context, resource, objects);

        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;

        if(convertView==null){
            //Si no existe la vista, la creamos
            layout = new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        }
        else{
            layout = (LinearLayout) convertView;
        }


        EarthQuake item = getItem(position);

        TextView lblMagnitude = (TextView) layout.findViewById(R.id.magnitude);
        TextView lblPlace = (TextView) layout.findViewById(R.id.place);
        TextView lblDate = (TextView) layout.findViewById(R.id.date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        lblMagnitude.setText(String.valueOf(item.getMagnitude()));
        lblPlace.setText(item.getPlace());
        lblDate.setText(sdf.format(item.getDate()));


        return layout;
    }
}
