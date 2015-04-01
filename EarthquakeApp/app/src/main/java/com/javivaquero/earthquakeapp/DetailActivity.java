package com.javivaquero.earthquakeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.javivaquero.earthquakeapp.database.EarthQuakeDB;
import com.javivaquero.earthquakeapp.fragments.EarthQuakeFragment;
import com.javivaquero.earthquakeapp.model.EarthQuake;
import com.javivaquero.earthquakeapp.tasks.DownloadImageTask;



public class DetailActivity extends ActionBarActivity implements DownloadImageTask.SetImageInterface{

    public static final String URL = "URL";

    TextView lblPlace;
    TextView lblMagnitude;
    TextView lblCoordinates;
    TextView lblDate;
    TextView lblUrl;
    ImageView imgMap;

    EarthQuakeDB earthquakeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        earthquakeDB = new EarthQuakeDB(this);

        Intent detailIntent = getIntent();

        lblPlace = (TextView) findViewById(R.id.eqPlace);
        lblMagnitude = (TextView) findViewById(R.id.eqMagnitude);
        lblCoordinates = (TextView) findViewById(R.id.eqCoordinates);
        lblDate = (TextView) findViewById(R.id.eqDate);
        lblUrl = (TextView) findViewById(R.id.eqUrl);
        imgMap = (ImageView) findViewById(R.id.eqImage);


        String id = detailIntent.getStringExtra(EarthQuakeFragment.EQ_ID);
        EarthQuake earthquake = earthquakeDB.getById(id);

        String url = "http://maps.googleapis.com/maps/api/staticmap?center=" +
                earthquake.getCoords().getLongitude() + "," + earthquake.getCoords().getLatitude() +
                "&zoom=9&size=500x500&markers=color:red%7Ccolor:red%7Clabel:X%7C"+
                earthquake.getCoords().getLongitude() + "," + earthquake.getCoords().getLatitude() +
                "&sensor=false";
        new DownloadImageTask(this).execute(url);

        lblPlace.setText(earthquake.getPlace());
        lblMagnitude.setText(String.valueOf(earthquake.getMagnitude()));
        lblDate.setText(String.valueOf(earthquake.getDate()));
        lblCoordinates.setText(earthquake.getCoords().toString());
        lblUrl.setText(earthquake.getUrl());
        lblUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri uri = Uri.parse(lblUrl.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                Intent web = new Intent(DetailActivity.this, WebBrowserActivity.class);
                web.putExtra(URL,lblUrl.getText().toString());
                startActivity(web);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivity(prefsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        imgMap.setImageBitmap(bitmap);
    }
}
