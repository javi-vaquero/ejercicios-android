package com.javivaquero.earthquakeapp.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.javivaquero.earthquakeapp.R;
import com.javivaquero.earthquakeapp.model.Coordinate;
import com.javivaquero.earthquakeapp.model.EarthQuake;

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

/**
 * Created by cursomovil on 25/03/15.
 */
public class DownloadEarthQuakesTask extends AsyncTask<String,EarthQuake,Integer> {

    public interface AddEarthQuakeInterface{
        public void addEarthQuake(EarthQuake earthquake);
    }

    private final String EARTHQUAKE = "EARTHQUAKE";

    private AddEarthQuakeInterface target;

    public DownloadEarthQuakesTask(AddEarthQuakeInterface target){
        this.target=target;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        if(urls.length>0) {
            updateEarthQuakes(urls[0]);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthquakes) {
        super.onProgressUpdate(earthquakes);
        target.addEarthQuake(earthquakes[0]);
    }



    private void updateEarthQuakes(String earthQuakeFeed) {
        JSONObject json;
        String earthquakeUrl = earthQuakeFeed;

        try{
            URL url = new URL(earthquakeUrl);

            //	Create	a	new	HTTP	URL	connection
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int	responseCode = httpConnection.getResponseCode();
            if	(responseCode == HttpURLConnection.HTTP_OK)	{
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");

                for (int i = earthquakes.length()-1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {
            //Get coordinates
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1),jsonCoords.getDouble(2));

            //Get properties
            JSONObject properties = jsonObject.getJSONObject("properties");

            //EarthQuake
            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(jsonObject.getString("id"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setDate(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d(EARTHQUAKE, earthQuake.toString());
            publishProgress(earthQuake);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
