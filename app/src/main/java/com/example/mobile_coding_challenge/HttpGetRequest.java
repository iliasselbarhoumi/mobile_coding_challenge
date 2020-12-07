package com.example.mobile_coding_challenge;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params){


        // For storing data from web service
        String data = "";
        try {
            // Fetching the data from web service
            data = downloadUrl(params[0]);
            Log.d("mylog", "Background task data " + data.toString());
        }
        catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;

    }


    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();


            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            Log.d("mylog", "Downloaded URL: " + data.toString());
            br.close();

        }

        catch (Exception e) { Log.d("mylog", "Exception downloading URL: " + e.toString()); }

        finally
        {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
}
