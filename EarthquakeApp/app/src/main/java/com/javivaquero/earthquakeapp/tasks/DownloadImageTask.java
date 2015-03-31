package com.javivaquero.earthquakeapp.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by javi-vaquero on 31/03/15.
 */
public class DownloadImageTask extends AsyncTask<String,String,Bitmap> {

    public interface SetImageInterface{
        public void setImage(Bitmap bitmap);
    }

    private SetImageInterface target;


    public DownloadImageTask(SetImageInterface target){
        this.target=target;

    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch(Exception ex){
            return null;
        }

    }

    @Override
    protected void onProgressUpdate(String... s) {
        super.onProgressUpdate(s);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        target.setImage(bitmap);

    }
}
